package creatures;

import controller.Combat;
import controller.Direction;
import creatures.move.MovePlayer;
import items.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Creature implements MovePlayer {


    private final Map<Equipment.Type,Equipment> inventory = new HashMap<>();

    private boolean alive = true;

    public Player(int y,int x) {
        super(y,x);
        super.setSymbol("P");
        super.setHealth(9);
        super.setStrength(3);

    }


    public Map<Equipment.Type, Equipment> getInventory() {
        return inventory;
    }


    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
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
    public boolean moveMe(Direction direction) {
        int curY = this.getY();
        int curX = this.getX();
        String destination = findClosestNodeDirection(curY + " " + curX, "*", direction);
        String goblinPosition = findClosestNodeDirection(curY+ " "+curX,"G",direction);
        if(!goblinPosition.isEmpty()){
            this.attack(GOBLINS.get(goblinPosition));
            return true;
        }else if (!destination.isEmpty()) {
            move(destination, this);
            return true;
        }
        return false;
    }

    @Override
    public boolean attack(Creature g) {
        String combatResult = Combat.playerVsGoblin(this, (Goblin) g);
        if (this.getHealth()<=0){
            CONTACT_TEXT.setText(combatResult);
            this.setAlive(false);
            return false;
        } else if (g.getHealth()<=0) {
            CONTACT_TEXT.setText(combatResult);
            ((Goblin) g).remove();
            GOBLINS.remove(g.getY()+ " "+g.getX());
        }
        return true;
    }
    public ArrayList<Equipment> findItem(Direction direction){
        String chestPosition = findClosestNodeDirection(this.getY() + " " + this.getX(), "C", direction);
        String dropPosition = findClosestNodeDirection(this.getY() + " " + this.getX(), "D", direction);
        String humanPosition = findClosestNodeDirection(this.getY() + " " + this.getX(), "H", direction);
        if (!chestPosition.isEmpty()) {
            CONTACT_TEXT.setText("You have made contact with a chest:");
            ArrayList<Equipment> items = CHESTS.get(chestPosition).getItemsList();
            if (!items.isEmpty()) {
                return items;
            } else {
                ALERT_TEXT.setText("The chest is empty! Hurry up Goblins are Coming!");
                return null;
            }
        } else if (!dropPosition.isEmpty()) {
            CONTACT_TEXT.setText("You have made contact with a drop:");
            ArrayList<Equipment> items = DROPS.get(dropPosition).getItemsList();
            if (!items.isEmpty()) {
                return items;
            } else {
                ALERT_TEXT.setText("The drop is empty! Hurry up Goblins are Coming!");
                return null;
            }
        } else if (!humanPosition.isEmpty()) {
            CONTACT_TEXT.setText("You have made contact with a human:");
            ArrayList<Equipment> items = HUMANS.get(humanPosition).getInventory().getItemsList();
            if (!items.isEmpty()) {
                return items;
            } else {
                ALERT_TEXT.setText("The Humans inventory is empty! Hurry up Goblins are Coming!");
                return null;
            }
        }
        return null;
    }


    public boolean isTrapped(){
        return findClosestNode(this.getY()+" "+ this.getX(),"*").isEmpty();
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
}
