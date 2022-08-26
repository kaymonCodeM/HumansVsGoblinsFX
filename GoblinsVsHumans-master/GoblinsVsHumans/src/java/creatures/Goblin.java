package creatures;

import items.Drop;
import land.Land;

public class Goblin extends Land {

    public static final String RESET = "\033[0m";

    public static final String GREEN_BOLD = "\033[1;32m";
    private int health = 6;
    private int strength = 2;
    private Drop drops;

    public Goblin(int[] position) {
        super(position, GREEN_BOLD + "G" + RESET);
        this.drops = new Drop(position);
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
        result += "Position: " + "x = " + this.getPosition()[1] + ", " + "y = " + this.getPosition()[0] + "\n";
        result += "Health: " + this.getHealth() + "\n";
        result += "Strength: " + this.getStrength() + "\n\n";
        result += "DROPS: " + "\n\n";
        result += drops.toString();
        return result;
    }
}
