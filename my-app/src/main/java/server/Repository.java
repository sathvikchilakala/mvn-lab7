package server;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Array;

class Repository {
    private List<Coordinate> coordinates;

    public Repository(){
        this.coordinates = new ArrayList<>();
    }


    public void addCoordinate(int x, int y){
        coordinates.add(new Coordinate(x, y));
    }

//    public int[] ShowRepo(){
//        int[] coords = {this.x,this.y};
//        return(coords);
//    }

    public List<Coordinate> getCoordinates() {
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