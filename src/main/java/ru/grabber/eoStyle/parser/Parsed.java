package ru.grabber.eoStyle.parser;

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

public class Parsed {
    private final Set<URI> allWebsiteLinks = new HashSet<>();
    private final Map<URI, Boolean> pagesLinks = new HashMap<>();
    private final String WEBSITE;

    public Parsed(String website) {
        this.WEBSITE = website;

        parse(WEBSITE);
        while (nextLink() != null)
            parse(nextLink());
    }

    private void parse(String webpage) {
        Internal links =
            new Internal(WEBSITE,
                new FilteredConditionalURI(
                    new ConvertedToURI(
                        new ElementsOf(
                            new JSoupDoc(webpage)
                        )
                    )
                )
            );

        allWebsiteLinks.addAll(links.getImagesLinks());
        allWebsiteLinks.addAll(links.getPagesLinks());

        for (URI uri : links.getPagesLinks())
            pagesLinks.putIfAbsent(uri, false);
    }

    private String nextLink() {
        for (Map.Entry entry : pagesLinks.entrySet()) {
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