package creatures.move;

import controller.GameView;
import javafx.scene.text.Text;
import land.Land;

import java.util.Arrays;

public interface Move<T> extends GameView {

    boolean moveMe(T t);

    default void move(String direction, Land land){
        swapLand(land.getY()+ " " + land.getX(),direction);
        int[] directionToInt = Arrays.stream(direction.split(" ")).mapToInt(Integer::parseInt).toArray();
        land.setY(directionToInt[0]);
        land.setX(directionToInt[1]);
    }

    default void swapLand(String from, String to){

        double fromY = NODES.get(from).getY();
        double fromX = NODES.get(from).getX();

        double toY = NODES.get(to).getY();
        double toX = NODES.get(to).getX();

        NODES.get(from).setY(toY);
        NODES.get(from).setX(toX);

        NODES.get(to).setY(fromY);
        NODES.get(to).setX(fromX);

        Text newFrom = NODES.get(from);
        Text newTo = NODES.get(to);


        NODES.put(from,newTo);
        NODES.put(to,newFrom);

    }
}
