package com.mynameislaurence;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    public BufferedImage image;
    public Graphics imageGraphics;


    public int size;


    public ImagePanel() {
        size = 600;

        setSize(size, size);
        setVisible(true);
        setName("UI");
        image = new BufferedImage(size, size, BufferedImage.TYPE_3BYTE_BGR);

        imageGraphics = image.getGraphics();
        imageGraphics.setColor(Color.WHITE);
        imageGraphics.drawRect(0, 0, size, size);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);

    }


}