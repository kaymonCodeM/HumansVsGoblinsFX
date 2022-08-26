

import controller.HumansVsGoblins;
import creatures.Goblin;
import creatures.Human;
import creatures.Player;
import items.Drop;
import items.Equipment;
import items.TreasureChest;
import land.Land;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class HumansVsGoblinsTest {
    HumansVsGoblins humansVsGoblins;
    @BeforeEach
    void setUp(){
        humansVsGoblins = new HumansVsGoblins();
        humansVsGoblins.getGameWorld();

        Land[][] testGame = new Land[22][22];
        for (int i=0;i<22;i++){
            for (int j=0;j<22;j++){
                testGame[i][j] = new Land(new int[]{i, j});
            }
        }
        humansVsGoblins.setGameWorld(testGame);
    }

    @DisplayName("Test if chest is created")
    @Test
    void createChest(){
        boolean chestInGameWorld = false;
        humansVsGoblins.createChest();
        for (int i=1;i<21;i++){
            for (int j=1;j<21;j++){
                if(humansVsGoblins.getGameWorld()[i][j] instanceof TreasureChest){
                    chestInGameWorld= true;
                    break;
                }
            }
        }

        assertTrue(chestInGameWorld,"Test to create chest failed");
    }


    //Test to see if the drop sees the player and the result will be that equipment 1 will be added to players inventory
    @DisplayName("Test if drop makes contact with player")
    @Test
    void dropsCheckForPlayerTest(){

        Drop drop = new Drop(new int[]{3,3});
        Equipment.Type type = drop.getDrops().get(1).getType();
        String result = drop.getDrops().get(1).toString();
        humansVsGoblins.getGameWorld()[3][3] = drop;
        humansVsGoblins.getDrops().add(drop);


        Player player = new Player(new int[]{3,4});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][4] = humansVsGoblins.getPlayer();

        InputStream input = new ByteArrayInputStream("1".getBytes());
        humansVsGoblins.setUserInput(new Scanner(input));

        humansVsGoblins.dropsCheckForPlayer();

        assertTrue(result.contains(humansVsGoblins.getPlayer().getInventory().get(type).toString()), "Test to check drop element in Player inventory failed");

    }

    //Test to see if the chest sees the player and the result will be that equipment 1 will be added to players inventory
    @DisplayName("Test if chest makes contact with player")
    @Test
    void chestCheckForPlayerTest(){

        TreasureChest chest = new TreasureChest(new int[]{3,3});
        Equipment.Type type = chest.getChest().get(1).getType();
        String result = chest.getChest().get(1).toString();
        humansVsGoblins.getGameWorld()[3][3] = chest;
        humansVsGoblins.getChest().add(chest);


        Player player = new Player(new int[]{3,4});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][4] = humansVsGoblins.getPlayer();

        InputStream input = new ByteArrayInputStream("1".getBytes());
        humansVsGoblins.setUserInput(new Scanner(input));

        humansVsGoblins.chestCheckForPlayer();

        assertTrue(result.contains(humansVsGoblins.getPlayer().getInventory().get(type).toString()), "Test to check chest element in Player inventory failed");
    }

    //Test to see if the Human sees the player and the result will be that equipment 1 will be added to players inventory
    @DisplayName("Test if Human makes contact with player")
    @Test
    void humansCheckForPlayerTest(){

        Human human = new Human(new int[]{3,3});
        Equipment.Type type = human.getInventory().get(1).getType();
        String result = human.getInventory().get(1).toString();
        humansVsGoblins.getGameWorld()[3][3] = human;
        humansVsGoblins.getHumans().add(human);


        Player player = new Player(new int[]{3,4});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][4] = humansVsGoblins.getPlayer();

        InputStream input = new ByteArrayInputStream("1".getBytes());
        humansVsGoblins.setUserInput(new Scanner(input));

        humansVsGoblins.humansCheckForContact();

        assertTrue(result.contains(humansVsGoblins.getPlayer().getInventory().get(type).toString()), "Test to check inventory item from human is in players inventory failed");

    }

    //Test to see if Human makes contact with goblin result will be that human or Goblin will die
    @DisplayName("Test if Human makes contact with player")
    @Test
    void humansCheckForGoblinTest(){

        boolean combat = false;
        Human human = new Human(new int[]{3,3});
        humansVsGoblins.getHumans().add(human);
        humansVsGoblins.getGameWorld()[3][3] = human;


        Goblin goblin = new Goblin(new int[]{3,2});
        humansVsGoblins.getGoblins().add(goblin);
        humansVsGoblins.getGameWorld()[3][2] = goblin;

        humansVsGoblins.humansCheckForContact();

        if(humansVsGoblins.getGameWorld()[3][2] instanceof Drop || !(humansVsGoblins.getGameWorld()[3][3] instanceof Human)){
            combat = true;
        }


        assertTrue(combat,"Test Human made contact with Goblin failed");

    }


    //Test goblin made contact with player result will be goblin dies or player dies and end the game.
    @DisplayName("Test goblin contacted with Player")
    @Test
    void goblinAttackPlayer(){
        boolean combat = false;
        Goblin goblin = new Goblin(new int[]{3,3});
        humansVsGoblins.getGoblins().add(goblin);
        humansVsGoblins.getGameWorld()[3][3] = goblin;


        Player player = new Player(new int[]{3,2});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][2] = player;

        humansVsGoblins.goblinsPursuePlayer();

        if(humansVsGoblins.getGameWorld()[3][3] instanceof Drop || !humansVsGoblins.getPlay()){
            combat = true;
        }

        assertTrue(combat,"Test Goblin made contact with Human failed");
    }

    //Test goblin moves towards Player result will be goblin moves forward
    @DisplayName("Test goblin pursues player")
    @Test
    void goblinPursuesPlayer(){
        boolean moved = false;
        Goblin goblin = new Goblin(new int[]{3,3});
        humansVsGoblins.getGoblins().add(goblin);
        humansVsGoblins.getGameWorld()[3][3] = goblin;


        Player player = new Player(new int[]{5,3});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][2] = player;

        humansVsGoblins.goblinsPursuePlayer();

        if(humansVsGoblins.getGameWorld()[4][3] instanceof Goblin && !(humansVsGoblins.getGameWorld()[3][3] instanceof Goblin)){
            moved = true;
        }

        assertTrue(moved,"Test Goblin pursues player failed");
    }

    //Test player makes contact with goblin. Result will be goblin dies or player days and game is over.
    @DisplayName("Test goblin pursues player")
    @Test
    void playerAttackGoblin(){

        boolean contact = false;
        Player player = new Player(new int[]{3,3});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][3] = player;

        Goblin goblin = new Goblin(new int[]{4,3});
        humansVsGoblins.getGoblins().add(goblin);
        humansVsGoblins.getGameWorld()[4][3] = goblin;


        InputStream input = new ByteArrayInputStream("s".getBytes());
        humansVsGoblins.setUserInput(new Scanner(input));

        humansVsGoblins.movePlayer();

        if(humansVsGoblins.getGameWorld()[4][3] instanceof Drop || !humansVsGoblins.getPlay()){
            contact = true;
        }

        assertTrue(contact,"Test Player makes contact with Goblin failed");
    }

    //Test if Player moves position the result will have the player move s
    @DisplayName("Test Player moves position")
    @Test
    void playerMoves(){

        Player player = new Player(new int[]{3,3});
        humansVsGoblins.setPlayer(player);
        humansVsGoblins.getGameWorld()[3][3] = player;


        InputStream input = new ByteArrayInputStream("s".getBytes());
        humansVsGoblins.setUserInput(new Scanner(input));

        humansVsGoblins.movePlayer();

        boolean move = humansVsGoblins.getGameWorld()[4][3] instanceof Player;


        assertTrue(move,"Test Move player failed");
    }




    @AfterEach
    void tearDown(){
        if (humansVsGoblins.getUserInput()!=null){
            humansVsGoblins.closeScanner();
        }
    }


}
