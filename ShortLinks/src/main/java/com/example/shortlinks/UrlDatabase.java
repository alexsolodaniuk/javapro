package com.example.shortlinks;

import java.io.PrintWriter;
import java.util.*;

public class UrlDatabase {

    public final static UrlDatabase INSTANCE = new UrlDatabase();

    private final Map<String, UrlRecord> db = new HashMap<>();
    private final Map<String,String> dbArc = new HashMap<>();
    private long n;
    private Object PrintWriter;

    private UrlDatabase() {
    }

    public synchronized String save(String url) {
        UrlRecord value = new UrlRecord();
        value.setUrl(url);
        if (dbArc.get(value.getUrl()) == null) {
            String key;
            String str = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int number = random.nextInt(str.length());
                sb.append(str.charAt(number));
            }
            key = sb.toString();
            db.put(key, value);
            dbArc.put(value.getUrl(), key);
            return key;
        }else {
            return dbArc.get(value.getUrl());
        }
    }

    public synchronized String get(String shortUrl) {
        UrlRecord value = db.get(shortUrl);
        value.inc();

        return value.getUrl();
    }

    private void getStringRandom() {

    }

    public synchronized Collection<UrlRecord> getStats() {
        return db.values();
    }

    static public class UrlRecord {
        String url;
        long counter;

        public void inc() {
            counter++;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public long getCounter() {
            return counter;
        }

        public void setCounter(long counter) {
            this.counter = counter;
        }
    }
}
