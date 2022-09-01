package controller;
import creatures.Goblin;
import creatures.Human;
import items.Drop;
import items.TreasureChest;
import javafx.scene.text.Text;
import land.Land;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface GameView {
    Map<String, Text> NODES = new HashMap<>();

    Map<String, Human> HUMANS = new HashMap<>();

    Map<String,Goblin> GOBLINS = new HashMap<>();

    Map<String, TreasureChest> CHESTS = new HashMap<>();

    Map<String, Drop> DROPS = new HashMap<>();

    Text CONTACT_TEXT = new Text();


    Text ALERT_TEXT = new Text();


}
