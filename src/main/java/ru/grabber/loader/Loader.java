package ru.grabber.loader;

import org.apache.log4j.Logger;
import ru.grabber.holder.Holder;
import ru.grabber.holder.LoadedLinksHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public Loader(String folder) {
        this.folder = folder;
    }

    @Override
    public void run() {

        while (holder.haveNextLink()) {
            URI uri = holder.chooseNext();
            String path = getPath(uri);
            String file = getName(path);
            String folders = excludeFileName(path, file);

            make(folder + "/" + folders);
            save(uri, folder + "/" + path);
        }
    }

    /**
     * @param uri http://www.website.ru/folder/folder/file/
     * @return    folder/folder/file/
     */
    private String getPath(URI uri) {
        return uri.getPath().replaceFirst("/", "");
    }

    /**
     * @param path http://www.website.ru/folder/folder/file/
     * @return     file
     */
    private String getName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    private String excludeFileName(String path, String fileName) {
        return path.replace(fileName, "");
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
