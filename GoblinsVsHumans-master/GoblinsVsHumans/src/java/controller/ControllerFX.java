package controller;


import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import items.Drop;
import items.Equipment;
import items.TreasureChest;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import land.Land;

import java.util.*;

public class ControllerFX extends Application {
    private  static final int ROOT_WIDTH = 928;
    private  static final int ROOT_HEIGHT = 778;
    private  static final int GAME_WIDTH = 504;
    private  static final int GAME_HEIGHT = 504;

    private  static final int INFO_WIDTH = 204;
    private  static final int INFO_HEIGHT = 504;


    private GameBoard gameBoard;

    private Scene scene;
    private HBox container;

    private VBox root;
    private Pane gamePane;

    private VBox infoPane;

    private Pane equipmentPane;

    private Pane playerPane;

    private Player player;

    private Text playerInfo;

    private Pane contactPane;

    private Text contactText;

    private Pane alertPane;

    private Text alertText;

    private Text equipmentText;

    private Map<String,Text> locateNodes = new HashMap<>();

    private Map<String, Human> humans = new HashMap<>();

    private Map<String,Goblin> goblins = new HashMap<>();

    private Map<String, TreasureChest> chests = new HashMap<>();

    private Map<String, Drop> drops = new HashMap<>();

    //Event Handler to move a player which controls the game state
    private EventHandler<KeyEvent> eventHandlerMovePlayer;

    //Event Handler to select equipment
    private EventHandler<KeyEvent> eventHandlerSelectEquipment;

    private int rounds =0;

    private int addHuman = 0;


    public void setupFXGame(Land[][] gameBoard){
        gamePane = new Pane();
        gamePane.setPrefSize(GAME_WIDTH,GAME_HEIGHT);

        for (int i=0;i<gameBoard.length;i++){
            int y = i*24;
            for (int j= 0;j<gameBoard[i].length;j++){
                int x = j*24;
                Text text = new Text();
                String symbol = gameBoard[i][j].getSymbol();
                switch (symbol){
                    case "|":
                        text.setText("|" + symbol + "|");
                        text.setFill(Color.BLACK);
                        break;
                    case "P":
                        text.setText(" " + symbol + " ");
                        text.setFill(Color.DARKVIOLET);
                        setPlayer((Player) gameBoard[i][j]);
                        break;
                    case "H":
                        text.setText(" " + symbol + " ");
                        text.setFill(Color.DARKBLUE);
                        humans.put(i+ " "+ j, (Human) gameBoard[i][j]);
                        break;
                    case "G":
                        text.setText(" " + symbol + " ");
                        text.setFill(Color.DARKGREEN);
                        goblins.put(i+ " "+ j, (Goblin) gameBoard[i][j]);
                        break;
                    case "D":
                        text.setText(" " + symbol + " ");
                        text.setFill(Color.DARKRED);
                        drops.put(i+ " "+ j, (Drop) gameBoard[i][j]);
                        break;
                    case "C":
                        text.setText(" " + symbol + " ");
                        text.setFill(Color.DARKORANGE);
                        chests.put(i+ " "+ j, (TreasureChest) gameBoard[i][j]);
                        break;
                    default:
                        text.setText(" " + symbol + " ");
                }
                text.setX(x);
                text.setY(y);

                locateNodes.put(i+" "+j,text);
                gamePane.getChildren().add(text);
            }
        }

    }

    private Direction getDirection(KeyCode code){
        Direction result = null;
        if (code==KeyCode.UP){
            result = Direction.NORTH;
        } else if (code==KeyCode.DOWN) {
            result = Direction.SOUTH;
        } else if (code==KeyCode.LEFT) {
            result = Direction.WEST;
        }else if (code==KeyCode.RIGHT){
            result = Direction.EAST;
        }
        return result;
    }

    public Land move(String direction,Land land){
        swapLand(land.getY()+ " " + land.getX(),direction);
        int[] directionToInt = Arrays.stream(direction.split(" ")).mapToInt(Integer::parseInt).toArray();
        land.setY(directionToInt[0]);
        land.setX(directionToInt[1]);
        return land;
    }

    private void swapLand(String from, String to){

        double fromY = locateNodes.get(from).getY();
        double fromX = locateNodes.get(from).getX();

        double toY = locateNodes.get(to).getY();
        double toX = locateNodes.get(to).getX();

        locateNodes.get(from).setY(toY);
        locateNodes.get(from).setX(toX);

        locateNodes.get(to).setY(fromY);
        locateNodes.get(to).setX(fromX);

        Text newFrom = locateNodes.get(from);
        Text newTo = locateNodes.get(to);


        locateNodes.put(from,newTo);
        locateNodes.put(to,newFrom);

    }


    public String findClosestNodeDirection(String curKey,String symbol,Direction direction){
        int[] location = Arrays.stream(curKey.split(" ")).mapToInt(Integer::parseInt).toArray();
        String resultKey = "";
        if(locateNodes.get((location[0]-1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.NORTH){
            resultKey += (location[0]-1)+ " "+ location[1];
        }else if(locateNodes.get((location[0]+1)+ " "+ location[1]).getText().contains(symbol) && direction==Direction.SOUTH){
            resultKey += (location[0]+1)+ " "+ location[1];
        }else if(locateNodes.get(location[0]+ " "+ (location[1]-1)).getText().contains(symbol) && direction==Direction.WEST){
            resultKey += location[0]+ " "+ (location[1]-1);
        }else if(locateNodes.get(location[0]+ " "+ (location[1]+1)).getText().contains(symbol) && direction==Direction.EAST){
            resultKey += location[0]+ " "+ (location[1]+1);
        }
        return resultKey;
    }

    public String findClosestNode(String curKey,String symbol){
        Direction[] locations = {Direction.NORTH,Direction.SOUTH,Direction.WEST,Direction.EAST};
        String destination = "";
        for (int i=0;i<4;i++){
            destination = findClosestNodeDirection(curKey,symbol,locations[i]);
            if (!destination.isEmpty()){
                return destination;
            }
        }
        return destination;
    }

    public boolean movePlayer(Direction direction){

        int curY = player.getY();
        int curX = player.getX();
        String destination = findClosestNodeDirection(curY+ " "+curX,"*",direction);
        if (!destination.isEmpty()){
            setPlayer((Player) move(destination,player));
        }else {
            String goblinPosition = findClosestNodeDirection(curY+ " "+curX,"G",direction);
            String chestPosition = findClosestNodeDirection(curY+ " "+curX,"C",direction);
            String dropPosition = findClosestNodeDirection(curY+ " "+curX,"D",direction);
            String humanPosition = findClosestNodeDirection(curY+ " "+curX,"H",direction);
            if(!goblinPosition.isEmpty()){
                Goblin curGoblin = goblins.get(goblinPosition);
                String combatResult = Combat.playerVsGoblin(player,curGoblin);
                if (player.getHealth()<=0){
                    contactText.setText(combatResult);
                    gameBoard = new GameBoard();
                    setRounds(0);
                    setAddHuman(0);
                    newGame();
                    return false;
                } else if (curGoblin.getHealth()<=0) {
                    contactText.setText(combatResult);
                    removeGoblin(curGoblin);
                    goblins.remove(goblinPosition);
                    createChest();
                }
            } else if (!chestPosition.isEmpty()) {
                contactText.setText("You have made contact with a chest:");
                ArrayList<Equipment> items = chests.get(chestPosition).getChest();
                if(!items.isEmpty()) {
                    selectEquipment(items);
                }else {
                    alertText.setText("The chest is empty! Hurry up Goblins are Coming!");
                    return false;
                }
            } else if (!dropPosition.isEmpty()) {
                contactText.setText("You have made contact with a drop:");
                ArrayList<Equipment> items = drops.get(dropPosition).getDrops();
                if(!items.isEmpty()) {
                    selectEquipment(items);
                }else {
                    alertText.setText("The drop is empty! Hurry up Goblins are Coming!");
                    return false;
                }
            } else if (!humanPosition.isEmpty()) {
                contactText.setText("You have made contact with a human:");
                ArrayList<Equipment> items = humans.get(humanPosition).getInventory();
                if(!items.isEmpty()) {
                    selectEquipment(items);
                }else {
                    alertText.setText("The Humans inventory is empty! Hurry up Goblins are Coming!");
                    return false;
                }
            } else {
                //Contact Human
                alertText.setText("Something is blocking you from going there! Hurry up Goblins are Coming!");
                return false;
            }
        }
        return true;
    }

    private void goblinsPursuePlayer(){
        Map<String,Goblin> result = new HashMap<>();
        Iterator<String> iterator = goblins.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Goblin curGoblin = goblins.get(key);
            int curY = curGoblin.getY();
            int curX = curGoblin.getX();
            Goblin newGoblin = null;

            String curPlayer = findClosestNode(key,"P");
            if (!curPlayer.isEmpty()) {
                String combatResult = Combat.playerVsGoblin(player, curGoblin);
                if (player.getHealth() <= 0) {
                    contactText.setText(combatResult);
                    gameBoard = new GameBoard();
                    setRounds(0);
                    setAddHuman(0);
                    newGame();
                    return;
                } else if (curGoblin.getHealth() <= 0) {
                    contactText.setText(combatResult);
                    removeGoblin(curGoblin);
                    iterator.remove();
                    createChest();
                }
            }else if (player.getY()<curY && !findClosestNodeDirection(key,"*",Direction.NORTH).isEmpty()){
                newGoblin = (Goblin) move((curY-1)+ " "+ curX,curGoblin);
            } else if (player.getY()>curY && !findClosestNodeDirection(key,"*",Direction.SOUTH).isEmpty()) {
                newGoblin = (Goblin) move((curY+1)+ " "+ curX,curGoblin);
            }else if (player.getX()<curX && !findClosestNodeDirection(key,"*",Direction.WEST).isEmpty()) {
                newGoblin = (Goblin) move(curY+ " "+ (curX-1),curGoblin);
            }else if (player.getX()>curX && !findClosestNodeDirection(key,"*",Direction.EAST).isEmpty()) {
                newGoblin = (Goblin) move(curY+ " "+ (curX+1),curGoblin);
            }else{
                newGoblin = curGoblin;
            }
            if (newGoblin!=null) {
                result.put(newGoblin.getY() + " " + newGoblin.getX(), newGoblin);
            }
        }
        setGoblins(result);
    }

    public void humansAttackGoblins(){
        Iterator<String> iterator = humans.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Human curHuman = humans.get(key);
            Goblin curGoblin = goblins.get(findClosestNode(key,"G"));
            if (curGoblin!=null) {
                String combatResult = Combat.goblinVsHuman(curHuman,curGoblin);
                if (curGoblin.getHealth()<=0 && curHuman.getHealth()<=0){
                    contactText.setText(combatResult);

                    //remove Human
                    removeHuman(curHuman);
                    iterator.remove();

                    //remove goblin
                    removeGoblin(curGoblin);
                    goblins.remove(curGoblin.getY()+ " "+ curGoblin.getX());

                    createChest();
                } else if (curHuman.getHealth()<=0) {
                    contactText.setText(combatResult);
                    removeHuman(curHuman);
                    iterator.remove();

                } else if (curGoblin.getHealth()<=0) {
                    contactText.setText(combatResult);
                    removeGoblin(curGoblin);
                    goblins.remove(curGoblin.getY()+ " "+ curGoblin.getX());
                    createChest();
                }
            }
        }
    }

    public void createChest(){
        while (true) {
            int y = (int) (Math.random() * 20) + 1;
            int x = (int) (Math.random() * 20) + 1;
            if (locateNodes.get(y + " "+ x).getText().contains("*")) {
                locateNodes.get(y + " "+ x).setText("C");
                locateNodes.get(y + " "+ x).setFill(Color.DARKORANGE);
                TreasureChest c = new TreasureChest(y,x);
                chests.put(y + " "+ x,c);
                break;
            }
        }
    }


    public void newGame(){
        goblins.clear();
        humans.clear();
        drops.clear();
        chests.clear();
        locateNodes.clear();
        gameBoard.setupGameBoard();
        setupFXGame(gameBoard.getGameWorld());
        container.getChildren().set(0,gamePane);
        equipmentText.setText("");
        playerInfo.setText("********************************************\n" + player.toString());
        if(eventHandlerSelectEquipment!=null){
            scene.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerSelectEquipment);
        }
        scene.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerMovePlayer);

    }

    public void removeHuman(Human h){
        locateNodes.get(h.getY()+" "+ h.getX()).setFill(Color.BLACK);
        locateNodes.get(h.getY()+" "+ h.getX()).setText("*");
    }

    public void removeGoblin(Goblin g){
        int y = g.getY();
        int x = g.getX();
        g.getDrops().setY(y);
        g.getDrops().setX(x);

        //Place Drop when goblin dies
        drops.put(y+" "+ x,g.getDrops());
        locateNodes.get(y+" "+ x).setFill(Color.DARKRED);
        locateNodes.get(y+" "+ x).setText("D");
    }

    public int keyCodeToInteger(KeyCode code, int size){
        if (KeyCode.DIGIT0==code || KeyCode.NUMPAD0==code){
            return 0;
        } else if ((KeyCode.DIGIT1==code || KeyCode.NUMPAD1==code) && size>=1){
            return 1;
        }else if ((KeyCode.DIGIT2==code || KeyCode.NUMPAD2==code) && size>=2){
            return 2;
        }else if ((KeyCode.DIGIT3==code || KeyCode.NUMPAD3==code) && size==3){
            return 3;
        }
        return -1;
    }

    public void selectEquipment(ArrayList<Equipment> items){
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerMovePlayer);
        for(int i=0;i<items.size();i++){
            equipmentText.setText(equipmentText.getText()+"ELEMENT: " +  (i+1) + "\n" + items.get(i).toString()+ "\n\n");
        }
        contactText.setText(contactText.getText() +"\nSelect Item based on the item number.\n"
                + "If you do not want an item just type the number 0\n"
                + "WARNING: Any items that are of the same type will be replaced! Also a player can only have one attack item!\n");
        //set contact text
        eventHandlerSelectEquipment = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                alertText.setText("");
                KeyCode code = event.getCode();
                int i = keyCodeToInteger(code, items.size());
                if (i!=-1){
                    if(i!=0) {
                        player.attachEquipment(items.get(i-1));
                        items.remove(i-1);
                        playerInfo.setText("********************************************\n" + player.toString());
                    }
                    scene.removeEventHandler(KeyEvent.KEY_PRESSED, eventHandlerSelectEquipment);
                    scene.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerMovePlayer);
                    clearTextInfo();
                }else {
                    alertText.setText("Give a valid number");
                }
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED,eventHandlerSelectEquipment);
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Text> getLocateNodes() {
        return locateNodes;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGoblins(Map<String, Goblin> goblins) {
        this.goblins = goblins;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setAddHuman(int addHuman) {
        this.addHuman = addHuman;
    }

    @Override
    public void start(Stage stage) {
        gameBoard = new GameBoard();
        gameBoard.setupGameBoard();
        setupFXGame(gameBoard.getGameWorld());

        setupGUI();

        scene = new Scene(root);
        stage.setTitle("Humans Vs Goblins!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        eventHandlerMovePlayer = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                clearTextInfo();
                KeyCode code = event.getCode();
                Direction direction = getDirection(code);
                if (direction!=null){
                    if(movePlayer(direction)){
                        goblinsPursuePlayer();
                        humansAttackGoblins();
                        playerInfo.setText("********************************************\n" + player.toString());
                    }
                    if (goblins.isEmpty()){
                        contactText.setText("Great Job You have Won! All the Goblins have been killed within the round but now there are even more Goblins!\n");
                        gameBoard = new GameBoard();
                        rounds++;
                        if((rounds +1)%3==0){
                            contactText.setText(contactText.getText() +"\nYou have entered round "+(rounds+1)+" as a reward we have recruited an extra human for you!\nThis will happen for ever 3 rounds so don't mess this up...");
                            addHuman++;
                        }
                        gameBoard.setNumberOfHumans(gameBoard.getNumberOfHumans() + addHuman);
                        gameBoard.setNumberOfGoblins(gameBoard.getNumberOfGoblins()+ rounds);
                        newGame();
                    }
                    if(player.getHealth()<=0 || findClosestNode(player.getY()+" "+ player.getX(),"*").isEmpty()){
                        contactText.setText("Your are dead or rotting away for being trapped! You have lost.");
                        gameBoard = new GameBoard();
                        setRounds(0);
                        setAddHuman(0);
                        newGame();
                    }
                }else {
                    alertText.setText("Use an arrow key: N/S/E/W");
                }
            }
        };

        scene.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerMovePlayer);

    }

    public void clearTextInfo(){
        contactText.setText("");
        alertText.setText("");
        equipmentText.setText("");
    }

    private void setupGUI(){
        //Main root
        root = new VBox();
        root.setPrefSize(ROOT_WIDTH,ROOT_HEIGHT);
        root.setPadding(new Insets(56));
        root.setSpacing(56);

        //Container
        container = new HBox();
        container.setPrefSize(ROOT_WIDTH,GAME_HEIGHT);
        container.setSpacing(56);

        //Combat
        contactPane = new Pane();
        contactPane.setPrefSize(GAME_WIDTH,25);
        contactText = new Text();
        contactText.setText("Use the arrow keys to move North/South/East/West.\n"
                + "Watch out for the goblins and you can grab items by picking up\nDrops, Chest, or Equipments from the Humans.\n"
                + "The Humans do not move but will attack the goblins when they come near them.\n"
                + "Good luck and have fun!");
        contactPane.getChildren().add(contactText);

        //Alert
        alertPane = new Pane();
        alertPane.setPrefSize(GAME_WIDTH,25);
        alertText = new Text();
        alertText.setText("");
        alertText.setFill(Color.RED);
        alertPane.getChildren().add(alertText);


        //Info Pane
        infoPane = new VBox();
        infoPane.setPrefSize(INFO_WIDTH,INFO_HEIGHT);
        infoPane.setPadding(new Insets(5));

        //Player Pane
        playerPane = new Pane();
        playerPane.setPrefSize(INFO_WIDTH-10,INFO_HEIGHT*.5-10);
        playerInfo = new Text();
        playerInfo.setText("********************************************\n" + this.player.toString());
        playerPane.getChildren().add(playerInfo);

        //Equipment Pane
        equipmentPane = new Pane();
        equipmentPane.setPrefSize(INFO_WIDTH-10,INFO_HEIGHT*.5-10);
        equipmentText = new Text();
        equipmentText.setText("");
        equipmentPane.getChildren().add(equipmentText);


        infoPane.getChildren().addAll(equipmentPane,playerPane);
        container.getChildren().addAll(gamePane,infoPane);
        root.getChildren().addAll(contactPane,container,alertPane);

    }
    public static void main(String[] args) {
        launch();
    }

}
