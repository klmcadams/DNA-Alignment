# DNA-Alignment

This program uses dynamic programming in order to align two DNA sequences. Within the program, you can change the various scoring schemes and gap penalties to your desire. I used an abstract class to implement the score function, matrices for the creation of the score matrix as well as the traceback, and input strings. After running the program, it will print out the alignment of the two DNA sequences, as well as the alignment score, according to the chosen scoring scheme. To compile and run the program:

javac dna.java

java dna TGAC ACTG

You can replace TGAC and ACTG with any combination of letters (A,C,T,G).
