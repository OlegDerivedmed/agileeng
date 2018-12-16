package com.agileengine.testtask.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ElementFinder {

    private static final Logger LOGGER = LogManager.getLogger(ElementFinder.class);
    private static final String error_elem_id = "Incorrect ID entered or can`t find element with such ID.";

    private static HashMap<String, String> originalAttrs = new HashMap<>();

    private static ElementFinder ourInstance = new ElementFinder();

    public static ElementFinder getInstance() {
        return ourInstance;
    }

    private ElementFinder() {
    }

    public static Element findElement(Document original, Document diff, String originalId) {

        Element original_element = original.getElementById(originalId);
        if (original_element == null){
            LOGGER.error(error_elem_id);
            throw new InternalError(error_elem_id);
        }
        fillAttrs(original_element);
        List<Element> candidates = diff.getAllElements().stream()
                .filter(e -> e.tagName().equals(original_element.tagName()))
                .filter(e -> e.children().isEmpty())
                .collect(Collectors.toList());
        return getMostSimilarCandidate(candidates);
    }

    private static void fillAttrs(Element original_element) {
        original_element.attributes()
                .forEach(a -> originalAttrs.put(a.getKey(), a.getValue()));
    }

    private static Element getMostSimilarCandidate(List<Element> candidates) {
        if (candidates.isEmpty()){
            throw new InternalError("No similar elements found.");
        }
        int matches = 0;
        Optional<Element> winner = Optional.empty();
        for (Element candidate : candidates) {
            int count = matchesCount(candidate);
            if (count > matches) {
                matches = count;
                winner = Optional.of(candidate);
            }
        }
        return winner.orElseThrow(InternalError::new);
    }

    private static int matchesCount(Element candidate) {
        return (int) candidate.attributes().asList().stream()
                .filter(a -> originalAttrs.containsKey(a.getKey()))
                .filter(a -> a.getValue().equals(originalAttrs.get(a.getKey())))
                .count();
    }
}
