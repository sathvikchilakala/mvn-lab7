package client;

import org.eclipse.paho.client.mqttv3.*;

public class Subscriber {
    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "cal-poly/csc/309";
    private static final String CLIENT_ID = "jgs-subscriber";
    private MqttClient mqttClient;
    private RepositorySubscriber repositorySubscriber;
    private DrawAreaSubscriber drawAreaSubscriber;

    // Constructor for the Subscriber class
    public Subscriber() {
        repositorySubscriber = new RepositorySubscriber();
        drawAreaSubscriber = new DrawAreaSubscriber(this);

        try {
            mqttClient = new MqttClient(BROKER, CLIENT_ID);
            mqttClient.connect();
            System.out.println("Connected to BROKER: " + BROKER);
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
                    drawAreaSubscriber.addCoordinateToDraw(x, y);
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

    // Method to publish coordinates back to the MQTT broker
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

    public RepositorySubscriber getRepository() {
        return repositorySubscriber;
    }

    // Set the DrawArea
    public void setDrawArea(DrawAreaSubscriber drawAreaSubscriber) {
        this.drawAreaSubscriber = drawAreaSubscriber;
    }

}
