package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.net.URI;
import java.util.Set;

/**
 * Grab all static content (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class Parsed {
    private final Logger logger = Logger.getLogger(Parsed.class);
    private final LinksHolder holder = new LinksHolder();
    private final String website;

    public Parsed(String website) {
        this.website = website.toLowerCase();

        parse(website);

        while (holder.nextLink() != null)
            parse(holder.nextLink());

        logger.info("Parsed: " + holder.amount() + " links");
    }

    private void parse(String webpage) {
        Filtered links =
             new Filtered(website,
                new ConvertedToURI(
                    new ElementsFrom(webpage)
                )
            );

            holder.addAll(links.images());
            holder.addAll(links.pages());
    }

    public LinksHolder getHolder() { return holder; }
}