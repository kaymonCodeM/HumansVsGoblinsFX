package creatures;

import items.Drop;
import land.Land;

public class Goblin extends Land {

    private int health = 6;
    private int strength = 2;
    private Drop drops;

    public Goblin(int y,int x) {
        super(y,x, "G");
        this.drops = new Drop(x,y);
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public Drop getDrops() {
        return drops;
    }

    public void setHealth(int health) {
        this.health = health;
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
