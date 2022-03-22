#!/bin/bash

javac *.java

#java TreeTest test 1 > output1.txt
#diff output1.txt test_bst_output1.txt

#java TreeTest test 2 > output2.txt
#diff output2.txt test_bst_output2.txt

java TreeTest test 3 > output3.txt
diff output3.txt test_bst_output3.txt

java TreeTest test 4 > output4.txt
diff output4.txt test_bst_output4.txt

java TreeTest test 5 > output5.txt
diff output5.txt test_bst_output5.txt



