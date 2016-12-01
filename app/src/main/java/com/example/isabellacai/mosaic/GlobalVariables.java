package com.example.isabellacai.mosaic;

import java.util.ArrayList;

/**
 * Created by Heather on 11/30/2016.
 */

public class GlobalVariables {
        public ArrayList<Mosaic> mosaics;

        private GlobalVariables() {
            mosaics = new ArrayList<Mosaic>();
            Integer[] initialDrawables = {
                    R.drawable.sample_2, R.drawable.sample_3,
                    R.drawable.sample_4, R.drawable.sample_5,
                    R.drawable.sample_6, R.drawable.sample_7,
                    R.drawable.sample_0, R.drawable.sample_1,
                    R.drawable.sample_2, R.drawable.sample_3,
                    R.drawable.sample_4, R.drawable.sample_5,
                    R.drawable.sample_6, R.drawable.sample_7,
                    R.drawable.sample_0, R.drawable.sample_1,
            };

            String[] initialCreators = {
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous",
                    "Heather", "Janna", "Isabella", "Anonymous"
            };
            for (int i = 0; i < initialDrawables.length; i++){
                mosaics.add(new Mosaic(initialDrawables[i], initialCreators[i], "a minute ago"));
            }

        }

        private static GlobalVariables instance;

        public static GlobalVariables getInstance() {
            if (instance == null) instance = new GlobalVariables();
            return instance;
        }
}
