package ru.grabber.parser;

import org.apache.log4j.Logger;
import ru.grabber.holder.Holder;
import ru.grabber.holder.LoadedLinksHolder;
import ru.grabber.holder.ParsedLinksHolder;

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
    private final Holder loaded = LoadedLinksHolder.getInstance();
    private final Holder parsed = ParsedLinksHolder.getInstance();
    private final String website;
    private final AtomicInteger threadsCount;

    Parser(String website, AtomicInteger threadsCount) {
        if ((website==null)||(threadsCount==null))
            throw new IllegalArgumentException();

        this.website = website.toLowerCase();
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

            loaded.add(links.images());
            loaded.add(links.pages());
            parsed.add(links.pages());

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

        while (parsed.haveNextLink())
            parse( parsed.chooseNext().toString() );

        threadsCount.decrementAndGet();
    }
}