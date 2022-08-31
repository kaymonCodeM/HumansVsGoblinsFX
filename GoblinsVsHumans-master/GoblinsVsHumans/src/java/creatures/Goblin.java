package creatures;

import controller.Combat;
import controller.Direction;
import controller.GameView;
import items.Drop;
import javafx.scene.paint.Color;
import land.Land;

public class Goblin extends Land implements GameView,Attack {

    private int health = 6;
    private int strength = 2;
    private Drop drop;

    public Goblin(int y,int x) {
        super(y,x, "G");
        this.drop = new Drop(x,y);
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public Drop getDrops() {
        return drop;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public boolean attack(Land player) {
        String combatResult = Combat.playerVsGoblin((Player)player, this);
        if (((Player) player).getHealth() <= 0) {
            contactText.setText(combatResult);
            ((Player) player).setAlive(false);
        } else if (this.getHealth() <= 0) {
            contactText.setText(combatResult);
            this.remove();
            return false;
        }
        return true;
    }

    //Moves the goblin and returns if the goblin moves
    public boolean moveToPlayer(Player player){
        int curY = this.getY();
        int curX = this.getX();

        String curPlayer = findClosestNode(curY + " "+ curX,"P");
        if (!curPlayer.isEmpty()) {
           return attack(player);

        }else if (player.getY()<curY && !findClosestNodeDirection(curY + " "+ curX,"*", Direction.NORTH).isEmpty()){
            move((curY-1)+ " "+ curX,this);
        } else if (player.getY()>curY && !findClosestNodeDirection(curY + " "+ curX,"*",Direction.SOUTH).isEmpty()) {
            move((curY+1)+ " "+ curX,this);
        }else if (player.getX()<curX && !findClosestNodeDirection(curY + " "+ curX,"*",Direction.WEST).isEmpty()) {
            move(curY+ " "+ (curX-1),this);
        }else if (player.getX()>curX && !findClosestNodeDirection(curY + " "+ curX,"*",Direction.EAST).isEmpty()) {
            move(curY+ " "+ (curX+1),this);
        }
        return true;
    }
    public void remove(){
        int y = this.getY();
        int x = this.getX();
        this.getDrops().setY(y);
        this.getDrops().setX(x);

        //Place Drop when goblin dies
        drops.put(y+" "+ x,this.getDrops());
        locateNodes.get(y+" "+ x).setFill(Color.DARKRED);
        locateNodes.get(y+" "+ x).setText("D");
        contactText.setText(contactText.getText() + (goblins.size()-1) + " goblins remain. ");
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "y = " + this.getY() + ", " + "x = " + this.getX() + "\n";
        result += "Health: " + this.getHealth() + "\n";
        result += "Strength: " + this.getStrength() + "\n\n";
        result += "DROPS: " + "\n\n";
        result += drops.toString();
        return result;
    }

}
