package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;

public class TestLinksHolder {

    private final String website = "http://www.website.ru";
    LinksHolder holder;

    @Before
    public void prepareData() throws URISyntaxException {
        holder = new LinksHolder(website);
    }

    @Test
    public void addForGrabb_Test() throws URISyntaxException {
        holder.addForGrabb(new HashSet<>(Arrays.asList(
            new URI("http://www.website.ru/file1"),
            new URI("http://www.website.ru/file2")
        )
        ));
        TestCase.assertTrue(holder.getAllWebsiteLinks().size() == 2);
    }

    @Test
    public void haveLinkForParse_Test() throws URISyntaxException {
        TestCase.assertTrue(holder.haveLinkForParse() == true);

        holder.chooseNext();
        TestCase.assertTrue(holder.haveLinkForParse() == false);

        holder.addLinksForParse(new HashSet<>(
            Arrays.asList( new URI("http://www.website.ru/file3") )
        ));
        TestCase.assertTrue(holder.haveLinkForParse() == true);
    }

    @Test
    public void chooseNext_Test() throws URISyntaxException {

        holder.addLinksForParse(new HashSet<>(
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

        TestCase.assertTrue(count == 4);
    }


}
