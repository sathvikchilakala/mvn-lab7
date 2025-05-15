package client;

import java.lang.reflect.Array;

class Repository {
    int x;
    int y;

    public Repository(){
        this.x = 0;
        this.y = 0;
    }

    public Repository(int X, int Y){
        this.x = X;
        this.y = Y;
    }

    public int[] ShowRepo(){
        int[] coords = {this.x,this.y};
        return(coords);
    }

}