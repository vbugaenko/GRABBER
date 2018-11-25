package ru.grabber.eoStyle;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class JSoup {
    private final Logger logger = Logger.getLogger(JSoup.class);
    private final String website;
    private final Document doc = getDocument();

    public JSoup(String website) {
        this.website = website;
    }

    private Document getDocument() {
        try {
            return Jsoup.connect(website).get();
        } catch (IOException e) {
            logger.error("Problem to connect with (" + website + ") for parsing : " + e.getMessage());
        }
        return null;
    }

    public Document getDoc() { return doc; }
}