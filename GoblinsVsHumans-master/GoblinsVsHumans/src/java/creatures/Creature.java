package creatures;

import controller.GameView;
import land.Land;

public abstract class Creature extends Land implements GameView {

    private int health;
    private int strength;
    public Creature(int y, int x) {
        super(y, x);
    }

    //Creature attacks another creature result will be if the creature lives or not
    abstract boolean attack(Creature creature);

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
