package controller;

import creatures.Goblin;
import creatures.Human;
import items.Drop;
import items.TreasureChest;
import javafx.scene.text.Text;
import land.Land;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface GameView {
    Map<String, Text> locateNodes = new HashMap<>();

    Map<String, Human> humans = new HashMap<>();

    Map<String,Goblin> goblins = new HashMap<>();

    Map<String, TreasureChest> chests = new HashMap<>();

    Map<String, Drop> drops = new HashMap<>();

    Text contactText = new Text();


    Text alertText = new Text();



    default void move(String direction, Land land){
        swapLand(land.getY()+ " " + land.getX(),direction);
        int[] directionToInt = Arrays.stream(direction.split(" ")).mapToInt(Integer::parseInt).toArray();
        land.setY(directionToInt[0]);
        land.setX(directionToInt[1]);
    }

    default void swapLand(String from, String to){

        double fromY = locateNodes.get(from).getY();
        double fromX = locateNodes.get(from).getX();

        double toY = locateNodes.get(to).getY();
        double toX = locateNodes.get(to).getX();

        locateNodes.get(from).setY(toY);
        locateNodes.get(from).setX(toX);

        locateNodes.get(to).setY(fromY);
        locateNodes.get(to).setX(fromX);

        Text newFrom = locateNodes.get(from);
        Text newTo = locateNodes.get(to);


        locateNodes.put(from,newTo);
        locateNodes.put(to,newFrom);

    }


    default String findClosestNodeDirection(String curKey,String symbol,Direction direction){
        int[] location = Arrays.stream(curKey.split(" ")).mapToInt(Integer::parseInt).toArray();
        String resultKey = "";
        if(locateNodes.get((location[0]-1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.NORTH){
            resultKey += (location[0]-1)+ " "+ location[1];
        }else if(locateNodes.get((location[0]+1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.SOUTH){
            resultKey += (location[0]+1)+ " "+ location[1];
        }else if(locateNodes.get(location[0]+ " "+ (location[1]-1)).getText().contains(symbol) && direction==Direction.WEST){
            resultKey += location[0]+ " "+ (location[1]-1);
        }else if(locateNodes.get(location[0]+ " "+ (location[1]+1)).getText().contains(symbol) && direction==Direction.EAST){
            resultKey += location[0]+ " "+ (location[1]+1);
        }
        return resultKey;
    }

    default String findClosestNode(String curKey,String symbol){
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
}
