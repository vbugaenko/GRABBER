package ru.grabber;

import org.apache.log4j.Logger;
import ru.grabber.holder.Load;
import ru.grabber.loader.Loader;
import ru.grabber.parser.Parser;

import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author Victor Bugaenko
 * @since 11.12.2018
 */

public class Grab {
    private static final Logger logger = Logger.getLogger(Grab.class);
    private static final String WEBSITE="http://www.website.ru/";

    public static void main(String[] args) throws InterruptedException, URISyntaxException {

    parse();
    load();

    }

    private static void parse() throws InterruptedException, URISyntaxException {
        long start = System.currentTimeMillis();
        Parser parser = new Parser(new URI(WEBSITE));
        while (parser.getThreadsCount().get()>0)
            Thread.sleep(1000);
        logger.info("Parsed time: " + (System.currentTimeMillis()-start));
    }

    private static void load() throws InterruptedException {
        long start = System.currentTimeMillis();
        Loader loader = new Loader(WEBSITE, new Load(WEBSITE).get());
        while (loader.getThreadsCount().get()>0)
            Thread.sleep(1000);
        logger.info("Loaded time: " + (System.currentTimeMillis()-start));
    }

}
