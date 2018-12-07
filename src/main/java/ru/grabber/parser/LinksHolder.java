package ru.grabber.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Parse all static content links (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 05.12.2018
 */

public class LinksHolder {

    private final Set<URI> allWebsiteLinks = Collections.newSetFromMap(new ConcurrentHashMap<URI, Boolean>());
    private final Map<URI, Boolean> parsingLinks = new ConcurrentHashMap<>();

    public LinksHolder(String website) throws URISyntaxException {
        parsingLinks.put(new URI(website), false);
    }

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

    public URI chooseNext() {
        for (Map.Entry entry : parsingLinks.entrySet())
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                return (URI) entry.getKey();
            }
        return null;
    }

    public boolean haveLinkForParse(){
        for (Map.Entry entry : parsingLinks.entrySet()) {
            if (entry.getValue().equals(false)) {
                return true;
            }
        }
        return false;
    }

}
