package ru.grabber.holder;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestSave {

    @Before
    public void prepareData() throws URISyntaxException {
        Map<URI, Boolean> links = new ConcurrentHashMap<>();
        links.put( new URI("http://www.website.ru/file1"), false);

        Holder holder = new LinksHolder();
        holder.add(links);
        new Save("testFile", holder);
    }

    @Test
    public void SaveResult_Test() throws URISyntaxException {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("testFile.save"))) {

            Holder loaded = (Holder) ois.readObject();

            TestCase.assertTrue(loaded.amount()==1);
            TestCase.assertTrue(loaded.chooseNext().toString().equals("http://www.website.ru/file1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void deleteTestFile(){
        new File("testFile.save").delete();
    }
}
