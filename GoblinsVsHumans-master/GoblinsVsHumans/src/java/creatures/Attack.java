package creatures;

import land.Land;

public interface Attack {
    //Creature attacks another creature result will be if the creature lives or not
    boolean attack(Land creature);
}
