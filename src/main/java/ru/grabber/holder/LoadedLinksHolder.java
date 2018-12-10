package ru.grabber.holder;

public class LoadedLinksHolder extends AbstractHolder{

    private static volatile LoadedLinksHolder instance;
    private LoadedLinksHolder(){}

    public static LoadedLinksHolder getInstance() {
        LoadedLinksHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (LoadedLinksHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LoadedLinksHolder();
                }
            }
        }
        return localInstance;
    }


}
