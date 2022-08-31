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

public class ControllerFX extends Application implements GameView {
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

    private Pane contactPane;

    private Pane alertPane;

    private Player player;

    private Text playerInfo;


    private Text equipmentText;


    //Event Handler to move a player which controls the game state
    private EventHandler<KeyEvent> eventHandlerMovePlayer;

    //Event Handler to select equipment
    private EventHandler<KeyEvent> eventHandlerSelectEquipment;

    private int rounds =0;

    private int addHuman = 0;

    //--module-path "C:\javafx18\javafx-sdk-18.0.2\lib" --add-modules javafx.controls,javafx.fxml
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

    public Direction getDirection(KeyCode code){
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




    //returns if a player moves or not
    public boolean movePlayer(Direction direction){

        int curY = player.getY();
        int curX = player.getX();
        String goblinPosition = findClosestNodeDirection(curY+ " "+curX,"G",direction);
        if(!goblinPosition.isEmpty()){
            player.attackGoblin(goblins.get(goblinPosition));
            return player.isAlive();
        }else {
            ArrayList<Equipment> items = this.player.moveMe(direction);
            if(items!=null){
                selectEquipment(items);
            } else if ((curY+ " "+ curX).compareTo(player.getY()+ " "+ player.getX())==0) {
                return false;
            }

        }
        return true;
    }

    public void goblinsPursuePlayer(){
        Map<String,Goblin> result = new HashMap<>();
        Iterator<String> iterator = goblins.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            Goblin goblin = goblins.get(key);
            boolean goblinAlive = goblin.moveToPlayer(player);
            if (!player.isAlive()){
                return;
            }
            if (!goblinAlive){
                iterator.remove();
                createChest();
            }else{
                result.put(goblin.getY() + " " + goblin.getX(), goblin);
            }
        }
        goblins.clear();
        goblins.putAll(result);
    }

    public void humansAttackGoblins(){
        Iterator<String> iterator = humans.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Human curHuman = humans.get(key);
            Goblin curGoblin = goblins.get(findClosestNode(key,"G"));
            if (curGoblin!=null) {
                if (!curHuman.attackGoblin(curGoblin)){
                    iterator.remove();
                }
                createChest();
            }
        }
    }

    public void createChest(){
        TreasureChest chest;
        while (true) {
            int y = (int) (Math.random() * 20) + 1;
            int x = (int) (Math.random() * 20) + 1;
            if (locateNodes.get(y + " "+ x).getText().contains("*")) {
                locateNodes.get(y + " "+ x).setText("C");
                locateNodes.get(y + " "+ x).setFill(Color.DARKORANGE);
                chest = new TreasureChest(y,x);
                chests.put(y + " "+ x,chest);
                break;
            }
        }
        contactText.setText(contactText.getText() + "\nRandom Chest is displayed at: y = "+ chest.getY()+ " x = "+ chest.getX());
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

    public Map<String, Goblin> getGoblins() {
        return goblins;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public Text getContactText() {
        return contactText;
    }

    public HBox getContainer() {
        return container;
    }

    public Map<String, Human> getHumans() {
        return humans;
    }

    public Map<String, TreasureChest> getChests() {
        return chests;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setAddHuman(int addHuman) {
        this.addHuman = addHuman;
    }


    public void setContainer(HBox container) {
        this.container = container;
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
                        if(player.isAlive()) {
                            humansAttackGoblins();
                            playerInfo.setText("********************************************\n" + player.toString());
                        }
                    }
                    if (goblins.isEmpty()){
                        gameBoard = new GameBoard();
                        rounds++;
                        gameBoard.setNumberOfGoblins(gameBoard.getNumberOfGoblins()+ rounds);
                        contactText.setText("Great Job You have Won! All the Goblins have been killed within the round but now there are even more Goblins!\n" + gameBoard.getNumberOfGoblins() + " Goblins remain. ");
                        if((rounds +1)%3==0){
                            contactText.setText(contactText.getText() +"You have entered round "+(rounds+1)+" as a reward we have recruited an extra human for you!\nThis will happen for ever 3 rounds so don't mess this up...");
                            addHuman++;
                        }
                        gameBoard.setNumberOfHumans(gameBoard.getNumberOfHumans() + addHuman);
                        newGame();
                    }
                    if(player.getHealth()<=0 || findClosestNode(player.getY()+" "+ player.getX(),"*").isEmpty() || !player.isAlive()){
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
        contactText.setText("Use the arrow keys to move North/South/East/West.\n"
                + "Watch out for the goblins and you can grab items by picking up\nDrops, Chest, or Equipments from the Humans.\n"
                + "The Humans do not move but will attack the goblins when they come near them.\n"
                + "Good luck and have fun!");
        contactPane.getChildren().add(contactText);

        //Alert
        alertPane = new Pane();
        alertPane.setPrefSize(GAME_WIDTH,25);
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
