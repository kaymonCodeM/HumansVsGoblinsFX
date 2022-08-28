package creatures;

import items.Equipment;
import land.Land;

import java.util.HashMap;
import java.util.Map;

public class Player extends Land {


    private int Health = 8;
    private int strength = 3;

    private Map<Equipment.Type,Equipment> inventory = new HashMap<>();

    public Player(int y,int x) {
        super(y,x,"P");
    }

    public int getHealth() {
        return Health;
    }

    public int getStrength() {
        return strength;
    }

    public Map<Equipment.Type, Equipment> getInventory() {
        return inventory;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }


    public void attachEquipment(Equipment equipment){
        //A player can only attach one attack power
        if (equipment.getRole() == Equipment.Role.ATTACK){
            for (Equipment.Type key: this.inventory.keySet()){
                if (this.inventory.get(key).getRole() == Equipment.Role.ATTACK) {
                    setHealth(this.getHealth()-this.inventory.get(key).getHealth());
                    setStrength(this.getStrength()-this.inventory.get(key).getStrength());
                    this.inventory.remove(key);
                    break;
                }
            }
            //Remove the current defend equipment if the new equipment is of the same type
        }else if (this.inventory.containsKey(equipment.getType())){
            Equipment myEquipment = this.inventory.get(equipment.getType());
            setHealth(this.getHealth()-myEquipment.getHealth());
            setStrength(this.getStrength()-myEquipment.getStrength());
        }

        //Equip the new equipment
        setStrength(this.getStrength()+equipment.getStrength());
        setHealth(this.getHealth()+equipment.getHealth());
        inventory.put(equipment.getType(),equipment);
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "y = " + this.getY() + ", " + "x = " + this.getX() + "\n";
        result += "Health: " + this.getHealth() + "\n";
        result += "Strength: " + this.getStrength() + "\n\n";
        result += "Equipments: " + "\n";
        if(this.inventory.isEmpty()){
            result += "No Equipments are attached yet.";
        }
        for(Equipment.Type key: this.inventory.keySet()){
            result += this.inventory.get(key).toString() + "\n";
        }
        return result;
    }

    public void playerAttackGoblin(Player player,Goblin goblin){

    }
}
