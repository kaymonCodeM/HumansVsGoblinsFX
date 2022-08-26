package items;

public interface EquipmentPool {
    Equipment[] attack = {new Equipment("Sword of Darkness",Equipment.Role.ATTACK,Equipment.Type.SWORD,-2,+5),
                          new Equipment("Axe of Despair",Equipment.Role.ATTACK,Equipment.Type.AXE,-1,4),
                          new Equipment("Thor's Hammer",Equipment.Role.ATTACK,Equipment.Type.HAMMER,1,3),
                          new Equipment("Slasher",Equipment.Role.ATTACK,Equipment.Type.SWORD,1,3),
                          new Equipment("Butcher",Equipment.Role.ATTACK,Equipment.Type.AXE,0,4),
                          new Equipment("War Hammer",Equipment.Role.ATTACK,Equipment.Type.HAMMER,0,3),
                          new Equipment("Sword of heavenly Light",Equipment.Role.ATTACK,Equipment.Type.SWORD,2,3),
                          new Equipment("God destroyer",Equipment.Role.ATTACK,Equipment.Type.AXE,-2,6),
                          new Equipment("Breaker of Mountains",Equipment.Role.ATTACK,Equipment.Type.HAMMER,-1,5)};
    Equipment[] defend = {new Equipment("Shield of Destiny",Equipment.Role.DEFEND,Equipment.Type.SHIELD,4,0),
                          new Equipment("The Great Defence",Equipment.Role.DEFEND,Equipment.Type.CHEST,6,0),
                          new Equipment("Golden Gauntlets",Equipment.Role.DEFEND,Equipment.Type.GLOVES,2,0),
                          new Equipment("Boots of Comfort",Equipment.Role.DEFEND,Equipment.Type.BOOTS,3,0),
                          new Equipment("Night Helmet",Equipment.Role.DEFEND,Equipment.Type.HELMET,2,0),
                          new Equipment("Shield of Fire",Equipment.Role.DEFEND,Equipment.Type.SHIELD,2,2),
                          new Equipment("Light Leather",Equipment.Role.DEFEND,Equipment.Type.CHEST,4,2),
                          new Equipment("Assassins Gloves",Equipment.Role.DEFEND,Equipment.Type.GLOVES,1,2),
                          new Equipment("Wonderful Boots",Equipment.Role.DEFEND,Equipment.Type.BOOTS,4,0),
                          new Equipment("Silver Helmet",Equipment.Role.DEFEND,Equipment.Type.HELMET,3,0),
                          new Equipment("Spiked Shield",Equipment.Role.DEFEND,Equipment.Type.SHIELD,3,2),
                          new Equipment("Invisible robe",Equipment.Role.DEFEND,Equipment.Type.CHEST,2,3),
                          new Equipment("Steal Globes",Equipment.Role.DEFEND,Equipment.Type.GLOVES,3,-1),
                          new Equipment("Beautiful Mask",Equipment.Role.DEFEND,Equipment.Type.HELMET,1,2),
                          new Equipment("Boots that go nowhere",Equipment.Role.DEFEND,Equipment.Type.BOOTS,4,-1)};

    Equipment[][] equipments = {attack,defend};

    default Equipment selectRandomEquipment(){
        int type = (int)(Math.random()*2);
        int equip;
        if(type==0){
            equip = (int) (Math.random()*attack.length);
        }else {
            equip = (int) (Math.random()*defend.length);
        }
        return equipments[type][equip];
    }
}
