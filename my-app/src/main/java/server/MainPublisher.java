package server;

import javax.swing.*;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainPublisher extends JFrame {

    private Publisher publisher;
    private DrawAreaPublisher drawAreaPublisher;

    // Constructor for Main
    public MainPublisher() {
        // Initialize the Publisher that handles MQTT communication
        try {
            publisher = new Publisher();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Initialize the DrawArea which interacts with the Publisher
        drawAreaPublisher = new DrawAreaPublisher(publisher);

        // Set the DrawArea in Publisher so it can draw circles
        publisher.setDrawArea(drawAreaPublisher);

        // Set up the JFrame properties
        setTitle("Publisher - Draw and Send Coordinates");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.BorderLayout());
        add(drawAreaPublisher, java.awt.BorderLayout.CENTER);
        setVisible(true);

        // Start the subscribing process in the Publisher
        publisher.subscribeToCoordinates();
    }

    // Main method to run the application
    public static void main(String[] args) {
        new MainPublisher();
    }
}
