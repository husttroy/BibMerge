# BibMerge

BibMerge merges duplicated bib entries in bib files and update the corresponding references in tex files.

BibMerge considers two bib entries are duplicated if they share the same title (ignore special characters such as periods and parentheses).

If there are multple entries with the same title, BibMerge chooses the longest (or most detailed) bib entry and eliminates the rest. BibMerge also updates the references to the duplicated records in the given tex files to the reference of the chosen entry.

However, BibMerge does not fix broken entries in the original bib files. So you may want to fix all broken entries in the original tex files before merging them.
