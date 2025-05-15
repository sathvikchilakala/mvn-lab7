package server;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        DrawArea drawArea = new DrawArea(this);
        add(drawArea);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(400, 400);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }

}