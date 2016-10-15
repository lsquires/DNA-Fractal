package com.mynameislaurence;

import java.util.ArrayList;

/**
 * Created by Home on 29/05/2015.
 */
public class Intensity {

    public ArrayList<Double> k;
    public ArrayList<Double> w;

    public Intensity(String[] s, int offset) {
        k = new ArrayList<Double>();
        w = new ArrayList<Double>();

        //Parse the input string into the actual useful variables for calculating colour
        for (int i = 0; i < 3; i++) {
            //Load the numbers and scale to the right size
            double value = (Double.parseDouble(s[i + offset].substring(1, 7))) / 10000;
            if (s[i + offset].substring(0, 1).equalsIgnoreCase("-")) {
                //Apply the right sign to the number
                value = -value;
            }
            k.add(value);
            double value2 = (Double.parseDouble(s[i + 1 + offset].substring(1, 7))) / 10000;
            if (s[i + 1 + offset].substring(0, 1).equalsIgnoreCase("-")) {
                value2 = -value2;
            }
            w.add(value2);
        }
    }

    public double getInten(int iteration) {
        //Get the value associated with the iteration
        double value = 0;
        for (int i = 0; i < k.size(); i++) {
            value = value + (1 / (1 + Math.exp((-k.get(i)) * (iteration - w.get(i)))));
        }
        value = value / (double) (k.size());
        return value;
    }
}
