package client;

import java.util.ArrayList;
import java.util.List;

class RepositorySubscriber {
    private List<RepositorySubscriber.Coordinate> coordinates;

    public RepositorySubscriber(){
        this.coordinates = new ArrayList<>();
    }

    public void addCoordinate(int x, int y){
        coordinates.add(new RepositorySubscriber.Coordinate(x, y));
    }

    public List<RepositorySubscriber.Coordinate> getCoordinates() {
        return coordinates;
    }

    public void clear() {
        coordinates.clear();
    }

    public static class Coordinate {
        public int x, y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}