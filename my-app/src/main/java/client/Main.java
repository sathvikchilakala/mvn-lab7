package client;

import javax.swing.*;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main extends JFrame {

    private Subscriber subscriber;
    private DrawArea drawArea;

    // Constructor for Main that extends JFrame
    public Main() {
        // Initialize the Subscriber (this will handle MQTT communication)
        subscriber = new Subscriber();

        // Initialize the DrawArea which interacts with the Subscriber
        drawArea = new DrawArea(subscriber);

        // Set the DrawArea in Subscriber so it can draw circles
        subscriber.setDrawArea(drawArea);

        // Set up the JFrame properties
        setTitle("Subscriber - Receive and Draw Coordinates");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        add(drawArea, java.awt.BorderLayout.CENTER);  // Add the DrawArea (UI) to the frame
        setVisible(true);  // Make the frame visible

        // Start the subscription process in the Subscriber
        subscriber.subscribeToCoordinates();
    }

    // Main method to run the application
    public static void main(String[] args) {
        new Main();
    }
}
