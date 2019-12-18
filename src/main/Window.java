package main;

import javax.swing.*;
import java.awt.*;

class Window {
    Window(int width, int height, String title, Image iconImage, Game game) {
        JFrame frame = new JFrame(title);
        frame.add(game);
        frame.setSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(iconImage);
        frame.setVisible(true);
    }
}
