package ru.grabber.loader;

import org.apache.log4j.Logger;
import ru.grabber.holder.Holder;
import ru.grabber.holder.LoadedLinksHolder;
import ru.grabber.util.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Load static content (images, html) from URI.
 *
 * @author Victor Bugaenko
 * @since 23.11.2018
 */

public class Loader implements Runnable {
    private final org.apache.log4j.Logger logger = Logger.getLogger(Loader.class);
    private final Holder holder = LoadedLinksHolder.getInstance();
    private final String folder;
    private final AtomicInteger threadsCount;

    public Loader(String folder) {
        this(Util.getProjectName(folder), new AtomicInteger(0));
    }

    private Loader(String folder, AtomicInteger threadsCount) {
        this.threadsCount = threadsCount;
        this.folder = folder;
    }

    @Override
    public void run() {
        threadsCount.incrementAndGet();

        while (holder.haveNextLink()) {
            URI uri = holder.chooseNext();
            String path = Util.getPath(uri);
            String file = Util.getFileName(path);
            String folders = Util.excludeFileName(path, file);

            make(folder + "/" + folders);
            save(uri, folder + "/" + path);

            startLoadersThreads();
        }
        threadsCount.decrementAndGet();
    }

    private void startLoadersThreads() {
        for (int i = threadsCount.get(); i < ( Runtime.getRuntime().availableProcessors()/2 ); i++)
            new Thread(new Loader(folder, threadsCount)).start();
    }

    private void make(String folders) {
        try {
            Files.createDirectories(Paths.get(folders));
        } catch (IOException e) {
            logger.error("Make folders (" + folders + ") error: " + e.getMessage());
        }
    }

    private void save(URI uri, String path) {
        try (ReadableByteChannel rbc = Channels.newChannel(uri.toURL().openStream());
             FileOutputStream fos = new FileOutputStream(path)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
