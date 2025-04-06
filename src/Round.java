import java.util.ArrayList;
import java.util.Scanner;

public class Round {

    private Character character;
    private Enemy enemy;
    private int characterRound;
    private int enemyRound;

    public Round(Character player, Enemy enemy) {
        this.character = player;
        this.enemy = enemy;
    }

    public void newRound(){
        characterRound = 1;
        enemyRound = 1;
        Scanner scanner = new Scanner(System.in);

        // character round
        while(characterRound > 0){

            if(character.isBurned > 0){
                character.isBurned -= 1;
                character.health -= 2;
            }

            // Mage
            if(character instanceof Mage){

                System.out.println("Choose a skill:");
                System.out.println("1) Attack");
                System.out.println("2) Gain Mana");

                boolean canUseFireSpell = ((Mage) character).getMana() >= 10;
                boolean canUsePoisonSpell = ((Mage) character).getMana() >= 12;

                if (canUseFireSpell) {
                    System.out.println("3) Use Fire Potion");
                }
                if (canUsePoisonSpell) {
                    System.out.println("4) Use Poison Potion");
                }

                int opt = scanner.nextInt();

                switch (opt) {
                    case 1 -> character.attack(enemy);
                    case 2 -> ((Mage) character).gainMana();
                    case 3 -> {
                        if (canUseFireSpell) {
                            ((Mage) character).useFireSpell(enemy);
                        } else {
                            System.out.println("Invalid option, defaulting to Attack.");
                            character.attack(enemy);
                        }
                    }
                    case 4 -> {
                        if (canUsePoisonSpell) {
                            ((Mage) character).usePoisonSpell(enemy);
                        } else {
                            System.out.println("Invalid option, defaulting to Attack.");
                            character.attack(enemy);
                        }
                    }
                    default -> {
                        System.out.println("Invalid option, defaulting to Attack.");
                        character.attack(enemy);
                    }
                }



            // Archer
            } else if (character instanceof Archer) {

                if (((Archer) character).shootsDouble == -1) {
                    character.attack /= 2;
                }

                System.out.println("Choose a skill:");
                System.out.println("1) Attack");
                System.out.println("2) Gain Energy");

                boolean canUseDoubleArrows = ((Archer) character).getEnergy() >= 16;

                if (canUseDoubleArrows)
                    System.out.println("3) Use Double Arrows");

                int opt = scanner.nextInt();

                switch (opt) {
                    case 1 -> {
                        if (((Archer) character).shootsDouble > 0) {
                            character.attack(enemy);
                            ((Archer) character).shootsDouble -= 1;
                        } else {
                            character.attack(enemy);
                        }
                    }
                    case 2 -> ((Archer) character).gainEnergy();
                    case 3 -> {
                        if (canUseDoubleArrows) {
                            ((Archer) character).doubleArrows(enemy);
                            ((Archer) character).shootsDouble -= 1;
                        } else {
                            System.out.println("Invalid option, defaulting to Attack.");
                            character.attack(enemy);
                        }
                    }
                    default -> {
                        System.out.println("Invalid option, defaulting to Attack.");
                        character.attack(enemy);
                    }
                }


            // Warrior
            }else {

                System.out.println("Choose a skill:");
                System.out.println("1) Attack");
                System.out.println("2) Gain Speed");

                boolean canSpeedUp = ((Warrior) character).getSpeed() >= 16;

                if(canSpeedUp)
                    System.out.println("3) Speed Up");

                int opt = scanner.nextInt();

                switch (opt) {
                    case 1 -> character.attack(enemy);
                    case 2 -> ((Warrior) character).gainSpeed();
                    case 3 -> {
                        if (canSpeedUp) {
                            characterRound += 2;
                            ((Warrior) character).useSpeed();
                        } else {
                            System.out.println("Invalid option, defaulting to Attack.");
                            character.attack(enemy);
                        }
                    }
                    default -> {
                        System.out.println("Invalid option, defaulting to Attack.");
                        character.attack(enemy);
                    }
                }
            }

            characterRound -= 1;

            if(!enemy.isAlive())
                endRound(character, enemy);
        }

        // enemy round
        while(enemyRound > 0){

            switch (enemy.getClass().getSimpleName()) {
                case "Goblin" -> {
                    enemy.attack(character);
                }
                case "Orc" -> {
                    if (((Orc) enemy).getEnergy() >= 11)
                        ((Orc) enemy).throwAxe(character);
                    else
                        enemy.attack(character);
                }
                case "Dragon" -> {
                    if (((Dragon) enemy).getEnergy() >= 18)
                        ((Dragon) enemy).breatheFire(character);
                    else
                        enemy.attack(character);
                }
                default -> System.out.println("Unknown enemy type.");

            };

            enemyRound -= 1;
            if(!character.isAlive())
                endRound(character, enemy);
        }
    }

    private void endRound(Character character, Enemy enemy){
        if(!character.isAlive()){
            character = null;
            System.out.println("The character " + character.name + " has been defeated!");
        }
        else if(!enemy.isAlive()) {
            ArrayList drops = enemy.drops();
            if(!drops.isEmpty()){
                character.addGold((int) drops.get(0));
                System.out.println(character.name + " has gained " + character.gold + " gold!");
                character.addExp((int) drops.get(1));
                System.out.println(character.name + " has gained " + character.exp + " exp!");

                drops.remove(0);
                drops.remove(1);

                if(!drops.isEmpty()) {
                    for (var item : drops) {
                        character.addItemToInventory((Item) item);
                        System.out.println(character.name + " has obtained: " + ((Item) item).name + "!");
                    }
                }
            }
        }
    }
}
