package de.prttstft.materialmensa.items;

import java.util.ArrayList;
import java.util.List;

import de.prttstft.materialmensa.model.Library;

public class LibraryItems {
    static public List<Library> getLibs() {
        List<Library> libraries = new ArrayList<>();

        Library testLibrary1 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary1);

        Library testLibrary2 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary2);

        Library testLibrary3 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary3);

        Library testLibrary4 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary4);

        Library testLibrary5 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary5);

        Library testLibrary6 = new Library("Author", "Description", "License", "Name");
        libraries.add(testLibrary6);

        return libraries;
    }
}