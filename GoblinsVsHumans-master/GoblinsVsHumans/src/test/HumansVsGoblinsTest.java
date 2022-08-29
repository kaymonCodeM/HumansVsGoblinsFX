


import controller.ControllerFX;
import controller.Direction;
import controller.GameBoard;
import creatures.Player;
import javafx.scene.input.KeyCode;
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
        controllerFX.getLocateNodes().get(controllerFX.getPlayer().getY() + " " + controllerFX.getPlayer().getX()).setText("P");
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

    @DisplayName("Swap Land Test")
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


    @AfterEach
    void tearDown(){

    }


}
