package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Parse all static content links (images, html) from web-site.
 *
 * @author Victor Bugaenko
 * @since 25.11.2018
 */

public class ParseThread implements Runnable {
    private final Logger logger = Logger.getLogger(ParseThread.class);
    private final LinksHolder holder;
    private final String website;
    private final AtomicInteger worked;

    public ParseThread(String website, LinksHolder holder, AtomicInteger worked) {
        if ((website==null)||(holder==null)||(worked==null))
            throw new IllegalArgumentException();

        this.website = website.toLowerCase();
        this.holder = holder;
        this.worked = worked;
    }

    private void parse(String webpage) {

        try {
            Filtered links =
                new Filtered(website,
                    new ConvertedToURI(
                        new ElementsFrom(webpage)
                    )
                );

            holder.addForGrabb(links.images());
            holder.addForGrabb(links.pages());

            holder.addLinksForParse(links.pages());

        } catch (IllegalArgumentException e) {
            logger.warn("Bad link: " + webpage);
        } catch (IOException e) {
            logger.warn("Problem connect with: " + webpage);
        }
    }

    public LinksHolder getHolder() {
        return holder;
    }

    @Override
    public void run() {
        worked.incrementAndGet();

        while (holder.haveLinkForParse())
            parse( holder.chooseNext().toString() );

        worked.decrementAndGet();
    }
}