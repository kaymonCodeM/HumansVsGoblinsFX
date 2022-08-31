package items;

import land.Land;

import java.util.ArrayList;

public class TreasureChest extends Land implements EquipmentPool {
    ArrayList<Equipment> chest = new ArrayList<>();

    public TreasureChest(int y,int x) {
        super(y,x);
        super.setSymbol("C");

        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.chest.add(selectRandomEquipment());
        }
    }

    public ArrayList<Equipment> getChest() {
        return chest;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i=0;i<chest.size();i++){
            result += "ELEMENT: " + (i+1) + "\n";
            result += chest.get(i).toString() + "\n";
        }
        return result;
    }
}
