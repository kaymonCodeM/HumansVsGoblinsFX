package controller;

import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import items.Drop;
import items.Equipment;
import items.TreasureChest;
import land.Land;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class HumansVsGoblins {
    //Drops
    private final ArrayList<Drop> drops = new ArrayList<>();

    //Humans
    private final ArrayList<Human> humans = new ArrayList<>();

    //Goblins
    private final ArrayList<Goblin> goblins = new ArrayList<>();

    //Chest
    private final ArrayList<TreasureChest>chest = new ArrayList<>();
    private boolean play = true;
    private Land[][] gameWorld = new Land[22][22];

    private Scanner userInput;

    private Player player;



    public void playHumansVsGoblins(){
        setupGame();
        setUserInput(new Scanner(System.in));
        System.out.println("This game is a n/s/e/w type of game.");
        System.out.println("This means you move n for north, s for south, e for east, and w for west");
        System.out.println("You are P which stands for Player. You will also be in the color purple");
        System.out.println("The goblins will come and attack you. The goal is to stay away from the goblins unless if you feel that you can beat them.");
        System.out.println("The game ends if you die or all the goblins are dealt with.");
        System.out.println("Humans have equipments for you to boost up your gear. The humans will also attack the goblins if they come near them.");
        System.out.println("Each completion of combat will spot a chest no matter what. Also a goblin will give you drop items when they are successfully killed.");
        System.out.println("Humans can die but they are your pillars of defence. Draw the goblins near them!");
        System.out.println("Good luck and have fun!");
        while (play){
            goblinsPursuePlayer();

            System.out.println(this.stringGameWorld());

            if(!this.play){
                this.closeScanner();
                break;
            }

            if (goblins.isEmpty()){
                System.out.println("Great Job! You win! All the goblins have been defeated.");
                this.closeScanner();
                break;
            }

            humansCheckForContact();

            chestCheckForPlayer();

            dropsCheckForPlayer();

            System.out.println(("Your Stats:"));
            System.out.println(this.player.toString() + "\n");
            System.out.println("*****************************************************");
            System.out.println("Make a move."+ "\n");
            movePlayer();
        }
    }
    public void setupGame(){
        for (int i=0;i<22;i++){
            for (int j=0;j<22;j++){
                this.gameWorld[i][j] = new Land(new int[]{i, j});
            }
        }
        for (int i=0;i<22;i++){
            this.gameWorld[0][i].setSymbol("|");
            this.gameWorld[i][0].setSymbol("|");
            this.gameWorld[21][i].setSymbol("|");
            this.gameWorld[i][21].setSymbol("|");
        }

        //Create 40 walls
        for (int i=0;i<40;i++){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0){
                this.gameWorld[y][x].setSymbol("|");
            }else {
                i--;
            }
        }

        //Create 4 Humans
        for (int i=0;i<4;i++){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human)){
                Human human = new Human(new int[]{y,x});
                this.gameWorld[y][x] = human;
                this.humans.add(human);
            }else {
                i--;
            }
        }

        //Create 5 Goblins
        for (int i=0;i<5;i++){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Goblin goblin = new Goblin(new int[]{y,x});
                this.gameWorld[y][x] = goblin;
                this.goblins.add(goblin);
            }else {
                i--;
            }
        }

        //Create Player
        while (true){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Player p = new Player(new int[]{y,x});
                this.gameWorld[y][x] = p;
                this.player = p;
                break;
            }
        }

    }

    public void createChest(){
        while (true) {
            int x = (int) (Math.random() * 20) + 1;
            int y = (int) (Math.random() * 20) + 1;
            if (this.gameWorld[y][x].getSymbol().contains("*")) {
                TreasureChest c = new TreasureChest(new int[]{y,x});
                this.gameWorld[y][x] = c;
                this.chest.add(c);
                break;
            }
        }
    }

    public void dropsCheckForPlayer(){
        for (int i = 0; i<this.drops.size();i++){
            int y = this.drops.get(i).getPosition()[0];
            int x = this.drops.get(i).getPosition()[1];
            int selectedItem =0;
            if (this.gameWorld[y+1][x] instanceof Player){
                System.out.println("You have made contact with a Drop" + "\n");
                while(selectedItem==0){
                    selectedItem = playerSelectItem(this.drops.get(i).getDrops());
                }
                ((Drop) this.gameWorld[y][x]).getDrops().remove(selectedItem);
                this.drops.get(i).getDrops().remove(selectedItem);
            }else if (this.gameWorld[y-1][x] instanceof Player){
                System.out.println("You have made contact with a drop" + "\n");
                while(selectedItem==0){
                    selectedItem = playerSelectItem(this.drops.get(i).getDrops());
                }
                ((Drop) this.gameWorld[y][x]).getDrops().remove(selectedItem);
                this.drops.get(i).getDrops().remove(selectedItem);
            }else if (this.gameWorld[y][x+1] instanceof Player){
                System.out.println("You have made contact with a Drop" + "\n");
                while(selectedItem==0){
                    selectedItem = playerSelectItem(this.drops.get(i).getDrops());
                }
                ((Drop) this.gameWorld[y][x]).getDrops().remove(selectedItem);
                this.drops.get(i).getDrops().remove(selectedItem);
            }else if (this.gameWorld[y][x-1] instanceof Player){
                System.out.println("You have made contact with a Drop" + "\n");
                while(selectedItem==0){
                    selectedItem = playerSelectItem(this.drops.get(i).getDrops());
                }
                ((Drop) this.gameWorld[y][x]).getDrops().remove(selectedItem);
                this.drops.get(i).getDrops().remove(selectedItem);
            }
        }
    }
    public void chestCheckForPlayer(){
        for(int i=0;i<this.chest.size();i++) {
            int y = this.chest.get(i).getPosition()[0];
            int x = this.chest.get(i).getPosition()[1];
            int selectedItem = 0;
            if (this.gameWorld[y + 1][x] instanceof Player) {
                System.out.println("You have made contact with a Chest" + "\n");
                while (selectedItem == 0) {
                    selectedItem = playerSelectItem(this.chest.get(i).getChest());
                }
                ((TreasureChest) this.gameWorld[y][x]).getChest().remove(selectedItem);
                this.chest.get(i).getChest().remove(selectedItem);
            } else if (this.gameWorld[y - 1][x] instanceof Player) {
                System.out.println("You have made contact with a Chest" + "\n");
                while (selectedItem == 0) {
                    selectedItem = playerSelectItem(this.chest.get(i).getChest());
                }
                ((TreasureChest) this.gameWorld[y][x]).getChest().remove(selectedItem);
                this.chest.get(i).getChest().remove(selectedItem);
            } else if (this.gameWorld[y][x + 1] instanceof Player) {
                System.out.println("You have made contact with a Chest" + "\n");
                while (selectedItem == 0) {
                    selectedItem = playerSelectItem(this.chest.get(i).getChest());
                }
                ((TreasureChest) this.gameWorld[y][x]).getChest().remove(selectedItem);
                this.chest.get(i).getChest().remove(selectedItem);
            } else if (this.gameWorld[y][x - 1] instanceof Player) {
                System.out.println("You have made contact with a Chest" + "\n");
                while (selectedItem == 0) {
                    selectedItem = playerSelectItem(this.chest.get(i).getChest());
                }
                ((TreasureChest) this.gameWorld[y][x]).getChest().remove(selectedItem);
                this.chest.get(i).getChest().remove(selectedItem);
            }
        }
    }
    public void humansCheckForContact(){
        for (int i=0; i<this.humans.size();i++){
            int y = this.humans.get(i).getPosition()[0];
            int x = this.humans.get(i).getPosition()[1];

            //Human will attack goblin if it has made contact
            if(this.gameWorld[y+1][x] instanceof Goblin){
                String result = Combat.goblinVsHuman(this.humans.get(i),(Goblin) this.getGameWorld()[y+1][x],this);
                System.out.println(result);
                System.out.println(this.stringGameWorld());
                createChest();
            } else if (this.gameWorld[y-1][x] instanceof Goblin) {
                String result = Combat.goblinVsHuman(this.humans.get(i),(Goblin) this.getGameWorld()[y-1][x],this);
                System.out.println(result);
                System.out.println(this.stringGameWorld());
                createChest();
            } else if (this.gameWorld[y][x+1] instanceof Goblin) {
                String result = Combat.goblinVsHuman(this.humans.get(i),(Goblin) this.getGameWorld()[y][x+1],this);
                System.out.println(result);
                System.out.println(this.stringGameWorld());
                createChest();
            } else if (this.gameWorld[y][x-1] instanceof Goblin) {
                String result = Combat.goblinVsHuman(this.humans.get(i),(Goblin) this.getGameWorld()[y][x-1],this);
                System.out.println(result);
                System.out.println(this.stringGameWorld());
                createChest();
            }else {
                int selectedItem =0;
                if (this.gameWorld[y+1][x] instanceof Player){
                    System.out.println("You have made contact with a Human" + "\n");
                    while(selectedItem==0){
                        selectedItem = playerSelectItem(this.humans.get(i).getInventory());
                    }
                    ((Human) this.gameWorld[y][x]).getInventory().remove(selectedItem);
                    this.humans.get(i).getInventory().remove(selectedItem);
                }else if (this.gameWorld[y-1][x] instanceof Player){
                    System.out.println("You have made contact with a Human" + "\n");
                    while(selectedItem==0){
                        selectedItem = playerSelectItem(this.humans.get(i).getInventory());
                    }
                    ((Human) this.gameWorld[y][x]).getInventory().remove(selectedItem);
                    this.humans.get(i).getInventory().remove(selectedItem);
                }else if (this.gameWorld[y][x+1] instanceof Player){
                    System.out.println("You have made contact with a Human" + "\n");
                    while(selectedItem==0){
                        selectedItem = playerSelectItem(this.humans.get(i).getInventory());
                    }
                    ((Human) this.gameWorld[y][x]).getInventory().remove(selectedItem);
                    this.humans.get(i).getInventory().remove(selectedItem);
                }else if (this.gameWorld[y][x-1] instanceof Player){
                    System.out.println("You have made contact with a Human" + "\n");
                    while(selectedItem==0){
                        selectedItem = playerSelectItem(this.humans.get(i).getInventory());
                    }
                    ((Human) this.gameWorld[y][x]).getInventory().remove(selectedItem);
                    this.humans.get(i).getInventory().remove(selectedItem);
                }
            }
        }
    }

    public void goblinsPursuePlayer(){
        for (int i=0;i<this.goblins.size();i++){
            int goblinY = goblins.get(i).getPosition()[0];
            int goblinX = goblins.get(i).getPosition()[1];

            //Goblin pursues player
            int playerY = this.player.getPosition()[0];
            int playerX = this.player.getPosition()[1];
            if(playerY>goblinY && this.gameWorld[goblinY+1][goblinX].getSymbol().contains("*")){
                int[] newPosition = new int[]{goblinY+1,goblinX};
                swapLand(goblins.get(i).getPosition(),newPosition);
                goblins.get(i).setPosition(newPosition);
            }else if(playerY<goblinY && this.gameWorld[goblinY-1][goblinX].getSymbol().contains("*")){
                int[] newPosition = new int[]{goblinY-1,goblinX};
                swapLand(goblins.get(i).getPosition(),newPosition);
                goblins.get(i).setPosition(newPosition);
            }else if(playerX>goblinX && this.gameWorld[goblinY][goblinX+1].getSymbol().contains("*")){
                int[] newPosition = new int[]{goblinY,goblinX+1};
                swapLand(goblins.get(i).getPosition(),newPosition);
                goblins.get(i).setPosition(newPosition);
            }else if(playerX<goblinX && this.gameWorld[goblinY][goblinX-1].getSymbol().contains("*")){
                int[] newPosition = new int[]{goblinY,goblinX-1};
                swapLand(goblins.get(i).getPosition(),newPosition);
                goblins.get(i).setPosition(newPosition);
            }

            goblinY = this.goblins.get(i).getPosition()[0];
            goblinX = this.goblins.get(i).getPosition()[1];

            //Goblin Attacks Player
            if(this.gameWorld[goblinY+1][goblinX] instanceof Player){
                String result = Combat.playerVsGoblin((Player) this.gameWorld[goblinY+1][goblinX],goblins.get(i),this);
                System.out.println(result);
                createChest();
            } else if (this.gameWorld[goblinY-1][goblinX] instanceof Player) {
                String result = Combat.playerVsGoblin((Player) this.gameWorld[goblinY-1][goblinX],goblins.get(i),this);
                System.out.println(result);
                createChest();
            } else if (this.gameWorld[goblinY][goblinX+1] instanceof Player) {
                String result = Combat.playerVsGoblin((Player) this.gameWorld[goblinY][goblinX+1],goblins.get(i),this);
                System.out.println(result);
                createChest();
            } else if (this.gameWorld[goblinY][goblinX-1] instanceof Player) {
                String result = Combat.playerVsGoblin((Player) this.gameWorld[goblinY][goblinX-1],goblins.get(i),this);
                System.out.println(result);
                createChest();
            }
        }
    }

    public void movePlayer(){
        char move;
        try{
            move = this.userInput.next().charAt(0);
        }catch (Exception e){
            System.out.println("Please give n for north, s for south, e for east, and w for west");
            movePlayer();
            return;
        }
        int y =this.player.getPosition()[0];
        int x = this.player.getPosition()[1];
        if(move =='n' && this.gameWorld[y-1][x] instanceof Goblin){
            String result = Combat.playerVsGoblin(this.player,(Goblin) this.gameWorld[y-1][x],this);
            System.out.println(result);
            createChest();
        }else if(move =='s' && this.gameWorld[y+1][x] instanceof Goblin){
            String result = Combat.playerVsGoblin(this.player,(Goblin) this.gameWorld[y+1][x],this);
            System.out.println(result);
            createChest();
        }else if(move =='e' && this.gameWorld[y][x+1] instanceof Goblin){
            String result = Combat.playerVsGoblin(this.player,(Goblin) this.gameWorld[y][x+1],this);
            System.out.println(result);
            createChest();
        }else if(move =='w' && this.gameWorld[y][x-1] instanceof Goblin){
            String result = Combat.playerVsGoblin(this.player,(Goblin) this.gameWorld[y][x-1],this);
            System.out.println(result);
            createChest();
        }else {
            if (move == 'n' && this.gameWorld[y - 1][x].getSymbol().contains("*")) {
                int[] newPosition = new int[]{y - 1, x};
                swapLand(this.player.getPosition(), newPosition);
                this.player.setPosition(newPosition);
            } else if (move == 's' && this.gameWorld[y + 1][x].getSymbol().contains("*")) {
                int[] newPosition = new int[]{y + 1, x};
                swapLand(this.player.getPosition(), newPosition);
                this.player.setPosition(newPosition);
            } else if (move == 'e' && this.gameWorld[y][x + 1].getSymbol().contains("*")) {
                int[] newPosition = new int[]{y, x + 1};
                swapLand(this.player.getPosition(), newPosition);
                this.player.setPosition(newPosition);
            } else if (move == 'w' && this.gameWorld[y][x - 1].getSymbol().contains("*")) {
                int[] newPosition = new int[]{y, x - 1};
                swapLand(this.player.getPosition(), newPosition);
                this.player.setPosition(newPosition);
            } else {
                System.out.println("Please give n for north, s for south, e for east, and w for west.");
                System.out.println("If a land is already field you can not go there.");
                movePlayer();
            }
        }
    }

    public int playerSelectItem(Map<Integer, Equipment> items){
        for (int key: items.keySet()){
            System.out.println("Equipment " + key);
            System.out.println(items.get(key).toString() + "\n");
        }
        System.out.println("Select the item that you want based on the equipment number.");
        System.out.println("If you do not want an item just type the number -1");
        System.out.println("WARNING: Any items that are of the same type will be replaced! Also a player can only have one attack item!" +"\n");

        int select =0;
        try{
            select = Integer.parseInt(this.userInput.next());
            if (select==-1){
                return -1;
            } else if (items.get(select) ==null) {
                return 0;
            }
            this.player.attachEquipment(items.get(select));
        }catch (Exception e){
            System.out.println("\n" + "Please select a valid number..." + "\n");
        }
        return select;
    }


    public void swapLand(int[] from,int[] to){
        Land temp = this.gameWorld[to[0]][to[1]];
        this.gameWorld[to[0]][to[1]] = this.gameWorld[from[0]][from[1]];
        this.gameWorld[from[0]][from[1]] = temp;
    }

    public String stringGameWorld() {
        String result = "";
        for (Land [] lands:this.getGameWorld()){
            for (Land land: lands){
                if (land.getSymbol().compareTo("|")==0) {
                    result += "|" + land.getSymbol() + "|";
                }else {
                    result += " " + land.getSymbol() + " ";
                }
            }
            result+="\n";
        }
        return result;
    }

    public void removeHuman(Human h){
        int[] position = h.getPosition();
        this.gameWorld[position[0]][position[1]] = new Land(position);
        this.getHumans().remove(h);
    }

    public void removeGoblin(Goblin g){
        int[] position = g.getPosition();
        g.getDrops().setPosition(g.getPosition());
        //Place Drop when goblin dies
        this.gameWorld[position[0]][position[1]] = g.getDrops();
        this.drops.add(g.getDrops());
        this.getGoblins().remove(g);
    }


    public boolean getPlay(){
        return this.play;
    }
    public Land[][] getGameWorld() {
        return gameWorld;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public ArrayList<Goblin> getGoblins() {
        return goblins;
    }

    public Scanner getUserInput() {
        return userInput;
    }

    public ArrayList<Drop> getDrops() {
        return drops;
    }

    public ArrayList<TreasureChest> getChest() {
        return chest;
    }


    public void setPlay(boolean play) {
        this.play = play;
    }

    public void setGameWorld(Land[][] gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setUserInput(Scanner userInput) {
        this.userInput = userInput;
    }

    public void closeScanner(){
        this.userInput.close();
    }
    public static void main(String[] args) {
        HumansVsGoblins humansVsGoblins = new HumansVsGoblins();
        humansVsGoblins.playHumansVsGoblins();
    }
}

