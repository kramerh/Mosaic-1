package com.example.isabellacai.mosaic;

/**
 * Created by Heather on 11/30/2016.
 */

class Mosaic {
    private String sourceFileName;
    private String creator;
    private String timestamp;

    Mosaic(String mosaicSource, String mosaicCreator, String createdTimestamp){
        sourceFileName = mosaicSource;
        creator = mosaicCreator;
        timestamp = createdTimestamp;
    }

    String getMosaicSource(){
        return sourceFileName;
    }

    String getCreator(){
        return creator;
    }

    String getTimestamp(){
        return timestamp;
    }


}
