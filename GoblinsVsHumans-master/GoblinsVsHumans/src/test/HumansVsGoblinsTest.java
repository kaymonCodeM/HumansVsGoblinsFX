


import controller.ControllerFX;
import controller.Direction;
import controller.GameBoard;
import creatures.Player;
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
        assertTrue(controllerFX.movePlayer(Direction.NORTH),"Move Player failed");
        assertTrue(controllerFX.movePlayer(Direction.SOUTH),"Move Player failed");
        assertTrue(controllerFX.movePlayer(Direction.WEST),"Move Player failed");
        assertTrue(controllerFX.movePlayer(Direction.EAST),"Move Player failed");
    }


    @AfterEach
    void tearDown(){

    }


}
