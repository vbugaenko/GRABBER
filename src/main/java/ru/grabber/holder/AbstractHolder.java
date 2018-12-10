package ru.grabber.holder;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractHolder implements Holder, Serializable {

    private final Map<String, Boolean> links = new ConcurrentHashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);
    private String website;

    public void add(Set<URI> links) {
        for (URI uri : links)
            this.links.putIfAbsent(uri.toString(), false);
    }

    public int amount() {
        return links.size();
    }

    public String chooseNext() {
        for (Map.Entry entry : links.entrySet())
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                count.incrementAndGet();
                return entry.getKey().toString();
            }
        return null;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean haveNextLink(){
        return links.size() > count.get();
    }
/*
    public void save() {
        new SaveResult(website, links);
    }
    */
}
