package ru.grabber.parser;

import org.apache.log4j.Logger;
import ru.grabber.holder.Holder;
import ru.grabber.holder.ParsedLinksHolder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Multithreading management for parsers.
 *
 * @author Victor Bugaenko
 * @since 22.11.2018
 */

public class Manager {
    private final Logger logger = Logger.getLogger(Manager.class);
    private final String website;
    private final int cores = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executor = Executors.newFixedThreadPool(cores);
    private final AtomicInteger threadsCount = new AtomicInteger(0);
    private final Holder holder = ParsedLinksHolder.getInstance();

    public Manager(String website) throws URISyntaxException {
        this.website = website;
        this.holder.add(new URI(website));
        parse();
    }

    private void parse(){
        try {
            long startTime = System.currentTimeMillis();

            executor.execute(new Parser(website, threadsCount));
            Thread.sleep(3000);

            while (threadsCount.get() > 0) {
                startParsersThreads();
                Thread.sleep(10000);
            }

            logger.info("Total spent time:" + (System.currentTimeMillis() - startTime) / 1000 + " second");
            logger.info("Total collected: " + holder.amount() + " links");

        } catch (InterruptedException e) {
            logger.error("Mistake with main thread: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            logger.error("ParseThread did not get enough or correct data: " + e.getMessage());
        }
    }

    private void startParsersThreads() {
        for (int i = threadsCount.get(); i < (cores); i++)
            executor.execute(new Parser(website, threadsCount));
    }


}