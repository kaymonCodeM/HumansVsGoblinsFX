package creatures;

import items.Equipment;
import items.EquipmentPool;
import land.Land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Human extends Land implements EquipmentPool {

    private int health = 8;
    private int strength = 3;
    private ArrayList<Equipment> inventory = new ArrayList<>();

    public Human(int y,int x) {
        super(y,x, "H");
        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.inventory.add(selectRandomEquipment());
        }
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
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
        for(int i=0;i<inventory.size();i++){
            result += "Equipment: " + (i+1) +  "\n";
            result += this.inventory.get(i).toString() + "\n";
        }
        return result;
    }
}
