package ru.grabber.holder;

import org.apache.log4j.Logger;
import ru.grabber.util.Util;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Save (Holder) parsed links.
 *
 * @author Victor Bugaenko
 * @since 08.12.2018
 */

public class Save {
    private final Logger logger = Logger.getLogger(Save.class);
    private final String file;

    public Save(String website, Holder holder) {
        file = Util.getProjectName(website) +".save";
        save(holder);
    }

    private void save(Holder holder){
        try (ObjectOutputStream bOS = new ObjectOutputStream(new FileOutputStream( file ))) {
            bOS.writeObject(holder);
        } catch (Exception e) {
            logger.error("Problem with serialization (" + file + "): " + e.getMessage());
        }
    }


}
