package ru.grabber.eoStyle.parser;

import org.apache.log4j.Logger;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Grab all static content (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

//TODO: Выделить хранилище в отдельный класс

public class Parsed {
    private final Set<URI> allWebsiteLinks = new HashSet<>();
    private final Map<URI, Boolean> parsingLinks = new HashMap<>();
    private final String WEBSITE;

    public Parsed(String website) {
        this.WEBSITE = website.toLowerCase();

        parse(WEBSITE);
        while (nextLink() != null)
            parse(nextLink());
    }

    private void parse(String webpage) {
        Internal links =
                new Internal(WEBSITE,
                    new Conditioned(
                        new ConvertedToURI(
                            new ElementsFrom(webpage)
                        )
                    )
                );

        if (links != null) {
            allWebsiteLinks.addAll(links.images());
            allWebsiteLinks.addAll(links.pages());
        }

        for (URI uri : links.pages())
            parsingLinks.putIfAbsent(uri, false);
    }

    private String nextLink() {
        for (Map.Entry entry : parsingLinks.entrySet()) {
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                return entry.getKey().toString();
            }
        }
        return null;
    }

    public Set<URI> getAllWebsiteLinks() {
        return allWebsiteLinks;
    }
}