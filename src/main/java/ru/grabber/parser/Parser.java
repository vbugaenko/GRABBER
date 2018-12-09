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

public class Parser implements Runnable {
    private final Logger logger = Logger.getLogger(Parser.class);
    private final LinksHolder holder;
    private final String website;
    private final AtomicInteger threadsCount;

    public Parser(String website, LinksHolder holder, AtomicInteger threadsCount) {
        if ((website==null)||(holder==null)||(threadsCount==null))
            throw new IllegalArgumentException();

        this.website = website.toLowerCase();
        this.holder = holder;
        this.threadsCount = threadsCount;
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

            logger.info("Parser collected: " +(links.images().size()+links.pages().size()) +" static links, "
                + "(for next parse " + links.pages().size() +")");

        } catch (IllegalArgumentException e) {
            logger.warn("Bad link: " + webpage);
        } catch (IOException e) {
            logger.warn("Problem connect with: " + webpage);
        }
    }

    @Override
    public void run() {
        threadsCount.incrementAndGet();

        while (holder.haveLinkForParse())
            parse( holder.chooseNext() );

        threadsCount.decrementAndGet();
    }
}