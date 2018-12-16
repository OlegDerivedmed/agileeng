# HTML "analyzer"

How it works.

to run appliacation you should open promt and type: 
java -jar <app>.jar <input_origin_file_path> <input_other_sample_file_path> <original_element_id>
  
for example if you cloned this repo cd to results folder and type 
java -jar testtask-0.0.1-SNAPSHOT.jar test\sample0.html test\sample3.html make-everything-ok-button

Output is logging in console.

It`s very simple algorythm to find original element with bit diffs.
App founding original element by ID, remember all it`s parameters, it`s tag.
Then it looks on document with bit differences and founds all elements which have same tag and have no children elements. And then it choose element which have more same attributes then others.
