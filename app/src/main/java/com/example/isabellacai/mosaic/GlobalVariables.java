package com.example.isabellacai.mosaic;

import java.util.ArrayList;

/**
 * Created by Heather on 11/30/2016.
 */

public class GlobalVariables {
        public ArrayList<Mosaic> mosaics;
        public int mosaicNumber;
        public int startOfInitials;
        public String storageDir;
        public Integer[] initDrawables = {
            R.drawable.m1
        };
        private GlobalVariables() {
            mosaics = new ArrayList<Mosaic>();
            storageDir = "";
            startOfInitials = 0;


            String[] initCreators = {
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous"
            };


            for (int i = 0; i < initDrawables.length; i++){
                mosaics.add(new Mosaic("m"+i+".jpeg", initCreators[i], "Dec 9, 2016"));
            }
            mosaicNumber = mosaics.size(); //number of the next one to add
        }
        private static GlobalVariables instance;

        public static GlobalVariables getInstance() {
            if (instance == null) instance = new GlobalVariables();
            return instance;
        }

}
