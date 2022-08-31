package items;

import land.Land;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Drop extends Land implements EquipmentPool {
    ArrayList<Equipment> drops = new ArrayList<Equipment>();

    public Drop(int y,int x) {
        super(y,x);
        super.setSymbol("D");

        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.drops.add(selectRandomEquipment());
        }
    }

    public ArrayList<Equipment> getDrops() {
        return drops;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i=0;i<drops.size();i++){
            result += "ELEMENT: " + (i+1) + "\n";
            result += drops.get(i).toString() + "\n\n";
        }
        return result;
    }
}
