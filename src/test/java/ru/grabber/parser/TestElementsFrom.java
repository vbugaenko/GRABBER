package ru.grabber.parser;

import junit.framework.TestCase;
import org.junit.Test;

public class TestElementsFrom {
    private final String blank = "<html><head><title>BlankPage</title></head><body>"
        + "<a href=\"http://www.website.ru/folder/file/\">text</a>"
        + "<img src=\"http://www.website.ru/folder/file/\" />"
        + "</body></html>";

    @Test
    public void getElementsFromHtml(){
        ElementsFrom elms = new ElementsFrom(blank);
        TestCase.assertTrue(elms.images().size() == 1);
        TestCase.assertTrue(elms.pages().size() == 1);
    }

    @Test
    public void getElementsFromNull(){
        ElementsFrom elms = new ElementsFrom(null);
        TestCase.assertTrue(elms.images().size() == 0);
        TestCase.assertTrue(elms.pages().size() == 0);
    }

    @Test
    public void getElementsFrom404Resource(){
        ElementsFrom elms = new ElementsFrom("http://www.website404.ru/all_items/");
        TestCase.assertTrue(elms.images().size() == 0);
        TestCase.assertTrue(elms.pages().size() == 0);
    }

}
