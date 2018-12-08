package ru.grabber.parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parse all static content links (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 05.12.2018
 */

public class LinksHolder {

    private final Set<URI> allWebsiteLinks = Collections.newSetFromMap( new ConcurrentHashMap<URI, Boolean>() );
    private final Map<URI, Boolean> linksForParsing = new ConcurrentHashMap<>();
    private final AtomicInteger countParsedLinks = new AtomicInteger(0);

    public LinksHolder(String website) throws URISyntaxException {
        linksForParsing.put(new URI(website), false);
    }

    public void addForGrabb(Set<URI> links) {
        allWebsiteLinks.addAll(links);
    }

    public void addLinksForParse(Set<URI> links) {
        for (URI uri : links)
            linksForParsing.putIfAbsent(uri, false);
    }

    public int amount() {
        return allWebsiteLinks.size();
    }

    public URI chooseNext() {
        for (Map.Entry entry : linksForParsing.entrySet())
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                countParsedLinks.incrementAndGet();
                return (URI) entry.getKey();
            }
        return null;
    }

    public boolean haveLinkForParse(){
        return linksForParsing.size() > countParsedLinks.get();
    }

    public Set<URI> getAllWebsiteLinks() {
        return allWebsiteLinks;
    }
}
