


import controller.Combat;
import controller.ControllerFX;
import controller.Direction;
import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import items.Drop;
import items.TreasureChest;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import land.Land;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class HumansVsGoblinsTest {

    ControllerFX controllerFX;

    @BeforeEach
    void setUp(){
        controllerFX = new ControllerFX();
        Land[][] gameWorld = new Land[22][22];
        for (int i=0;i<22;i++){
            for (int j=0;j<22;j++){
                gameWorld[i][j] = new Land(i,j);
            }
        }
        for (int i=0;i<22;i++){
            gameWorld[0][i].setSymbol("|");
            gameWorld[i][0].setSymbol("|");
            gameWorld[21][i].setSymbol("|");
            gameWorld[i][21].setSymbol("|");
        }
        controllerFX.setupFXGame(gameWorld);
    }

    @DisplayName("Test to move Player")
    @Test
    void movePlayer(){
        controllerFX.setPlayer(new Player(3,3));
        controllerFX.getLocateNodes().get(3 + " " + 3).setText("P");
        assertTrue(controllerFX.movePlayer(Direction.NORTH),"Move Player North failed");
        assertTrue(controllerFX.movePlayer(Direction.SOUTH),"Move Player South failed");
        assertTrue(controllerFX.movePlayer(Direction.WEST),"Move Player West failed");
        assertTrue(controllerFX.movePlayer(Direction.EAST),"Move Player East failed");

        controllerFX.setAlertText(new Text());
        controllerFX.getLocateNodes().get(2+" "+3).setText("|");
        controllerFX.getLocateNodes().get(4+" "+3).setText("|");
        controllerFX.getLocateNodes().get(3+" "+2).setText("|");
        controllerFX.getLocateNodes().get(3+" "+4).setText("|");
        assertFalse(controllerFX.movePlayer(Direction.NORTH),"Moved into wall North failed");
        assertFalse(controllerFX.movePlayer(Direction.SOUTH),"Moved into wall South failed");
        assertFalse(controllerFX.movePlayer(Direction.WEST),"Moved into wall West failed");
        assertFalse(controllerFX.movePlayer(Direction.EAST),"Moved into wall East failed");

        controllerFX.setContactText(new Text(""));
        controllerFX.setContainer(new HBox());
        controllerFX.getContainer().getChildren().add(new Pane());
        controllerFX.getGoblins().put(2 + " "+ 3,new Goblin(2,3));
        controllerFX.getLocateNodes().get(2 + " "+3).setText("G");
        controllerFX.movePlayer(Direction.NORTH);
        assertFalse(controllerFX.getContactText().getText().isEmpty(),"Contact with Player and Goblin Failed");
    }

    @DisplayName("Test to get direction")
    @Test
    void getDirection(){
        assertEquals(Direction.NORTH,controllerFX.getDirection(KeyCode.UP), "Test Direction North Failed");
        assertEquals(Direction.SOUTH,controllerFX.getDirection(KeyCode.DOWN), "Test Direction South Failed");
        assertEquals(Direction.WEST,controllerFX.getDirection(KeyCode.LEFT), "Test Direction West Failed");
        assertEquals(Direction.EAST,controllerFX.getDirection(KeyCode.RIGHT), "Test Direction East Failed");
    }

    @DisplayName("Test Move")
    @Test
    void move(){
        controllerFX.setPlayer(new Player(3,3));
        int y = controllerFX.getPlayer().getY();
        int x = controllerFX.getPlayer().getX();
        controllerFX.move((y-1) + " " + x,controllerFX.getPlayer());
        assertEquals(y-1,controllerFX.getPlayer().getY(),"Move North Failed");
        controllerFX.move(y + " " + x,controllerFX.getPlayer());
        assertEquals(y,controllerFX.getPlayer().getY(),"Move South Failed");
        controllerFX.move(y + " " + (x-1),controllerFX.getPlayer());
        assertEquals(x-1,controllerFX.getPlayer().getX(),"Move West Failed");
        controllerFX.move(y + " " + x,controllerFX.getPlayer());
        assertEquals(x,controllerFX.getPlayer().getX(),"Move East Failed");
    }

    @DisplayName("Test Swap Land")
    @Test
    void swapLand(){
        int y = 3;
        int x = 3;
        controllerFX.getLocateNodes().get(y + " " + x).setText("P");
        controllerFX.swapLand(y+ " "+ x,(y-1) + " "+x);
        assertEquals(controllerFX.getLocateNodes().get((y-1) + " "+x).getText(),"P", "Swap land North Failed");
        controllerFX.swapLand((y-1)+ " "+ x,y + " "+x);
        assertEquals(controllerFX.getLocateNodes().get(y + " "+x).getText(),"P", "Swap land South Failed");
        controllerFX.swapLand(y+ " "+ x,y + " "+(x-1));
        assertEquals(controllerFX.getLocateNodes().get(y + " "+(x-1)).getText(),"P", "Swap land West Failed");
        controllerFX.swapLand(y+ " "+ (x-1),y + " "+x);
        assertEquals(controllerFX.getLocateNodes().get(y + " "+x).getText(),"P", "Swap land East Failed");
    }

    @DisplayName("Test to find closest node based on direction")
    @Test
    void findClosestNodeDirection(){
        int y = 3;
        int x = 3;
        controllerFX.getLocateNodes().get((y-1) + " " + x).setText("G");
        controllerFX.getLocateNodes().get((y+1) + " " + x).setText("G");
        controllerFX.getLocateNodes().get(y + " " + (x-1)).setText("G");
        controllerFX.getLocateNodes().get(y + " " + (x+1)).setText("G");
        assertEquals((y-1) + " " + x,controllerFX.findClosestNodeDirection(y+ " "+x,"G",Direction.NORTH), "Find node at Direction North failed");
        assertEquals((y+1) + " " + x,controllerFX.findClosestNodeDirection(y+ " "+x,"G",Direction.SOUTH), "Find node at Direction South failed");
        assertEquals(y + " " + (x-1),controllerFX.findClosestNodeDirection(y+ " "+x,"G",Direction.WEST), "Find node at Direction West failed");
        assertEquals(y + " " + (x+1),controllerFX.findClosestNodeDirection(y+ " "+x,"G",Direction.EAST), "Find node at Direction East failed");
    }

    @DisplayName("Test to find closest node")
    @Test
    void findClosestNode(){
        int y = 3;
        int x = 3;
        controllerFX.getLocateNodes().get((y-1) + " " + x).setText("G");
        assertEquals((y-1) + " " + x,controllerFX.findClosestNode(y+ " "+x,"G"), "Find node North failed");
        controllerFX.getLocateNodes().get((y-1) + " " + x).setText("*");
        controllerFX.getLocateNodes().get((y+1) + " " + x).setText("G");
        assertEquals((y+1) + " " + x,controllerFX.findClosestNode(y+ " "+x,"G"), "Find node South failed");
        controllerFX.getLocateNodes().get((y+1) + " " + x).setText("*");
        controllerFX.getLocateNodes().get(y + " " + (x-1)).setText("G");
        assertEquals(y + " " +(x-1),controllerFX.findClosestNode(y+ " "+x,"G"), "Find node West failed");
        controllerFX.getLocateNodes().get(y + " " + (x-1)).setText("*");
        controllerFX.getLocateNodes().get(y + " " + (x+1)).setText("G");
        assertEquals(y + " " +(x+1),controllerFX.findClosestNode(y+ " "+x,"G"), "Find node East failed");
    }

    @DisplayName("Test Goblin pursue player")
    @Test
    void goblinsPursuePlayer(){
        controllerFX.setContactText(new Text(""));
        controllerFX.setContainer(new HBox());
        controllerFX.getContainer().getChildren().add(new Pane());

        controllerFX.setPlayer(new Player(3,3));
        controllerFX.getLocateNodes().get(3 + " " + 3).setText("P");

        controllerFX.getGoblins().put(2 + " "+ 3,new Goblin(2,3));
        controllerFX.getLocateNodes().get(2 + " "+3).setText("G");
        controllerFX.goblinsPursuePlayer();
        assertFalse(controllerFX.getContactText().getText().isEmpty(),"Goblin attack player failed");

        controllerFX.getLocateNodes().get(3 + " " + 3).setText("P");
        controllerFX.getLocateNodes().get(3 + " " + 3).setText("P");
        controllerFX.getGoblins().put(6 + " "+ 3,new Goblin(6,3));
        controllerFX.getLocateNodes().get(6 + " "+3).setText("G");
        controllerFX.goblinsPursuePlayer();
        assertEquals("G",controllerFX.getLocateNodes().get(5 +" "+3).getText(),"Goblin pursue player failed");
    }

    @DisplayName("Test Humans attack the goblins")
    @Test
    void humansAttackGoblins(){
        controllerFX.setContactText(new Text(""));
        controllerFX.setContainer(new HBox());
        controllerFX.getContainer().getChildren().add(new Pane());

        controllerFX.getHumans().put(3+" "+3,new Human(3,3));
        controllerFX.getLocateNodes().get(3 + " "+3).setText("H");

        controllerFX.getGoblins().put(2 + " "+ 3,new Goblin(2,3));
        controllerFX.getLocateNodes().get(2 + " "+3).setText("G");

        controllerFX.humansAttackGoblins();

        assertFalse(controllerFX.getContactText().getText().isEmpty(),"Human attack goblin failed");

    }

    @DisplayName("Test chest creation")
    @Test
    void createChest(){
        controllerFX.setContactText(new Text());
        controllerFX.createChest();
        assertFalse(controllerFX.getChests().isEmpty(), "Create chest failed");
    }

    @DisplayName("Test remove Human")
    @Test
    void removeHuman(){
        controllerFX.setContactText(new Text());
        controllerFX.getHumans().put(3+" "+3,new Human(3,3));
        controllerFX.getLocateNodes().get(3 + " "+3).setText("H");
        controllerFX.removeHuman(controllerFX.getHumans().get(3+ " "+3));
        assertEquals("*",controllerFX.getLocateNodes().get(3 + " "+ 3).getText(),"Test remove human failed");
    }

    @DisplayName("Test remove Goblin")
    @Test
    void removeGoblin(){
        controllerFX.setContactText(new Text());
        controllerFX.getGoblins().put(3+" "+3,new Goblin(3,3));
        controllerFX.getLocateNodes().get(3 + " "+3).setText("G");
        controllerFX.removeGoblin(controllerFX.getGoblins().get(3+ " "+3));
        assertEquals("D",controllerFX.getLocateNodes().get(3 + " "+ 3).getText(),"Test remove Goblin failed");
    }

    @DisplayName("Test KeyCode to Integer")
    @Test
    void keyCodeToInteger(){
        assertEquals(0,controllerFX.keyCodeToInteger(KeyCode.NUMPAD0,3), "Test KeyCode to 0 Failed");
        assertEquals(1,controllerFX.keyCodeToInteger(KeyCode.NUMPAD1,3), "Test KeyCode to 1 Failed");
        assertEquals(2,controllerFX.keyCodeToInteger(KeyCode.NUMPAD2,3), "Test KeyCode to 2 Failed");
        assertEquals(3,controllerFX.keyCodeToInteger(KeyCode.NUMPAD3,3), "Test KeyCode to 3 Failed");
    }

    @DisplayName("Test Combat Goblin Vs Human")
    @Test
    void goblinVsHuman(){
        Human human = new Human(3,3);
        Goblin goblin = new Goblin(3,4);
        assertFalse(Combat.goblinVsHuman(human,goblin).isEmpty(),"Goblin Vs Human Failed");
    }

    @DisplayName("Test Attach Equipment")
    @Test
    void attachEquipment(){
        Player player = new Player(3,3);
        Human human = new Human(3,3);
        player.attachEquipment(human.getInventory().get(human.getInventory().size()-1));
        assertEquals(1,player.getInventory().size(),"Add inventory item failed");
        Drop drop = new Drop(3,3);
        player  = new Player(3,3);
        player.attachEquipment(drop.getDrops().get(drop.getDrops().size()-1));
        assertEquals(1,player.getInventory().size(),"Add drop item failed");
        TreasureChest chest = new TreasureChest(3,3);
        player  = new Player(3,3);
        player.attachEquipment(chest.getChest().get(chest.getChest().size()-1));
        assertEquals(1,player.getInventory().size(),"Add chest item failed");
    }


}
