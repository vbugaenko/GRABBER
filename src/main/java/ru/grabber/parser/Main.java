package ru.grabber.parser;

import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    private static final String WEBSITE="http://wwww.website.ru/";
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executor = Executors.newFixedThreadPool( cores );
    private static final AtomicInteger worked = new AtomicInteger(0);

    public static void main(String[] args) {

        try {
            long startTime = System.currentTimeMillis();

            LinksHolder holder = new LinksHolder(WEBSITE);

                executor.execute( new Parsed( WEBSITE, holder, worked ) );
                Thread.sleep(3000);
                while (worked.get() < cores-1)
                    new Parsed( WEBSITE, holder, worked );

            System.out.println("Программа отработала за "+ (System.currentTimeMillis() - startTime) + " миллисекунд");
            System.out.println("Было собрано: "+  holder.amount() + " links");
        } catch (IllegalArgumentException e) {
            logger.warn("Bad link: " + WEBSITE);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}