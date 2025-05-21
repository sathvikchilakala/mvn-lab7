package server;

import org.eclipse.paho.client.mqttv3.*;

public class Publisher {
    private static final String BROKER = "tcp://test.mosquitto.org:1883"; // MQTT Broker URL
    private static final String TOPIC = "cal-poly/csc/309"; // MQTT Topic
    private static final String CLIENT_ID = "jgs-publisher";
    private MqttClient mqttClient;
    private RepositoryPublisher repositoryPublisher;
    private DrawAreaPublisher drawAreaPublisher;

    // Constructor for the Publisher class
    public Publisher() throws MqttException {
        repositoryPublisher = new RepositoryPublisher();

        // Setup MQTT client and connect to the broker
        mqttClient = new MqttClient(BROKER, CLIENT_ID);
        mqttClient.connect();
        System.out.println("Connected to BROKER: " + BROKER);
    }


    // Publish coordinates to the MQTT broker
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

    // Method to subscribe to the topic and receive coordinates
    public void subscribeToCoordinates() {
        try {
            mqttClient.subscribe(TOPIC);
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    // Handle connection loss
                    System.out.println("Connection lost: " + cause);
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) {
                    // Parse the received message
                    String[] coords = new String(message.getPayload()).split(",");
                    int x = Integer.parseInt(coords[0]);
                    int y = Integer.parseInt(coords[1]);

                    // Draw the circle based on the received coordinates
                    drawAreaPublisher.addCoordinateToDraw(x, y);
                    System.out.println("Received Coordinates: " + x + "," + y);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Delivered complete: " + token.getMessageId());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public RepositoryPublisher getRepository() {
        return repositoryPublisher;
    }

    // Set the DrawArea (called from Main.java)
    public void setDrawArea(DrawAreaPublisher drawAreaPublisher) {
        this.drawAreaPublisher = drawAreaPublisher;
    }
}
