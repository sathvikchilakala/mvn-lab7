package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawAreaPublisher extends JPanel {
    private Publisher publisher;
    private RepositoryPublisher repositoryPublisher;
    private List<RepositoryPublisher.Coordinate> coordinatesToDraw;

    // Constructor where Publisher is passed for interaction
    public DrawAreaPublisher(Publisher publisher) {
        this.publisher = publisher;
        this.repositoryPublisher = publisher.getRepository();
        this.coordinatesToDraw = new ArrayList<>();

        // Mouse listener to detect clicks and send coordinates
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // Store the coordinate in the repository
                repositoryPublisher.addCoordinate(x, y);

                // Publish the coordinates to MQTT
                publisher.publishCoordinates(x, y);
                addCoordinateToDraw(x,y);
                System.out.println("Published Coordinates: " + x + "," + y);
            }
        });
    }

    // Override the paintComponent method to draw circles
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all stored coordinates as circles
        g.setColor(Color.RED);
        for (RepositoryPublisher.Coordinate coordinate : coordinatesToDraw) {
            g.fillOval(coordinate.x - 10, coordinate.y - 10, 20, 20); // Draw circle at the location
        }
    }

    // Method to add a coordinate to the list and trigger a repaint
    public void addCoordinateToDraw(int x, int y) {
        coordinatesToDraw.add(new RepositoryPublisher.Coordinate(x, y));
        repaint(); // Repaint the panel for the new circle
    }

}
