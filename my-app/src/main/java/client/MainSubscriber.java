package client;

import javax.swing.*;


public class MainSubscriber extends JFrame {

    private Subscriber subscriber;
    private DrawPanelSubscriber drawPanelSubscriber;

    // Constructor for Main that extends JFrame
    public MainSubscriber() {
        // Initialize the Subscriber to handle MQTT communication
        subscriber = new Subscriber();

        // Initialize the DrawArea which interacts with the Subscriber
        drawPanelSubscriber = new DrawPanelSubscriber(subscriber);

        // Set the DrawArea in Subscriber so it can draw circles
        subscriber.setDrawArea(drawPanelSubscriber);

        // Set up the JFrame properties
        setTitle("Subscriber - Receive and Draw Coordinates");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        add(drawPanelSubscriber, java.awt.BorderLayout.CENTER);
        setVisible(true);

        // Start the subscription process in the Subscriber
        subscriber.subscribeToCoordinates();
    }

    // Main method to run the application
    public static void main(String[] args) {
        new MainSubscriber();
    }
}
