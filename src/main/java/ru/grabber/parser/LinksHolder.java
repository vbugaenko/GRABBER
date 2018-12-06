package ru.grabber.parser;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Parse all static content links (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 05.12.2018
 */

public class LinksHolder {

    private final Set<URI> allWebsiteLinks = new HashSet<>();
    private final Map<URI, Boolean> parsingLinks = new HashMap<>();

    public void addForGrabb(Set<URI> links) {
        allWebsiteLinks.addAll(links);
    }

    public void addLinksForParse(Set<URI> links) {
        for (URI uri : links)
            parsingLinks.putIfAbsent(uri, false);
    }


    public int amount() {
        return allWebsiteLinks.size();
    }

    public String nextLink() {
        for (Map.Entry entry : parsingLinks.entrySet()) {
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                return entry.getKey().toString();
            }
        }
        return null;
    }

    //TODO сохранение результатов?
}
