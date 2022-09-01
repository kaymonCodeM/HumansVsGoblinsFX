package creatures;

import controller.Combat;
import items.Items;
import javafx.scene.paint.Color;

public class Human extends Creature implements RemoveNode{

    Items inventory = new Items(this.getY(),this.getX());

    public Human(int y,int x) {
        super(y,x);
        super.setSymbol("H");
        super.setHealth(8);
        super.setStrength(3);
    }


    public Items getInventory() {
        return inventory;
    }

    public Goblin findClosestGoblin(){
        return GOBLINS.get(findClosestNode(this.getY()+ " " + this.getX() ,"G"));
    }

    //Attack goblin and returns if human is alive or not
    @Override
    public boolean attack(Creature g) {
        String combatResult = Combat.goblinVsHuman(this, (Goblin) g);
        if (g.getHealth()<=0 && this.getHealth()<=0){
            CONTACT_TEXT.setText(combatResult);
            //remove Human
            this.remove();

            //remove goblin
            ((Goblin) g).remove();
            GOBLINS.remove(g.getY()+ " "+ g.getX());

            return false;

        } else if (this.getHealth()<=0) {
            CONTACT_TEXT.setText(combatResult);
            this.remove();

            return false;

        } else if (g.getHealth()<=0) {
            CONTACT_TEXT.setText(combatResult);
            ((Goblin) g).remove();
            GOBLINS.remove(g.getY()+ " "+ g.getX());
        }
        return true;
    }

    @Override
    public void remove(){
        NODES.get(this.getY()+" "+ this.getX()).setFill(Color.BLACK);
        NODES.get(this.getY()+" "+ this.getX()).setText("*");
        CONTACT_TEXT.setText(CONTACT_TEXT.getText()+ "You have only " + (HUMANS.size()-1)+ " humans left! ");
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "y = " + this.getY() + ", " + "x = " + this.getX() + "\n";
        result += "Health: " + this.getHealth() + "\n";
        result += "Strength: " + this.getStrength() + "\n\n";
        for(int i = 0; i<this.inventory.getItemsList().size(); i++){
            result += "Equipment: " + (i+1) +  "\n";
            result += this.inventory.getItemsList().get(i).toString() + "\n";
        }
        return result;
    }

}
