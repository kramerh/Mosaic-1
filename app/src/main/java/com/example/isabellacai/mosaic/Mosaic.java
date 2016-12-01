package com.example.isabellacai.mosaic;

/**
 * Created by Heather on 11/30/2016.
 */

public class Mosaic {
    private int source;
    private String creator;
    private String timestamp;

    public Mosaic(int mosaicSource, String mosaicCreator, String createdTimestamp){
        source = mosaicSource;
        creator = mosaicCreator;
        timestamp = createdTimestamp;
    }

    public int getMosaicSource(){
        return source;
    }

    public void setMosaicSource(int newSource){
        source = newSource;
    }

    public String getCreator(){
        return creator;
    }

    public void setCreator(String s){
        creator = s;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String s){
        timestamp = s;
    }
}
