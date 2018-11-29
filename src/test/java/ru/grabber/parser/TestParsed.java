package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Test;

public class TestParsed {

    private final String blank = "<html><head><title>BlankPage</title></head><body>"
        + "<a href=\"http://www.website.ru/folder/file/\">text</a>"
        + "<img src=\"http://www.website.ru/folder/file/\" />"
        + "</body></html>";

    @Test
    public void integretaionTest() {
        String website = blank;
        Parsed parsed = new Parsed(website);

        TestCase.assertTrue(parsed.getAllWebsiteLinks().size() > 0);

    }
}
