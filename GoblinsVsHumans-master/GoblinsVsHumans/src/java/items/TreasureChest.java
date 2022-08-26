package items;

import land.Land;

import java.util.HashMap;
import java.util.Map;

public class TreasureChest extends Land implements EquipmentPool {

    public static final String RESET = "\033[0m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    Map<Integer,Equipment> chest = new HashMap();

    public TreasureChest(int[] position) {
        super(position, YELLOW_BOLD + "C" + RESET);

        int count = (int)(Math.random()*3)+1;
        for(int i=1; i<=count;i++){
            this.chest.put(i,selectRandomEquipment());
        }
    }

    public Map<Integer, Equipment> getChest() {
        return chest;
    }


    @Override
    public String toString() {
        String result = "";
        for (Integer key: chest.keySet()){
            result += "ELEMENT: " + key + "\n";
            result += chest.get(key).toString() + "\n";
        }
        return result;
    }
}
