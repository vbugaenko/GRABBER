package ru.grabber.eoStyle;

import ru.grabber.eoStyle.parser.Parsed;

public class Main {
    private static final String WEBSITE="http://www..ru/"; //http://wwww.website.ru/
    private static final String fileForSave=".ru";

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        System.out.println(new Parsed(WEBSITE).getAllWebsiteLinks().size() );
        //new SaveResult(fileForSave, new Parser(WEBSITE));

        System.out.println("Программа отработала за "+ (System.currentTimeMillis() - startTime) + " миллисекунд");

    }
}
