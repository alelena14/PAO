import java.util.*;

public class Battle {
    Character character;
    Character savedState = new Character();

    enum Difficulty {
        SUPER_EASY, EASY, NORMAL, HARD, VERY_HARD, NIGHTMARE
    }

    public Battle(Character character) {
        this.character = character;
        this.savedState.setStats(character);
    }

    public int newBattle(Character character) {
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

    public void quitBattle() {
        character.setStats(savedState);
        System.out.println("⚠️ You chose to quit the battle...");
        System.out.println("🔄 Your character's stats have been restored.");
    }

    private void winBattle(Difficulty difficulty) {
        System.out.println("\n✅ Battle complete! Victory recorded for difficulty: " + difficulty);
        character.incrementBattlesWon(difficulty);
        character.health = savedState.health + (character.level - savedState.level) * 10; // reset health
    }

    private int superEasyBattle(Character character){ // 1-3 goblins
        boolean quit = false;
        int goblins = (int)(Math.random() * 3) + 1;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
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
        if(character.isAlive())
            winBattle(Difficulty.SUPER_EASY);
        return 0;
    }

    private int easyBattle(Character character){ // 4-7 goblins
        boolean quit = false;
        int goblins = (int)(Math.random() * 4) + 4;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
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
        if(character.isAlive())
            winBattle(Difficulty.EASY);
        return 0;
    }

    private int normalBattle(Character character){ //2 orcs / 1-2 goblins and 1 orc
        boolean quit = false;
        if(Math.random() <= 0.5){
            for (int i = 0; i < 2; i++) {
                Orc orc = new Orc();
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
        if(character.isAlive())
            winBattle(Difficulty.NORMAL);
        return 0;
    }

    private int hardBattle(Character character){ // 2-5 orcs
        boolean quit = false;
        int orcs = (int)(Math.random() * 4) + 2;
        for(int i = 0; i < orcs && !quit; i++){
            Orc orc = new Orc();
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
        if(character.isAlive())
            winBattle(Difficulty.HARD);
        return 0;
    }

    private int veryHardBattle(Character character){ // 3 orcs and 1 dragon
        boolean quit = false;
        for(int i = 0; i < 3 && !quit; i++){
            Orc orc = new Orc();
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
        if(character.isAlive())
            winBattle(Difficulty.VERY_HARD);
        return 0;
    }

    private int nightmareBattle(Character character){ // 2-3 dragons
        boolean quit = false;
        int dragons = (int)(Math.random() * 2) + 2;
        for(int i = 0; i < dragons && !quit; i++){
            Dragon dragon = new Dragon();
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
        if(character.isAlive())
            winBattle(Difficulty.NIGHTMARE);
        return 0;
    }
}
