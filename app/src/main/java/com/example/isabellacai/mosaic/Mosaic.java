package com.example.isabellacai.mosaic;

/**
 * Created by Heather on 11/30/2016.
 */

class Mosaic {
    private String sourceFileName;
    private String creator;
    private String timestamp;
    private int originalSource;
    private String originalArtist;
    private String title;

    Mosaic(String mosaicSource, String mosaicCreator, String createdTimestamp,
           int origSrc, String origArtist, String origTitle){
        sourceFileName = mosaicSource;
        creator = mosaicCreator;
        timestamp = createdTimestamp;
        originalSource = origSrc;
        originalArtist = origArtist;
        title = origTitle;
    }

    String getMosaicSource(){
        return sourceFileName;
    }

    int getOriginalSource(){
        return originalSource;
    }

    String getCreator(){
        return creator;
    }

    String getOriginalArtist(){
        return originalArtist;
    }

    String getTimestamp(){
        return timestamp;
    }

    String getTitle(){
        return title;
    }

}
