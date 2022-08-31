package creatures;

import controller.Combat;
import controller.GameView;
import items.Equipment;
import items.EquipmentPool;
import javafx.scene.paint.Color;
import land.Land;

import java.util.ArrayList;

public class Human extends Land implements EquipmentPool, GameView, Attack {

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
    

    //Attack goblin and returns if human is alive or not
    @Override
    public boolean attack(Land g) {
        String combatResult = Combat.goblinVsHuman(this, (Goblin) g);
        if (((Goblin) g).getHealth()<=0 && this.getHealth()<=0){
            contactText.setText(combatResult);
            //remove Human
            this.remove();

            //remove goblin
            ((Goblin) g).remove();
            goblins.remove(g.getY()+ " "+ g.getX());

            return false;

        } else if (this.getHealth()<=0) {
            contactText.setText(combatResult);
            this.remove();

            return false;

        } else if (((Goblin) g).getHealth()<=0) {
            contactText.setText(combatResult);
            ((Goblin) g).remove();
            goblins.remove(g.getY()+ " "+ g.getX());
        }
        return true;
    }

    public void remove(){
        locateNodes.get(this.getY()+" "+ this.getX()).setFill(Color.BLACK);
        locateNodes.get(this.getY()+" "+ this.getX()).setText("*");
        contactText.setText(contactText.getText()+ "You have only " + (humans.size()-1)+ " humans left! ");
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
