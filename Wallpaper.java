package com.mynameislaurence;

import sun.nio.cs.ext.IBM037;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Home on 26/05/2015.
 */
public class Wallpaper {


    public Double[] memory = new Double[32];
    public Double[] oldmemory = new Double[32];
    public String[] functions = new String[32];
    public Random rand;
    public String randomSeed;
    private BufferedImage image;
    public double[][] red;
    public double[][] green;
    public double[][] blue;

    public Intensity Ired;
    public Intensity Igreen;
    public Intensity Iblue;

    public Wallpaper(String[] DNA) {
        parseDna(DNA);
    }

    private void parseDna(String[] dna) {
        for (int i = 0; i < 13; i++) {
            //Intilize all odd memories
            double value = (Double.parseDouble(dna[i].substring(3, 13))) / (double) (10000000);
            int mem = ((int) dna[i].charAt(0)) - 65;
            if (dna[i].substring(2, 3).equalsIgnoreCase("-")) {
                value = -value;
            }
            //Assign each memory
            setMemory(dna[i].charAt(0), value);
        }
        oldmemory = memory.clone();

        //Mirroring of the memories, a trick to get more interesting behaviours from less DNA
        for (int i = 13; i < 21; i++) {
            functions[i - 13] = dna[i];
        }

        //Simplify

        for (int i = 0; i < 8; i++) {
            //Delete second variable in singleton functions, this happens to reduce lag and useless cycles
            String old = functions[i];
            functions[i] = filterSingles(functions[i]);

        }

        //Simplify


        //Get the random seed
        randomSeed = dna[21];
        rand = new Random(Long.parseLong(randomSeed.toLowerCase(), 36));

        //Get the colour settings
        Ired = new Intensity(dna, 22);
        Igreen = new Intensity(dna, 27);
        Iblue = new Intensity(dna, 32);
    }

    private String filterSingles(String s) {
        StringBuilder ss = new StringBuilder(s);
        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i, i + 1).equalsIgnoreCase("#")) {
                String b = s.substring(i + 1, i + 2);

                //Some functions only use one paramter, so we replace the other paramter with A (wether it is a variable or function)
                if (b.equalsIgnoreCase("A")
                        || b.equalsIgnoreCase("B")
                        || b.equalsIgnoreCase("C")
                        || b.equalsIgnoreCase("D")
                        || b.equalsIgnoreCase("E")
                        || b.equalsIgnoreCase("F")
                        || b.equalsIgnoreCase("S")
                        || b.equalsIgnoreCase("T")
                        || b.equalsIgnoreCase("U")
                        || b.equalsIgnoreCase("V")
                        || b.equalsIgnoreCase("W")
                        || b.equalsIgnoreCase("X")
                        || b.equalsIgnoreCase("Y")) { //I should really make this nicer
                    int current = 1;

                    //Count brackets to get the correct range to delete
                    for (int i2 = (i + 2); i2 < s.length(); i2++) {
                        if (s.substring(i2, i2 + 1).equalsIgnoreCase("[")) {
                            current++;
                        } else if (s.substring(i2, i2 + 1).equalsIgnoreCase("]")) {
                            current--;
                        }

                        if (current == 0) {
                            current = i2;
                            break;
                        }
                    }

                    if (s.substring(i + 2, i + 3).equalsIgnoreCase("[")) {
                        int current2 = 1;
                        for (int i2 = (i + 3); i2 < s.length(); i2++) {
                            if (s.substring(i2, i2 + 1).equalsIgnoreCase("[")) {
                                current2++;
                            } else if (s.substring(i2, i2 + 1).equalsIgnoreCase("]")) {
                                current2--;
                            }

                            if (current2 == 0) {
                                current2 = i2 + 1;
                                break;
                            }
                        }

                        ss.replace(current2, current, "A");
                        s = ss.toString();
                    } else {
                        ss.replace(i + 3, current, "A");

                        s = ss.toString();
                    }

                }
            }
        }
        return ss.toString();
    }

    private void setMemory(char c, double value) {
        int mem = ((int) c) - 65;
        setMemory2(mem, value);
    }

    private void setMemory2(int mem, double value) {
        if (mem < 22) {
            //Mirroring memories
            memory[mem % 11] = value;
        }
    }


    private void setMemoryForce(char c, double value) { //Use only at start to set unchangeable memories
        int mem = ((int) c) - 65;
        setMemoryForce2(mem, value);

    }

    private void setMemoryForce2(int mem, double value) {
        if (mem < 22) {
            memory[mem % 11] = value;
        } else {
            memory[mem % 11 + 11] = value;
        }
    }

    private double getMemory(char c) {
        int mem = ((int) c) - 65;
        //System.out.println("get "+c+"="+mem);
        return getMemory2(mem);
    }

    private double getMemory2(int mem) {

        //return 0;

        if (mem < 22) {
            return (memory[mem % 11] == null) ? 0 : memory[mem % 11];
        } else {
            return (memory[(mem % 11) + 11] == null) ? 0 : (memory[(mem % 11) + 11]);
        }

    }

    public BufferedImage drawWallPaper(int width, int height) {
        double ratio = ((double) (width) / (double) (height));
        //System.out.println(" ===  Starting  ===  width" + width+"   height"+height+"   ratio"+ratio);
        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        red = new double[width][height];
        blue = new double[width][height];
        green = new double[width][height];
        //Create the canvas

        for (int x = 0; x < width; x++) {
            if (x % 75 == 1) {
                System.out.println((80 * x / width) + "% done");
            }
            for (int y = 0; y < height; y++) {
                memory = oldmemory.clone();
                newPixel(x, y, width, height, ratio);
            }
        }
        double mostr = 0;
        double mostg = 0;
        double mostb = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (red[x][y] > mostr) {
                    mostr = red[x][y];
                }
                if (green[x][y] > mostg) {
                    mostg = green[x][y];
                }
                if (blue[x][y] > mostb) {
                    mostb = blue[x][y];
                }
            }
        }

        int maxr = (int) Math.ceil(mostr);
        int maxg = (int) Math.ceil(mostg);
        int maxb = (int) Math.ceil(mostb);


        int[] redf = new int[maxr + 1];
        int[] greenf = new int[maxg + 1];
        int[] bluef = new int[maxb + 1];


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = (int) Math.ceil(red[x][y]);
                for (int i = 0; i <= r; i++) {
                    redf[i] = redf[i] + 1;
                }
                int g = (int) Math.ceil(green[x][y]);
                for (int i = 0; i <= g; i++) {
                    greenf[i] = greenf[i] + 1;
                }
                int b = (int) Math.ceil(blue[x][y]);
                for (int i = 0; i <= b; i++) {
                    bluef[i] = bluef[i] + 1;
                }
            }
        }


        //Post processing to make any fractal visibile
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // red[x][y] = (double)(redf[(int)Math.ceil(red[x][y])]-1)/(double)(redf[0]);
                //  green[x][y] =(double)(greenf[(int)Math.ceil(green[x][y])]-1)/(double)(greenf[0]);
                // blue[x][y] = (double)(bluef[(int)Math.ceil(blue[x][y])]-1)/(double)(bluef[0]);
                red[x][y] = red[x][y] / mostr;
                green[x][y] = green[x][y] / mostg;
                blue[x][y] = blue[x][y] / mostb;
            }
        }
        // System.out.println(80+"% done");
        Color c;
        for (int x = 0; x < width; x++) {
            if (x % 75 == 1) {
                System.out.println(80 + (20 * x / width) + "% done");
            }
            for (int y = 0; y < height; y++) {
                c = new Color((float) (red[x][y]), (float) (green[x][y]), (float) (blue[x][y]), 1);
                image.setRGB(x, y, c.getRGB());
            }
        }


        return image;
    }

    public void newPixel(int xs, int ys, int w, int h, double ratio) {
        //Scale x,y to fractal space
        double x = ((double) (xs) / (double) (w)) * 4d - 2d;
        double y = (((double) (ys) / (double) (h)) * 4d - 2d) * ratio;

        //Hard code in x,y,z
        setMemoryForce('A', x);
        setMemoryForce('B', y);
        setMemoryForce('C', 0);

        //Copy to lock pos
        lockMemories();

        //Unscaled x,y
        double ux = 0;
        double uy = 0;
        for (int n = 0; n < 25; n++) {
            iterateFunctions();


            if (getMemory('A') * getMemory('A') + getMemory('B') * getMemory('B') <= 100) {
                ux = (((getMemory('A') + 2d) / 4d) * w);
                uy = ((((getMemory('B') / ratio) + 2d) / 4d) * h);

                if (ux >= 0 && ux < w && uy >= 0 && uy < h) {
                    //Check to make sure we are still in bounds of the image
                    int xx = (int) Math.floor(ux);
                    int yy = (int) Math.floor(uy);

                    double r1 = (1 - ux + xx) * (1 - uy + yy);
                    red[xx][yy] = red[xx][yy] + Ired.getInten(n * 5) * r1 * (1 / (red[xx][yy] + 1));
                    green[xx][yy] = green[xx][yy] + Igreen.getInten(n * 5) * r1 * (1 / (green[xx][yy] + 1));
                    blue[xx][yy] = blue[xx][yy] + Iblue.getInten(n * 5) * r1 * (1 / (blue[xx][yy] + 1));
                    //Add the iteration value to the current pixel we landed on


                    //If we are still in the picture render over a 2x2 to make the image more pretty
                    if (yy + 1 < h) {
                        double r2 = (1 - (ux - xx)) * (uy - yy);
                        red[xx][yy + 1] = red[xx][yy + 1] + Ired.getInten(n * 5) * r2 * (1 / (red[xx][yy + 1] + 1));
                        green[xx][yy + 1] = green[xx][yy + 1] + Igreen.getInten(n * 5) * r2 * (1 / (green[xx][yy + 1] + 1));
                        blue[xx][yy + 1] = blue[xx][yy + 1] + Iblue.getInten(n * 5) * r2 * (1 / (blue[xx][yy + 1] + 1));
                    }
                    if (xx + 1 < w) {
                        double r3 = (ux - xx) * (1 - uy + yy);
                        red[xx + 1][yy] = red[xx + 1][yy] + Ired.getInten(n * 5) * r3 * (1 / (red[xx + 1][yy] + 1));
                        green[xx + 1][yy] = green[xx + 1][yy] + Igreen.getInten(n * 5) * r3 * (1 / (green[xx + 1][yy] + 1));
                        blue[xx + 1][yy] = blue[xx + 1][yy] + Iblue.getInten(n * 5) * r3 * (1 / (blue[xx + 1][yy] + 1));

                        if (yy + 1 < h) {
                            double r4 = (ux - xx) * (uy - yy);
                            red[xx + 1][yy + 1] = red[xx + 1][yy + 1] + Ired.getInten(n * 5) * r4 * (1 / (red[xx + 1][yy + 1] + 1));
                            green[xx + 1][yy + 1] = green[xx + 1][yy + 1] + Igreen.getInten(n * 5) * r4 * (1 / (green[xx + 1][yy + 1] + 1));
                            blue[xx + 1][yy + 1] = blue[xx + 1][yy + 1] + Iblue.getInten(n * 5) * r4 * (1 / (blue[xx + 1][yy + 1] + 1));
                        }
                    }

                }
            } else {
                n = 100;
                break;
            }
        }
    }

    private void iterateFunctions() {

        for (int i = 0; i < 8; i++) {

            double value = getFunctionValue(functions[i].substring(1, functions[i].length()));

            setMemory(functions[i].charAt(0), value);
            //System.out.println("A"+getMemory('A')+"    B"+getMemory('B')+"   C"+getMemory('C')+"    D"+getMemory('D')+"   E"+getMemory('E')+"    W"+getMemory('W')+"    X"+getMemory('X'));
        }
    }

    public double getFunctionValue(String s) {
        //Recursive alog to work out function values
        // System.out.println("Function: "+s);
        double value = 0;
        if (s.startsWith("[")) //[#ABC]   #=fodder  A =id   BC = inputs
        {
            int id = ((int) s.charAt(2)) - 65;

            int split = 0;
            if (s.substring(3).startsWith("[")) {
                int brackets = 1;
                for (int i = 4; i < s.length(); i++) {
                    //Count brackets to find the innermost function
                    if (s.substring(i, i + 1).equalsIgnoreCase("]")) {
                        brackets--;
                    } else if (s.substring(i, i + 1).equalsIgnoreCase("[")) {
                        brackets++;
                    }

                    if (brackets == 0) {
                        split = i + 1;
                        break;
                    }
                }

            } else {
                split = 4;
            }

            double x = getFunctionValue(s.substring(3, split));
            double y = getFunctionValue(s.substring(split, s.length() - 1));

            switch (id) {
                case 0:
                    value = x;  //A
                    break;
                case 1:
                    value = Math.pow(x, 2d); //B
                    break;
                case 2:
                    value = Math.floor(x); //C
                    break;
                case 3:
                    value = Math.round(x); //D
                    break;
                case 4:
                    value = Math.ceil(x); //E
                    break;
                case 5:
                    value = x; //F
                    break;
                case 6:
                    value = x + y; //G
                    break;
                case 7:
                    value = x + y; //H
                    break;
                case 8:
                    value = x - y; //I
                    break;
                case 9:
                    value = x - y; //J
                    break;
                case 10:
                    value = x * y; //K
                    break;
                case 11:
                    value = x * y; //L
                    break;
                case 12:
                    value = Math.pow(Math.abs(x), Math.abs(y)); //M
                    break;
                case 13:
                    value = Math.pow(Math.abs(y), Math.abs(x)); //N
                    break;
                case 14:
                    value = x / ((y == 0d) ? 0.000001d : y); //O
                    break;
                case 15:
                    value = y / ((x == 0d) ? 0.000001d : x); //P
                    break;
                case 16:
                    value = Math.pow(Math.abs(x), Math.abs(y)); //Q
                    break;
                case 17:
                    value = Math.pow(Math.abs(y), Math.abs(x)); //R
                    break;
                case 18:
                    value = Math.cos(x); //S
                    break;
                case 19:
                    value = Math.sin(x); //T
                    break;
                case 20:
                    value = rand.nextDouble(); //U
                    break;
                case 21:
                    value = (rand.nextDouble() - 0.5d) * x; //V
                    break;
                case 22:
                    value = -x; //W
                    break;
                case 23:
                    value = -Math.abs(x); //X
                    break;
                case 24:
                    value = Math.abs(x); //Y
                    break;
            }

            return value;
        } else {
            return getMemory(s.charAt(0));
        }
    }

    private void lockMemories() {
        //System.out.println("FORCING");
        setMemoryForce('W', getMemory('A'));
        setMemoryForce('X', getMemory('B'));
        setMemoryForce('Y', getMemory('C'));
    }

}
