package ru.grabber.holder;

import org.apache.log4j.Logger;
import ru.grabber.util.Util;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Load (Holder) parsed links.
 *
 * @author Victor Bugaenko
 * @since 12.12.2018
 */

public class Load {

    private final Logger logger = Logger.getLogger(Save.class);
    private final String file;

    public Load(String project) {
        this.file = Util.getProjectName(project)+".save";
    }

    public Holder get() {
        Holder holder = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            holder = (Holder) ois.readObject();
        } catch (Exception e) {
            logger.error("Problem with loading ("+ file +")" + e.getMessage());
        }
        return holder;
    }

}
