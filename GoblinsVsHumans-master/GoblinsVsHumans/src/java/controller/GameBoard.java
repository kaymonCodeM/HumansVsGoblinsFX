package controller;

import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import items.TreasureChest;
import land.Land;



public class GameBoard {
    private final Land[][] gameWorld = new Land[22][22];

    public void setupGameBoard(){
        for (int i=0;i<22;i++){
            for (int j=0;j<22;j++){
                this.gameWorld[i][j] = new Land(i,j);
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
                Human human = new Human(y,x);
                this.gameWorld[y][x] = human;
            }else {
                i--;
            }
        }

        //Create 5 Goblins
        for (int i=0;i<5;i++){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Goblin goblin = new Goblin(y,x);
                this.gameWorld[y][x] = goblin;
            }else {
                i--;
            }
        }

        //Create Player
        while (true){
            int x = (int) (Math.random() * 20)+1;
            int y = (int) (Math.random() * 20)+1;
            if(this.gameWorld[y][x].getSymbol().compareTo("|")!=0 && !(this.gameWorld[y][x] instanceof Human) && !(this.gameWorld[y][x] instanceof Goblin)){
                Player p = new Player(y,x);
                this.gameWorld[y][x] = p;
                break;
            }
        }

    }

    public void createChest() {
        while (true) {
            int x = (int) (Math.random() * 20) + 1;
            int y = (int) (Math.random() * 20) + 1;
            if (this.gameWorld[y][x].getSymbol().contains("*")) {
                TreasureChest c = new TreasureChest(y, x);
                this.gameWorld[y][x] = c;
                break;
            }
        }
    }

    public Land[][] getGameWorld() {
        return gameWorld;
    }
}

