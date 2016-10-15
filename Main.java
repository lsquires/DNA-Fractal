package com.mynameislaurence;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {



    public static void main(String[] args) {

    ImagePanel panel = new ImagePanel();
    panel.setVisible(true);


    new Main();




    //System.out.println(w.getFunctionValue("[#K[#KA[#TAA]][#TAA]]"));
    }
    public Main() {
        normal();
       // different();
    }



    public void different() {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(100);
        list.add(108);
        list.add(136);
        list.add(138);
        list.add(149);
        list.add(150);
        list.add(165);
        list.add(173);
        list.add(184);
        list.add(196);
        list.add(203);
        list.add(210);
        list.add(216);
        list.add(227);
        list.add(231);
        list.add(279);
        list.add(280);
        list.add(304);




        Scanner scanner = null;
        for(int i = 0 ; i < list.size();i++)
        {
            File outputfolder = new File("E:\\Wallpaper6\\images" + "\\");
            outputfolder.mkdirs();
            outputfolder = new File("E:\\Wallpaper6\\dna" + "\\");
            outputfolder.mkdirs();
            int i2 = 0;
            String[] s2 = new String[64];
            try {
                scanner = new Scanner(new File("E:\\Wallpaper5\\dna" + 0 + "\\img00" + list.get(i) + ".txt"));
                System.out.println("E:\\Wallpaper5\\dna" + 0 + "\\img00" + list.get(i) + ".txt");
            } catch (FileNotFoundException e) {e.printStackTrace();}
            while (scanner.hasNextLine()) {
                s2[i2] = scanner.nextLine();
                i2++;
            }

            Wallpaper w = new Wallpaper(s2.clone());
            BufferedImage img = w.drawWallPaper(600, 600);
            File outputfile = new File("E:\\Wallpaper6\\images"  + "\\img00" + (i+100) + ".png");

            try {
                ImageIO.write(img, "png", outputfile);
                BufferedWriter out = new BufferedWriter(new FileWriter("E:\\Wallpaper6\\dna"  + "\\img00" +  (i+100) + ".txt"));
                for (int i22 = 0; i22 < 40; i22++) {
                    out.write(s2[i22]);
                    out.newLine();
                }
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    public void normal() {
        int num = 0;
        Scanner scanner = null;
        Scanner scanner2 = null;

            File outputfolder = new File("E:\\Wallpaper6\\images" + num + "\\");
            outputfolder.mkdirs();
            outputfolder = new File("E:\\Wallpaper6\\dna" + num + "\\");
            outputfolder.mkdirs();
         for (int i = 100; i < 9999; i++) {
             System.out.println("( "+i+" ) =");
            String[] s2 = new String[64];
            int i2 = 0;
            int ran = 0;
            int mutationAmount;
            File folder = new File("E:\\Wallpaper6\\dna");
            File[] list = folder.listFiles();
                try {
                     ran = (int)(Math.floor(list.length * Math.random()));
                    scanner = new Scanner(list[ran]);
                } catch (FileNotFoundException e) {e.printStackTrace();}
                while (scanner.hasNextLine()) {
                    s2[i2] = scanner.nextLine();
                    i2++;
                }

                if(Math.random()>0.2)
                {
                    mutationAmount =(int)(40*Math.random());
                    //Splice
                    i2= 0;
                    double ratio = 0.5;
                    try {
                         int ran2 = (int)(Math.floor(list.length * Math.random()));
                        scanner2 = new Scanner(list[ran2]);
                        System.out.println("Splicing: "+list[ran].getAbsolutePath() + " and  "+list[ran2].getAbsolutePath()+ " by "+ratio);
                    } catch (FileNotFoundException e) {e.printStackTrace();}
                    while (scanner2.hasNextLine()) {
                        String ss =  scanner2.nextLine();
                        if(ss.startsWith("A") || ss.startsWith("B") || ss.startsWith("L") || ss.startsWith("M")) {
                            ratio = 0.05;
                        }else
                        {
                            ratio = 0.05;
                        }
                        if(Math.random()>ratio) {
                            s2[i2] = ss ;
                        }
                        i2++;
                    }
                }else
                {
                    //Mutate

                    mutationAmount = (int)(10+Math.random()*Math.random()*100 + 40*Math.random()+Math.random()*Math.random()*100 + 40*Math.random());
                    System.out.println("Mutating: "+list[ran].getAbsolutePath()+list[ran].getName() + " by "+mutationAmount);
                }




                    String[] s = s2.clone();
                    s = mutate(s, mutationAmount);
                    Wallpaper w = new Wallpaper(s.clone());
                    BufferedImage img = w.drawWallPaper(150, 150);
                    File outputfile = new File("E:\\Wallpaper6\\images" + num + "\\img00" + i + ".png");
                    try {
                        ImageIO.write(img, "png", outputfile);
                        BufferedWriter out = new BufferedWriter(new FileWriter("E:\\Wallpaper6\\dna" + num + "\\img00" + i + ".txt"));
                        for (int i22 = 0; i22 < 40; i22++) {
                            out.write(s[i22]);
                            out.newLine();
                        }
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (outputfile.length() < 4000) {
                        System.out.println("Redoing");
                        i--;
                    }
                }
               // num++;

        }


    private String[] mutate(String[] l, int m) {

        for(int i = 0 ; i < m;i++)
            {
            Random random = new Random();
            int line = random.nextInt(40);
            String s = l[line];
                StringBuilder sb = new StringBuilder(s);
            int cha = random.nextInt(s.length());
             String sub = s.substring(cha,cha+1);
                if(sub.equalsIgnoreCase("#") || sub.equalsIgnoreCase("]"))
                {

                }else
                if(sub.equalsIgnoreCase("["))
                {
                    int possibles = 0;
                    int cha1 = (int)(s.substring(cha+3,cha+4).charAt(0));
                    if(cha1 >= 65 && cha1 <= 90)
                        {
                            possibles++;
                        }
                    cha1 = (int)(s.substring(cha+4,cha+5).charAt(0));
                    if(cha1 >= 65 && cha1 <= 90)
                        {
                            possibles++;
                        }
                    if(possibles==2)
                    {
                        StringBuilder rs = new StringBuilder();
                        rs.insert(0,(char)(65+random.nextInt(25)));
                        rs.insert(0,(char)(65+random.nextInt(25)));
                        if(random.nextBoolean())
                        {
                            sb.insert(cha+4,"]");
                            sb.insert(cha+3,"[#"+rs.toString());
                        }else
                        {
                            sb.insert(cha+5,"]");
                            sb.insert(cha+4,"[#"+rs.toString());
                        }
                    }else
                    if(possibles==1)
                    {
                        StringBuilder rs = new StringBuilder();
                        rs.insert(0,(char)(65+random.nextInt(25)));
                        rs.insert(0,(char)(65+random.nextInt(25)));
                        cha1 = (int)(s.substring(cha+3,cha+4).charAt(0));
                        if(cha1 >= 65 && cha1 <= 90)
                        {
                            sb.insert(cha+4,"]");
                            sb.insert(cha+3,"[#"+rs.toString());
                        }
                        cha1 = (int)(s.substring(cha+4,cha+5).charAt(0));
                        if(cha1 >= 65 && cha1 <= 90)
                        {
                            sb.insert(cha+5,"]");
                            sb.insert(cha+4,"[#"+rs.toString());
                        }
                    }

                }else
                if((int)sub.charAt(0) >= 48 && (int)sub.charAt(0) <= 57) // number
                {
                    sb.setCharAt(cha,(char)(48+random.nextInt(10)));
                }else
                if((int)sub.charAt(0) >= 65 && (int)sub.charAt(0) <= 90) // letter
                {
                    sb.setCharAt(cha,(char)(65+random.nextInt(25)));
                }else
            if(sub.equalsIgnoreCase("+") || sub.equalsIgnoreCase("-"))
                {
                    if(random.nextBoolean())
                    {
                      sb.setCharAt(cha,'+');
                    }else
                    {
                        sb.setCharAt(cha,'-');
                    }
                }

            s = sb.toString();
                l[line] = s;
            }
        return l;
    }
}
