# AutoGrader

A program that will automatically, upon file selection, translate the file into a computer-friendly, readable alhpa-numeric representation 
of the selected .JAVA file (pending other languages) and assess the file for indentation errors, bracket placement errors (or lack thereof),
proper naming convention (TBD), and proper comments (specifics TBD w/Tennyson).

## Main File

Contains the loop in which the file browser is opened, the original filepath to be analyzed is obtained, then passed to the Translator object where
the respective methods are performed on it to translate it to a non-alphabet representation that can be easily interpreted by the Analyzer class' 
children classes.

## Tanslator

The initial object in the process that obtains the original filepath from the Main File, performs mutations on a "dumpable" file (locally cached)
that will be read from for the rest of the process (this is to avoid accidental mutation or corruption of the original file). Specifically, the 
Translator reads the original file line by line, performs the translation methods and writes that to a locally made, new file that can be cleared 
after each use. The Translator object stores the new filepath so it will be accessable for the Analyzer class to use to read from the translated file
ONLY. This prevents the analyzer from ever interacting with the original, "unfriendly" file.

## Analyzer (Interface)

The original plan is to set up an Analyzer interface class that will be implemented in 5 (pending #) seperate, more specific Analyers 
(ex. IndentAnalyzer). The interface will lay out the basic methods and their anticiapted return types (TBD) which the implementers will follow
to make the process generic across the board and allows our Analyzers to be independent of each other in the event that we are able to implement
multithreading to run all 5 document analyzers simultaneously to save time (fractions of seconds).

## IndentAnalyzer

The first analyzer made will deal with indentation errors, anticiapation, and lay the frame work for the BracketAnalyzer. This class will use the 
JAVA keywords (specifically their substituted versions) to anticiapte indentation. The JAVA convention is that after specific keywords 
(for, if, else, while, etc), JAVA automatically expects ONLY the next line to be indented. This is only changed by the presence of opening brackets '{'
and the corresponding closing bracket '}'. This class will use the keywords to anticiapte indentation and in tandem, use provided brackets to recognize
when the indentation level should rise/fall for specific lines (lack of brackets will flag an error in the BracketAnalzyer, but for this class we can only
guess that they only require the next line to be indented without any brackets). This class will also utilize a set of variables that represent 
expected indentation level, actual indentation level, and will initially scan the file for multiple types of indentation (tabs or 4 spaces only) and
determine which type is the majority and flag any other use as incorrect, but still indented.

## Other Analyzer Implemetors

TBD



# TODO

## Main
update the look and feel of the file browser to match windows 10/newest MAC.
import/setup Maven

## Translator
develop testing for translator methods using MOCKITO <br />
develop list of 'KeyWords' or JAVA reserved words that anticipate indentation using brackets
develop list of 'KeyWord-Substitutes' for the Analyzer class to recognize when searching for brackets/indents/comments
develop method 'keywordReplace' that replaces all previously mentioned 'KeyWords' with their respective substitiues
layout legend for JAVA (and later other languages) for KeyWord -> Substitutes

## Analyzer (Interface)
develop this interface file
decide on which methods and their return types
set up basic start files for the implementors

## IndentAnalyzer
determine best way to assess a file for the multiple parameters we have to search for.


# Sources
https://google.github.io/styleguide/javaguide.html

