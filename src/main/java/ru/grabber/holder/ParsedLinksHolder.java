package ru.grabber.holder;

/**
 * @author Victor Bugaenko
 * @since 10.12.2018
 */

public class ParsedLinksHolder extends AbstractHolder{

    private static volatile ParsedLinksHolder instance;
    private ParsedLinksHolder(){}

    public static ParsedLinksHolder getInstance() {
        ParsedLinksHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (ParsedLinksHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ParsedLinksHolder();
                }
            }
        }
        return localInstance;
    }

}
