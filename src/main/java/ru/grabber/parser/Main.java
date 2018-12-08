package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String WEBSITE = "http://www.website.ru/";
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(CORES);
    private static final AtomicInteger threadsCount = new AtomicInteger(0);
    private static LinksHolder holder = new LinksHolder(WEBSITE);

    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();

            executor.execute(new Parser(WEBSITE, holder, threadsCount));
            Thread.sleep(3000);

            while (threadsCount.get() > 0) {
                startParsers();
                //System.out.println("threads: " + threadsCount.get());
                //System.out.println("collected links: " + holder.amount());
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

    private static void startParsers() {
        for (int i = threadsCount.get(); i < (CORES); i++)
            executor.execute(new Parser(WEBSITE, holder, threadsCount));
    }

}