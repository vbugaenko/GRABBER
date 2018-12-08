package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String WEBSITE = "http://wwww.website.ru/";
    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool(CORES);
    private static final AtomicInteger threadsCount = new AtomicInteger(0);

    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();

            LinksHolder holder = new LinksHolder(WEBSITE);

            executor.execute(new ParseThread(WEBSITE, holder, threadsCount));
            Thread.sleep(3000);
            while (threadsCount.get() < CORES - 1)
                executor.execute(new ParseThread(WEBSITE, holder, threadsCount));

            while (threadsCount.get() > 0) {
                Thread.sleep(10000);
                System.out.println("threads: " + threadsCount.get());
                System.out.println("collected links: " + holder.amount());
            }

            System.out.println("Программа отработала за " + (System.currentTimeMillis() - startTime) / 1000 + " секунд");
            System.out.println("Было собрано: " + holder.amount() + " links");

        } catch (InterruptedException e) {
            logger.error("Mistake with main thread: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (IllegalArgumentException e) {
            logger.error("ParseThread did not get enough or correct data: " + e.getMessage());
        } catch (URISyntaxException e) {
            logger.error("Bad link: " + WEBSITE);
        }
    }

}