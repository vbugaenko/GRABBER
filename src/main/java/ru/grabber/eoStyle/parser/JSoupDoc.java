package ru.grabber.eoStyle.parser;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class JSoupDoc {
    private final Logger logger = Logger.getLogger(JSoupDoc.class);
    private final String webpage;
    private final Document doc;

    public JSoupDoc(String webpage) {
        this.webpage = webpage;
        doc = getDocument();
    }

    private Document getDocument() {
        try {
            return Jsoup.connect(webpage).get();
        } catch (IOException e) {
            logger.error("Problem to connect with (" + webpage + ") for parsing : " + e.getMessage());
        }
        return null;
    }

    public Document getDoc() { return doc; }
}