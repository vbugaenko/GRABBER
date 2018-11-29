package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Test;

public class TestConvertedToURI {

    final String blank = "<html><head><title>BlankPage</title></head><body>"
        + "<a href=\"http://www.website.ru/folder/file1/\">text</a>"
        + "<a href=\"http://www.website.ru/folder/file2/\">text</a>"
        + "<a href=\"http://www.website.ru/folder/file3/\">text</a>"
        + "<img src=\"http://www.website.ru/folder/file1/\" />"
        + "<img src=\"http://www.website.ru/folder/file2/\" />"
        + "</body></html>";

    final String badLinksBlank = "<html><head><title>BlankPage</title></head><body>"
        + "<a href=\"http://\">text</a>"
        + "<a href=\"#\">text</a>"
        + "<a href=\"\">text</a>"
        + "<a>text</a>"
        + "<a href>text</a>"
        + "<a href=>text</a>"
        + "<img src=\"http://\" />"
        + "<img src= />"
        + "</body></html>";


    @Test
    public void amountLinksFromBlank_Test() {
        ElementsFrom elmsFrm = new ElementsFrom(blank);
        ConvertedToURI uri = new ConvertedToURI(elmsFrm);
        TestCase.assertTrue(uri.images().size() == 2);
        TestCase.assertTrue(uri.pages().size() == 3);
    }

    @Test
    public void badLinkFromBlank_Test() {
        ElementsFrom elmsFrm = new ElementsFrom(badLinksBlank);
        ConvertedToURI uri = new ConvertedToURI(elmsFrm);
        TestCase.assertTrue(uri.images().size() == 0);
        TestCase.assertTrue(uri.pages().size() == 0);
    }

}

