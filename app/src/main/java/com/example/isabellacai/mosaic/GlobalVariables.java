package com.example.isabellacai.mosaic;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Heather on 11/30/2016.
 */

public class GlobalVariables {
        public ArrayList<Mosaic> mosaics;
        public int mosaicNumber;
        public String storageDir;
        public Integer[] initDrawables = {
            R.drawable.m5, R.drawable.m1,
            R.drawable.m3, R.drawable.m6,
            R.drawable.m2, R.drawable.m4,
            R.drawable.m5, R.drawable.m1,
            R.drawable.m3, R.drawable.m6,
            R.drawable.m2, R.drawable.m4
        };
        private GlobalVariables() {
            mosaics = new ArrayList<Mosaic>();
            storageDir = "";


            String[] initCreators = {
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous"
            };

            Integer[] initOrigSrc = {
                    R.drawable.voyage, R.drawable.nyt,
                    R.drawable.sacramentoriver, R.drawable.voyage,
                    R.drawable.nyt, R.drawable.sacramentoriver,
                    R.drawable.voyage, R.drawable.nyt,
                    R.drawable.sacramentoriver, R.drawable.voyage,
                    R.drawable.nyt, R.drawable.sacramentoriver
            };

            String[] initOrigArtists = {
                    "Jules Olitski", "Dashiell Manley",
                    "Gregory Kondos", "Jules Olitski",
                    "Dashiell Manley", "Gregory Kondos",
                    "Jules Olitski", "Dashiell Manley",
                    "Gregory Kondos", "Jules Olitski",
                    "Dashiell Manley", "Gregory Kondos"
            };

            String[] initOrigTitles = {
                    "Voyage", "The New York Times",
                    "Sacremento River", "Voyage",
                    "The New York Times", "Sacremento River",
                    "Voyage", "The New York Times",
                    "Sacremento River", "Voyage",
                    "The New York Times", "Sacremento River"
            };

            String[] ids = {
                    "voyage", "nyt",
                    "sacramentoriver", "voyage",
                    "nyt", "sacramentoriver",
                    "voyage", "nyt",
                    "sacramentoriver", "voyage",
                    "nyt", "sacramentoriver"
            };
            for (int i = 0; i < initDrawables.length; i++){
                mosaics.add(new Mosaic(""+i, initCreators[i], "a minute ago"));
            }
            mosaicNumber = mosaics.size(); //number of the next one to add
        }
//        private string saveImage(Bitmap bm, String filename){
//
//            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//            filename = filename + ".jpg";
//            // Create imageDir
//            File path=new File(directory, filename);
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream(path);
//                // Use the compress method on the BitMap object to write image to the OutputStream
//                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return
//        }
        private static GlobalVariables instance;

        public static GlobalVariables getInstance() {
            if (instance == null) instance = new GlobalVariables();
            return instance;
        }
}
