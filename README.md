# BibMerge

BibMerge merges duplicated bib entries in bib files and update the corresponding references in tex files.

BibMerge considers two bib entries are duplicated if they share the same title (ignore special characters such as periods and parentheses).

If there are multple entries with the same title, BibMerge chooses the longest (or most detailed) bib entry and eliminates the rest. BibMerge also updates the references to the duplicated records in the given tex files to the reference of the chosen entry.

However, BibMerge does not fix broken entries in the original bib files. So you may want to fix all broken entries in the original tex files before merging them.

To run BibMerge,

```bash
java -jar bibmerge.jar edu.ucla.cs.bib.merge.BibMerge -b bib1:bib2 -t tex1:tex2 [-o output]
```
usage: BibMerge
 -b,--bib <arg>      list all bib files you want to merge, separated by :
 -h,--help           print this message
 -o,--output <arg>   specify the output file of merged bib entries,
                     default is merged.bib in the current directory
 -t,--tex <arg>      list all tex files you want to update, separated by :

To build from source,

```bash
mvn package
```
