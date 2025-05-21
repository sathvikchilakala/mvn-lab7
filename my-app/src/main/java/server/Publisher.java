package server;

import org.eclipse.paho.client.mqttv3.*;

public class Publisher {
    private static final String BROKER = "tcp://test.mosquitto.org:1883"; // MQTT Broker URL
    private static final String TOPIC = "cal-poly/csc/309"; // MQTT Topic
    private static final String CLIENT_ID = "jgs-publisher";
    private MqttClient mqttClient;
    private Repository repository;
    private DrawArea drawArea;

    // Constructor for the Publisher class
    public Publisher() throws MqttException {
        repository = new Repository();

        // Setup MQTT client and connect to the broker
        mqttClient = new MqttClient(BROKER, CLIENT_ID);
        mqttClient.connect();
        System.out.println("Connected to BROKER: " + BROKER);
    }

    // Method to start the publishing process
    public void startPublishing() {
        try {
            int counter = 0;
            while (true) {
                String content = "this is message " + counter;
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(2);

                if (mqttClient.isConnected()) {
                    mqttClient.publish(TOPIC, message);
                    System.out.println("Message published: " + content);
                }
                counter++;
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // Publish coordinates (x, y) to the MQTT broker
    public void publishCoordinates(int x, int y) {
        try {
            String messageContent = x + "," + y;
            MqttMessage message = new MqttMessage(messageContent.getBytes());
            message.setQos(2);
            if (mqttClient.isConnected()) {
                mqttClient.publish(TOPIC, message);
                System.out.println("Coordinates published: " + messageContent);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Repository getRepository() {
        return repository;
    }

    // Set the DrawArea (called from Main.java)
    public void setDrawArea(DrawArea drawArea) {
        this.drawArea = drawArea;
    }
}
