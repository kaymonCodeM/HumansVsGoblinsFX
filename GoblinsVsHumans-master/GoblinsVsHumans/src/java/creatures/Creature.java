package creatures;

import controller.Direction;
import controller.GameView;
import javafx.scene.text.Text;
import land.Land;

import java.util.Arrays;

public abstract class Creature extends Land implements GameView {

    private int health;
    private int strength;
    public Creature(int y, int x) {
        super(y, x);
    }

    //Creature attacks another creature result will be if the creature lives or not
    abstract boolean attack(Creature creature);
    public String findClosestNodeDirection(String curKey, String symbol, Direction direction){
        int[] location = Arrays.stream(curKey.split(" ")).mapToInt(Integer::parseInt).toArray();
        String resultKey = "";
        if(NODES.get((location[0]-1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.NORTH){
            resultKey += (location[0]-1)+ " "+ location[1];
        }else if(NODES.get((location[0]+1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.SOUTH){
            resultKey += (location[0]+1)+ " "+ location[1];
        }else if(NODES.get(location[0]+ " "+ (location[1]-1)).getText().contains(symbol) && direction==Direction.WEST){
            resultKey += location[0]+ " "+ (location[1]-1);
        }else if(NODES.get(location[0]+ " "+ (location[1]+1)).getText().contains(symbol) && direction==Direction.EAST){
            resultKey += location[0]+ " "+ (location[1]+1);
        }
        return resultKey;
    }

    public String findClosestNode(String curKey,String symbol){
        Direction[] locations = {Direction.NORTH,Direction.SOUTH,Direction.WEST,Direction.EAST};
        String destination = "";
        for (int i=0;i<4;i++){
            destination = findClosestNodeDirection(curKey,symbol,locations[i]);
            if (!destination.isEmpty()){
                return destination;
            }
        }
        return destination;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
