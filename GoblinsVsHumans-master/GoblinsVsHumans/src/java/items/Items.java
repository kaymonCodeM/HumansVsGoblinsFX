package items;

import land.Land;

import java.util.ArrayList;

public class Items extends Land implements EquipmentPool {

    ArrayList<Equipment> itemsList = new ArrayList<>();

    public Items(int y, int x) {
        super(y, x);

        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.itemsList.add(selectRandomEquipment());
        }
    }

    public ArrayList<Equipment> getItemsList() {
        return itemsList;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i< itemsList.size(); i++){
            result += "ELEMENT: " + (i+1) + "\n";
            result += itemsList.get(i).toString() + "\n\n";
        }
        return result;
    }
}
