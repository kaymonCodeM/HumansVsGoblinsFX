package creatures;

import items.Equipment;
import items.EquipmentPool;
import land.Land;

import java.util.HashMap;
import java.util.Map;

public class Human extends Land implements EquipmentPool {

    public static final String RESET = "\033[0m";
    public static final String BLUE_BOLD = "\033[1;34m";
    private int health = 8;
    private int strength = 3;
    private Map<Integer,Equipment> inventory = new HashMap();

    public Human(int[] position) {
        super(position, BLUE_BOLD +"H" + RESET);
        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.inventory.put(i,selectRandomEquipment());
        }
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public Map<Integer, Equipment> getInventory() {
        return inventory;
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
        for(Integer key: this.inventory.keySet()){
            result += "Equipment: " + key +  "\n";
            result += this.inventory.get(key).toString() + "\n";
        }
        return result;
    }
}
