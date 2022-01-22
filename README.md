# AutoGrader
Software project

An automatic program that, when fed a file consisting of majority code, will attempt to analyze the document for indentation errors, 
    naming convention errors, bracket placement, and comments.
  
The first goal of the program is to translate a given code file into a computer readable version using alpha-numeric representations of 
    indentations or spaces (acceptable indentations being multiples of 4 spaces or 1 Tab). Then take this translation and analyze it to determine
    what the expected indentation level is, what it actually is, where any deviations occur, and flag these deviations for assignment to an object 
    that will have a method to report all given lines that have an indentation error.
  
Second goal will follow as first is fully completed.
