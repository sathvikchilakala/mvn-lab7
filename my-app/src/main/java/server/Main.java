package server;

import javax.swing.*;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main extends JFrame {

    private Publisher publisher;
    private DrawArea drawArea;

    // Constructor for Main
    public Main() {
        // Initialize the Publisher that handles MQTT communication
        try {
            publisher = new Publisher();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Initialize the DrawArea which interacts with the Publisher
        drawArea = new DrawArea(publisher);

        // Set the DrawArea in Publisher so it can draw circles
        publisher.setDrawArea(drawArea);

        // Set up the JFrame properties
        setTitle("Publisher - Draw and Send Coordinates");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        add(drawArea, java.awt.BorderLayout.CENTER);  // Add the DrawArea (UI) to the frame
        setVisible(true);  // Make the frame visible

        // Start the subscribing process in the Publisher
        publisher.subscribeToCoordinates();
    }

    // Main method to run the application
    public static void main(String[] args) {
        new Main();
    }
}
