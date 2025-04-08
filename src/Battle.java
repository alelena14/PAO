import java.util.*;

public class Battle {
    Character character;
    Character savedState = new Character();

    public enum Difficulty {
        SUPER_EASY, EASY, NORMAL, HARD, VERY_HARD, NIGHTMARE
    }

    public Battle(Character character) {
        this.character = character;
        this.savedState.setStats(character);

        Scanner scanner = new Scanner(System.in);
        int i = 1;
        System.out.println("Choose the difficulty:");
        for (Difficulty difficulty : Difficulty.values()) {
            System.out.println(i + ") " + difficulty.name());
            i += 1;
        }
        int opt = scanner.nextInt();

        switch (opt){
            case 1 -> superEasyBattle(character);
            case 2 -> easyBattle(character);
            case 3 -> normalBattle(character);
            case 4 -> hardBattle(character);
            case 5 -> veryHardBattle(character);
            case 6 -> nightmareBattle(character);
            default -> {
                System.out.println("Invalid option, defaulting to SuperEasy.");
                superEasyBattle(character);
            }
        }

    }

    public void quitBattle() {
        character.setStats(savedState);
        System.out.println("You quit the battle. Character state restored.");
    }

    private void superEasyBattle(Character character){ // 1-3 goblini
        boolean quit = false;
        int goblins = (int)(Math.random() * 3) + 1;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
            Round r = new Round(character, goblin);
            while (goblin.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    quit = true;
                    break;
                }
            }
        }
    }

    private void easyBattle(Character character){ // 4-7 goblini
        boolean quit = false;
        int goblins = (int)(Math.random() * 4) + 4;
        for(int i = 0; i < goblins && !quit; i++){
            Goblin goblin = new Goblin();
            Round r = new Round(character, goblin);
            while (goblin.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    quit = true;
                    break;
                }
            }
        }
    }

    private void normalBattle(Character character){ //2 orci / 1-2 goblini si 1 orc
        boolean quit = false;
        if(Math.random() <= 0.5){
            for (int i = 0; i < 2; i++) {
                Orc orc = new Orc();
                Round r = new Round(character,orc);
                while (orc.isAlive() && character.isAlive())
                    if(r.newRound() == 0){
                        quitBattle();
                        quit = true;
                        break;
                    }
            }
        }else {
            int goblins = (int) (Math.random() * 2) + 1;
            for (int i = 0; i < goblins; i++) {
                Goblin goblin = new Goblin();
                Round r = new Round(character, goblin);
                while (goblin.isAlive() && character.isAlive()) {
                    if(r.newRound() == 0){
                        quitBattle();
                        quit = true;
                        break;
                    }
                }
            }
            if(character.isAlive() && !quit){
                Orc orc = new Orc();
                Round r = new Round(character,orc);
                while (orc.isAlive() && character.isAlive()) {
                    if(r.newRound() == 0){
                        quitBattle();
                        break;
                    }
                }
            }
        }
    }

    private void hardBattle(Character character){ // 2-5 orci
        boolean quit = false;
        int orcs = (int)(Math.random() * 4) + 2;
        for(int i = 0; i < orcs && !quit; i++){
            Orc orc = new Orc();
            Round r = new Round(character, orc);
            while (orc.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    quit = true;
                    break;
                }
            }
        }
    }

    private void veryHardBattle(Character character){ // 3 orci si 1 dragon
        boolean quit = false;
        for(int i = 0; i < 3 && !quit; i++){
            Orc orc = new Orc();
            Round r = new Round(character, orc);
            while (orc.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    quit = true;
                    break;
                }
            }
        }
        if(character.isAlive() && !quit){
            Dragon dragon = new Dragon();
            Round r = new Round(character, dragon);
            while (dragon.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    break;
                }
            }
        }
    }

    private void nightmareBattle(Character character){ // 2-3 dragoni
        boolean quit = false;
        int dragons = (int)(Math.random() * 2) + 2;
        for(int i = 0; i < dragons && !quit; i++){
            Dragon dragon = new Dragon();
            Round r = new Round(character, dragon);
            while (dragon.isAlive() && character.isAlive()) {
                if(r.newRound() == 0){
                    quitBattle();
                    break;
                }
            }
        }
    }
}
