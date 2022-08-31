package controller;

import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import land.Land;



public class GameBoard {
    private static final int BOARD_SIZE = 22;
    private final Land[][] gameWorld = new Land[BOARD_SIZE][BOARD_SIZE];
    private int numberOfGoblins = 5;
    private int numberOfHumans = 4;

    public void setupGameBoard(){
        for (int i = 0; i< BOARD_SIZE; i++){
            for (int j = 0; j< BOARD_SIZE; j++){
                this.gameWorld[i][j] = new Land(i,j);
            }
        }
        for (int i = 0; i< BOARD_SIZE; i++){
            this.gameWorld[0][i].setSymbol("|");
            this.gameWorld[i][0].setSymbol("|");
            this.gameWorld[21][i].setSymbol("|");
            this.gameWorld[i][21].setSymbol("|");
        }

        //Create 40 walls
        for (int i=0;i<40;i++){
            int x = (int) (Math.random() * (BOARD_SIZE -2))+1;
            int y = (int) (Math.random() * (BOARD_SIZE -2))+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0){
                this.gameWorld[y][x].setSymbol("|");
            }else {
                i--;
            }
        }

        //Create 4 Humans
        for (int i=0;i<numberOfHumans;i++){
            int x = (int) (Math.random() * (BOARD_SIZE -2))+1;
            int y = (int) (Math.random() * (BOARD_SIZE -2))+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human)){
                Human human = new Human(y,x);
                this.gameWorld[y][x] = human;
            }else {
                i--;
            }
        }

        //Create 5 Goblins
        for (int i=0;i<numberOfGoblins;i++){
            int x = (int) (Math.random() * (BOARD_SIZE -2))+1;
            int y = (int) (Math.random() * (BOARD_SIZE -2))+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Goblin goblin = new Goblin(y,x);
                this.gameWorld[y][x] = goblin;
            }else {
                i--;
            }
        }

        //Create Player
        while (true){
            int x = (int) (Math.random() * (BOARD_SIZE -2))+1;
            int y = (int) (Math.random() * (BOARD_SIZE -2))+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Player p = new Player(y,x);
                this.gameWorld[y][x] = p;
                break;
            }
        }

    }

    public Land[][] getGameWorld() {
        return gameWorld;
    }

    public int getNumberOfGoblins() {
        return numberOfGoblins;
    }

    public int getNumberOfHumans() {
        return numberOfHumans;
    }

    public void setNumberOfGoblins(int numberOfGoblins) {
        this.numberOfGoblins = numberOfGoblins;
    }

    public void setNumberOfHumans(int numberOfHumans) {
        this.numberOfHumans = numberOfHumans;
    }
}

