package Battles;

import Characters.*;

import java.sql.SQLException;
import java.util.*;

import Characters.Character;
import Database.CharacterService;
import Enemies.*;

public class Battle {
    Characters.Character character;
    Characters.Character savedState = new Characters.Character();

    public enum Difficulty {
        SUPER_EASY, EASY, NORMAL, HARD, VERY_HARD, NIGHTMARE
    }

    public Battle(Characters.Character character) {
        this.character = character;
        this.savedState.setStats(character);
    }

    public int newBattle(Characters.Character character) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("🎮 Choose your battle difficulty:");
        for (Difficulty difficulty : Difficulty.values()) {
            System.out.println(i + ") " + difficulty.name());
            i += 1;
        }
        int opt = scanner.nextInt();

        switch (opt){
            case 1 -> {
                return superEasyBattle(character);
            }
            case 2 -> {
                return easyBattle(character);
            }
            case 3 -> {
                return normalBattle(character);
            }
            case 4 -> {
                return hardBattle(character);
            }
            case 5 -> {
                return veryHardBattle(character);
            }
            case 6 -> {
                return nightmareBattle(character);
            }
            default -> {
                System.out.println("Invalid option, defaulting to SuperEasy.");
                return superEasyBattle(character);
            }
        }

    }

    public void quitBattle() throws SQLException {
        character.setStats(savedState);
        switch (character) {
            case Mage m ->
                    m.setMana(30 + (character.getLevel() - savedState.getLevel()) * 10);
            case Archer a ->
                    a.setEnergy(30 + (character.getLevel() - savedState.getLevel()) * 10);
            case Warrior w ->
                    w.setSpeed(30 + (character.getLevel() - savedState.getLevel()) * 10);
            default -> throw new IllegalStateException("Unexpected value: " + character.getClass().getSimpleName());
        }
        CharacterService.getInstance().updateCharacter(character);

        System.out.println("⚠️ You chose to quit the battle...");
        System.out.println("🔄 Your character's stats have been restored.");
    }

    private void winBattle(Difficulty difficulty) throws SQLException {
        System.out.println("\n✅ Battles.Battle complete! Victory recorded for difficulty: " + difficulty);
        character.incrementBattlesWon(difficulty);
        character.setHealth(savedState.getHealth() + (character.getLevel() - savedState.getLevel()) * 10); // reset health
        character.setAttack(savedState.getAttack() + (character.getLevel() - savedState.getLevel()) * 10);
        CharacterService.getInstance().updateCharacter(character);
        switch (character) {
            case Mage m ->
                    m.setMana(30 + (character.getLevel() - savedState.getLevel()) * 10);
            case Archer a ->
                    a.setEnergy(30 + (character.getLevel() - savedState.getLevel()) * 10);
            case Warrior w ->
                    w.setSpeed(30 + (character.getLevel() - savedState.getLevel()) * 10);
            default -> throw new IllegalStateException("Unexpected value: " + character.getClass().getSimpleName());
        }

    }

    private int superEasyBattle(Characters.Character character) throws SQLException { // 1-3 goblins
        boolean quit = false;
        int goblins = (int)(Math.random() * 3) + 1;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
            System.out.println("\n💥 A Goblin has spawned from the shadows! 🗡️");
            Round r = new Round(character, goblin);
            while (goblin.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.SUPER_EASY);
        return 0;
    }

    private int easyBattle(Characters.Character character) throws SQLException { // 4-7 goblins
        boolean quit = false;
        int goblins = (int)(Math.random() * 4) + 4;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
            System.out.println("\n💥 A Goblin has spawned from the shadows! 🗡️");
            Round r = new Round(character, goblin);
            while (goblin.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.EASY);
        return 0;
    }

    private int normalBattle(Characters.Character character) throws SQLException { //2 orcs / 1-2 goblins and 1 orc
        boolean quit = false;
        if(Math.random() <= 0.5){
            for (int i = 0; i < 2; i++) {
                Orc orc = new Orc();
                System.out.println("\n⚔️ A wild Enemies.Orc roars and charges into battle! 🧟");
                Round r = new Round(character,orc);
                while (orc.isAlive() && character.isAlive()) {
                    int roundStatus = r.newRound();
                    if (roundStatus == 0) {
                        quitBattle();
                        quit = true;
                        break;
                    } else if (roundStatus == -1) {
                        return -1;
                    }
                }
            }
        }else {
            int goblins = (int) (Math.random() * 2) + 1;
            for (int i = 0; i < goblins; i++) {
                Goblin goblin = new Goblin();
                System.out.println("\n💥 A Goblin has spawned from the shadows! 🗡️");
                Round r = new Round(character, goblin);
                while (goblin.isAlive() && character.isAlive()) {
                    int roundStatus = r.newRound();
                    if(roundStatus == 0){
                        quitBattle();
                        quit = true;
                        break;
                    } else if (roundStatus == -1) {
                        return -1;
                    }
                }
            }
            if(character.isAlive() && !quit){
                Orc orc = new Orc();
                System.out.println("\n⚔️ A wild Orc roars and charges into battle! 🧟");
                Round r = new Round(character,orc);
                while (orc.isAlive() && character.isAlive()) {
                    int roundStatus = r.newRound();
                    if(roundStatus == 0){
                        quitBattle();
                        quit = true;
                        break;
                    } else if (roundStatus == -1) {
                        return -1;
                    }
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.NORMAL);
        return 0;
    }

    private int hardBattle(Characters.Character character) throws SQLException { // 2-5 orcs
        boolean quit = false;
        int orcs = (int)(Math.random() * 4) + 2;
        for(int i = 0; i < orcs && !quit; i++){
            Orc orc = new Orc();
            System.out.println("\n⚔️ A wild Orc roars and charges into battle! 🧟");
            Round r = new Round(character, orc);
            while (orc.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.HARD);
        return 0;
    }

    private int veryHardBattle(Characters.Character character) throws SQLException { // 3 orcs and 1 dragon
        boolean quit = false;
        for(int i = 0; i < 3 && !quit; i++){
            Orc orc = new Orc();
            System.out.println("\n⚔️ A wild Orc roars and charges into battle! 🧟");
            Round r = new Round(character, orc);
            while (orc.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit){
            Dragon dragon = new Dragon();
            System.out.println("\n🔥 A mighty Dragon descends from the skies! 🐉");
            Round r = new Round(character, dragon);
            while (dragon.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.VERY_HARD);
        return 0;
    }

    private int nightmareBattle(Characters.Character character) throws SQLException { // 2-3 dragons
        boolean quit = false;
        int dragons = (int)(Math.random() * 2) + 2;
        for(int i = 0; i < dragons && !quit; i++){
            Dragon dragon = new Dragon();
            System.out.println("\n🔥 A mighty Dragon descends from the skies! 🐉");
            Round r = new Round(character, dragon);
            while (dragon.isAlive() && character.isAlive()) {
                int roundStatus = r.newRound();
                if(roundStatus == 0){
                    quitBattle();
                    quit = true;
                    break;
                } else if (roundStatus == -1) {
                    return -1;
                }
            }
        }
        if(character.isAlive() && !quit)
            winBattle(Difficulty.NIGHTMARE);
        return 0;
    }
}
