package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Parse all static content links (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class Parsed {
    private final Logger logger = Logger.getLogger(Parsed.class);
    private final LinksHolder holder = new LinksHolder();
    private final String website;

    public Parsed(String website) {
        if (website==null)
            throw new IllegalArgumentException();

        this.website = website.toLowerCase();

        parse(website);
        while (holder.nextLink() != null)
            parse(holder.nextLink());

        logger.info("Parsed: " + holder.amount() + " links");
    }

    private void parse(String webpage) {

        try {
            Filtered links =
                new Filtered(website,
                    new ConvertedToURI(
                        new ElementsFrom(webpage)
                    )
                );

            holder.addAll(links.images());
            holder.addAll(links.pages());

        } catch (IllegalArgumentException e) {
            logger.warn("Bad link: " + webpage);
        } catch (IOException e) {
            logger.warn("Problem connect with: " + webpage);
        }
    }

    public LinksHolder getHolder() {
        return holder;
    }
}