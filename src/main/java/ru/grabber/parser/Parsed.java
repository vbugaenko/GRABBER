package ru.grabber.parser;

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
    private final String website;

    public Parsed(String website) {
        this.website = website.toLowerCase();

        parse(website);
        while (nextLink() != null)
            parse(nextLink());
    }

    private void parse(String webpage) {
        Filtered links =
             new Filtered(website,
                new ConvertedToURI(
                    new ElementsFrom(webpage)
                )
            );

            allWebsiteLinks.addAll(links.images());
            allWebsiteLinks.addAll(links.pages());

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