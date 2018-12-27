package ru.grabber.holder;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestLinksHolder {

    private Holder holder;

    @Before
    public void prepareData() throws URISyntaxException {
        class TestHolder extends LinksHolder {}
        holder = new TestHolder();
    }

    @Test
    public void add_Test() throws URISyntaxException {
        holder.add(new URI("http://www.website.ru/file1"));
        TestCase.assertTrue(holder.amount() == 1);
    }

    @Test
    public void addSet_Test() throws URISyntaxException {
        holder.add(new HashSet<>(Arrays.asList(
            new URI("http://www.website.ru/file1"),
            new URI("http://www.website.ru/file2")
        )
        ));
        TestCase.assertTrue(holder.amount() == 2);
    }

    @Test
    public void addMap_Test() throws URISyntaxException {
        Map<URI, Boolean> links = new ConcurrentHashMap<>();
        links.put(new URI("http://www.website.ru/file1"), false);
        links.put(new URI("http://www.website.ru/file2"), false);
        holder.add(links);
        TestCase.assertTrue(holder.amount() == 2);
    }

    @Test
    public void haveNextLink_Test() throws URISyntaxException {
        TestCase.assertTrue(!holder.haveNextLink());
    }

    @Test
    public void haveNextLinkAfterAdded_Test() throws URISyntaxException {
        holder.add(new URI("http://www.website.ru/file1"));
        TestCase.assertTrue(holder.haveNextLink());
    }

    @Test
    public void haveNextLinkAfterChooseNext_Test() throws URISyntaxException {
        holder.add(new URI("http://www.website.ru/file1"));
        holder.chooseNext();
        TestCase.assertTrue(!holder.haveNextLink());
    }

    @Test
    public void haveNextLinkAfterAddedSetLinks_Test() throws URISyntaxException {
        holder.add(new HashSet<>(
            Arrays.asList( new URI("http://www.website.ru/file3") )
        ));
        TestCase.assertTrue(holder.haveNextLink());
    }

    @Test
    public void chooseNext_Test() throws URISyntaxException {

        holder.add(new HashSet<>(
            Arrays.asList(
                new URI("http://www.website.ru/file1"),
                new URI("http://www.website.ru/file2"),
                new URI("http://www.website.ru/file3")
            )
        ));

        int count = 0;
        for (int i = 0; i < 5; i++)
            if (holder.chooseNext() != null)
                count++;

        TestCase.assertTrue(count == 3);
    }

    @Test
    public void restore_Test() throws URISyntaxException {
        Map<URI, Boolean> links = new ConcurrentHashMap<>();
        links.put(new URI("http://www.website.ru/file1"), true);
        holder.add(links);
        TestCase.assertTrue(holder.amount() == 1);
        TestCase.assertTrue(holder.chooseNext() == null);
        holder.restore();
        TestCase.assertTrue(holder.chooseNext() != null);
    }

}
