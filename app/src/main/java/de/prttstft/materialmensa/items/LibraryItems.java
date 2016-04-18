package de.prttstft.materialmensa.items;

import java.util.ArrayList;
import java.util.List;

public class LibraryItems {
    static public List<LibraryItem> getLibs() {
        List<LibraryItem> libraries = new ArrayList<>();

        LibraryItem testLibraryItem1 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem1);

        LibraryItem testLibraryItem2 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem2);

        LibraryItem testLibraryItem3 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem3);

        LibraryItem testLibraryItem4 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem4);

        LibraryItem testLibraryItem5 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem5);

        LibraryItem testLibraryItem6 = new LibraryItem("Author", "Description", "License", "Name");
        libraries.add(testLibraryItem6);

        return libraries;
    }
}