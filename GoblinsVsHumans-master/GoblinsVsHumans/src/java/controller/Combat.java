package controller;

import creatures.Goblin;
import creatures.Human;
import creatures.Player;

public class Combat {

    public static void goblinAttack(Object o, Goblin g){

        if(o instanceof Human){
            ((Human) o).setHealth(((Human) o).getHealth()-(int)(Math.random()*g.getStrength()+1));
        } else if (o instanceof Player) {
            ((Player) o).setHealth(((Player) o).getHealth()-(int)(Math.random()*g.getStrength()+1));
        }
    }

    public static void humanAttackGoblin(Human h, Goblin g){
        g.setHealth(g.getHealth()-(int)(Math.random()*h.getStrength()+1));
    }

    public static void playerAttackGoblin(Player p, Goblin g){
        g.setHealth(g.getHealth()-(int)(Math.random()*p.getStrength()+1));
    }

    public static String goblinVsHuman(Human h, Goblin g,HumansVsGoblins game){
        String result = "";
        while (h.getHealth()>0 && g.getHealth()>0){
            goblinAttack(h,g);
            humanAttackGoblin(h,g);
        }
        if (h.getHealth()<=0 && g.getHealth()<=0){
            game.removeGoblin(g);
            game.removeHuman(h);
            result += "A Human and Goblin both killed each other." + "\n";
        } else if (h.getHealth()<=0) {
            game.removeHuman(h);
            result += "A Human has been killed by the Goblin." + "\n";
        }else if(g.getHealth()<=0){
            game.removeGoblin(g);
            result += "A Goblin has been killed by a Human." + "\n";
        }
        return result;
    }

    public static String playerVsGoblin(Player p, Goblin g, HumansVsGoblins game){
        String result = "";

        while (p.getHealth()>0 && g.getHealth()>0){
            goblinAttack(p,g);
            playerAttackGoblin(p,g);
        }
        if (p.getHealth()<=0 && g.getHealth()<=0){
            game.setPlay(false);
            result += "You and the goblin Killed each other: GAME OVER!" + "\n";
        } else if (p.getHealth()<=0) {
            game.setPlay(false);
            result += "You have been killed by the Goblin: GAME OVER!" + "\n";
        }else if(g.getHealth()<=0){
            game.removeGoblin(g);
            result += "Great Job you have killed the goblin." + "\n";
        }

        return result;
    }

}
