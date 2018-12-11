package ru.grabber.holder;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Victor Bugaenko
 * @since 10.12.2018
 */

public abstract class AbstractHolder implements Holder, Serializable {

    private final Map<URI, Boolean> links = new ConcurrentHashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);

    public void add(Set<URI> links) {
        for (URI uri : links)
            this.links.putIfAbsent(uri, false);
    }

    public void add(URI link) {
        links.put(link, false);
    }

    public int amount() {
        return links.size();
    }

    public URI chooseNext() {
        for (Map.Entry entry : links.entrySet())
            if (entry.getValue().equals(false)) {
                entry.setValue(true);
                count.incrementAndGet();
                return (URI) entry.getKey();
            }
        return null;
    }

    public boolean haveNextLink(){
        return links.size() > count.get();
    }

    public void save(String project) {
        new SaveResult(project, links);
    }

}
