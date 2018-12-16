package com.agileengine.testtask.app;

import com.agileengine.testtask.util.ElementFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

public class AppRunner {

    private static final Logger LOGGER = LogManager.getLogger(AppRunner.class);
    private final File original;
    private final File diff;
    private final String originalId;
    private final String error_message_args = "INVALID NUMBER OF ARGUMENTS";
    private Element element;

    public AppRunner(String[] args) {
        if (args.length<3){
            LOGGER.error(error_message_args);
            throw new InternalError(error_message_args);
        }
        original = new File(args[0]);
        diff = new File(args[1]);
        originalId = args[2];
    }

    public void runApp() {
        try {
            Document origin = Jsoup.parse(original, "UTF-8");
            Document differenced = Jsoup.parse(diff, "UTF-8");
            element = ElementFinder.findElement(origin, differenced, originalId);
        } catch (IOException e) {
            LOGGER.error("No valid original/diff HTML files found. ", e);
        }

        showElementInfo(element);

    }

    private void showElementInfo(Element element) {
        StringBuilder resultRow = new StringBuilder(element.tagName() + " < ");
        element.parents().forEach(p -> resultRow.append(p.tagName() + " < "));
        LOGGER.info(resultRow.deleteCharAt(resultRow.lastIndexOf("<")).toString());
        LOGGER.info("Element data : " + element.text());
        LOGGER.info("Element attributes : ");
        element.attributes().forEach(a -> LOGGER.info(a.getKey() + " : " + a.getValue()));
    }
}
