package ru.grabber;

import ru.grabber.parser.Parser;
import ru.grabber.util.Util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author Victor Bugaenko
 * @since 11.12.2018
 */

public class Grab {

    private static final String website="http://www.website.ru/";

    public static void main(String[] args) {

        loadParsedSavedLinks();

    }

    private static void parse(){
        try {
            new Parser(new URI(website));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static Map<URI, Boolean> loadParsedSavedLinks(){
        Map<URI, Boolean> parsed = null;
        try (FileInputStream fis = new FileInputStream(Util.getProjectName(website));
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            parsed = (Map<URI, Boolean>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parsed;
    }

}
