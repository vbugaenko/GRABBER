package ru.grabber.util;

import java.net.URI;
import java.nio.file.Paths;

public class Util {

    /**
     * Get WEBSITE name  http://www.website.ru/
     * @return           website
     */
    public static String getProjectName(String project) {
        return project
            .replaceAll(".*(//)", "")
            .replaceAll(".*(www.)", "")
            .replaceAll("(.ru).*", "");
    }

    /**
     * @param uri http://www.website.ru/folder/folder/file/
     * @return    folder/folder/file/
     */
    public static String getPath(URI uri) {
        return uri.getPath().replaceFirst("/", "");
    }

    /**
     * @param path http://www.website.ru/folder/folder/file/
     * @return     file
     */
    public static String getFileName(String path) {
        return Paths.get(path).getFileName().toString();
    }

    public static String excludeFileName(String path, String fileName) {
        return path.replace(fileName, "");
    }

}
