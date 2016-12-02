package com.example.isabellacai.mosaic;

import java.util.ArrayList;

/**
 * Created by Heather on 11/30/2016.
 */

public class GlobalVariables {
        public ArrayList<Mosaic> mosaics;

        private GlobalVariables() {
            mosaics = new ArrayList<Mosaic>();
            Integer[] initDrawables = {
                    R.drawable.m5, R.drawable.m1,
                    R.drawable.m3, R.drawable.m6,
                    R.drawable.m2, R.drawable.m4,
                    R.drawable.m5, R.drawable.m1,
                    R.drawable.m3, R.drawable.m6,
                    R.drawable.m2, R.drawable.m4
            };

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
            for (int i = 0; i < initDrawables.length; i++){
                mosaics.add(new Mosaic(initDrawables[i], initCreators[i], "a minute ago",
                        initOrigSrc[i], initOrigArtists[i], initOrigTitles[i]));
            }

        }

        private static GlobalVariables instance;

        public static GlobalVariables getInstance() {
            if (instance == null) instance = new GlobalVariables();
            return instance;
        }
}
