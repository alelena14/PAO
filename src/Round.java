import java.util.ArrayList;
import java.util.Scanner;

public class Round {

    private final Character character;
    private final Enemy enemy;

    public Round(Character character, Enemy enemy) {
        this.character = character;
        this.enemy = enemy;
    }

    public int newRound(){
        int characterRound = 1;
        int enemyRound = 1;
        Scanner scanner = new Scanner(System.in);

        // character round
        while(characterRound > 0 && character.isAlive()){
            character.getInfo();

            if(character.isBurned > 0){
                character.isBurned -= 1;
                character.health -= 2;
            }

            // Mage
            if(character instanceof Mage){

                System.out.println("Choose a skill:");
                System.out.println("0) Quit battle");
                System.out.println("1) Attack");
                System.out.println("2) Gain Mana");
                System.out.println("3) Use Healing Potion");

                boolean canUseFireSpell = ((Mage) character).getMana() >= 10;
                boolean canUsePoisonSpell = ((Mage) character).getMana() >= 12;

                if (canUseFireSpell) {
                    System.out.println("4) Use Fire Potion");
                }
                if (canUsePoisonSpell) {
                    System.out.println("5) Use Poison Potion");
                }

                int opt = scanner.nextInt();

                switch (opt) {
                    case 0 -> {
                        System.out.println("You chose to quit the battle.");
                        return 0;
                    }
                    case 1 -> character.attack(enemy);
                    case 2 -> ((Mage) character).gainMana();
                    case 3 -> {
                        ArrayList potions = character.showPotions();
                        if(!potions.isEmpty()) {
                            System.out.println("Choose potion: ");
                            scanner.nextLine();
                            int op = scanner.nextInt();
                            HealthPotion hp = (HealthPotion) potions.get(op);
                            hp.use(character);
                            character.inventory.removeItem(hp);
                        }
                    }
                    case 4 -> {
                        if (canUseFireSpell) {
                            ((Mage) character).useFireSpell(enemy);
                        } else {
                            System.out.println("Invalid option, defaulting to Attack.");
                            character.attack(enemy);
                        }
                    }
                    case 5 -> {
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
                int isShooting = ((Archer) character).getShootsDouble();
                if (isShooting == -1) {
                    character.attack /= 2;
                }

                System.out.println("Choose a skill:");
                System.out.println("0) Quit battle");
                System.out.println("1) Attack");
                System.out.println("2) Gain Energy");
                System.out.println("3) Use Healing Potion");

                boolean canUseDoubleArrows = ((Archer) character).getEnergy() >= 16;

                if (canUseDoubleArrows)
                    System.out.println("4) Use Double Arrows");

                int opt = scanner.nextInt();

                switch (opt) {
                    case 0 -> {
                        System.out.println("You chose to quit the battle.");
                        return 0;
                    }
                    case 1 -> {
                        if (isShooting > 0) {
                            character.attack(enemy);
                            ((Archer) character).setShootsDouble(isShooting - 1);
                        } else {
                            character.attack(enemy);
                        }
                    }
                    case 2 -> ((Archer) character).gainEnergy();
                    case 3 -> {
                        ArrayList potions = character.showPotions();
                        if(!potions.isEmpty()) {
                            System.out.println("Choose potion: ");
                            scanner.nextLine();
                            int op = scanner.nextInt();
                            HealthPotion hp = (HealthPotion) potions.get(op);
                            hp.use(character);
                            character.inventory.removeItem(hp);
                        }
                    }
                    case 4 -> {
                        if (canUseDoubleArrows) {
                            ((Archer) character).doubleArrows(enemy);
                            ((Archer) character).setShootsDouble(isShooting - 1);
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
                System.out.println("0) Quit battle");
                System.out.println("1) Attack");
                System.out.println("2) Gain Speed");
                System.out.println("3) Use Healing Potion");

                boolean canSpeedUp = ((Warrior) character).getSpeed() >= 16;

                if(canSpeedUp)
                    System.out.println("4) Speed Up");

                int opt = scanner.nextInt();

                switch (opt) {
                    case 0 -> {
                        System.out.println("You chose to quit the battle.");
                        return 0;
                    }
                    case 1 -> character.attack(enemy);
                    case 2 -> ((Warrior) character).gainSpeed();
                    case 3 -> {
                        ArrayList potions = character.showPotions();
                        if(!potions.isEmpty()) {
                            System.out.println("Choose potion: ");
                            scanner.nextLine();
                            int op = scanner.nextInt();
                            HealthPotion hp = (HealthPotion) potions.get(op);
                            hp.use(character);
                            character.inventory.removeItem(hp);
                        }
                    }
                    case 4 -> {
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
        while(enemyRound > 0 && enemy.isAlive()){
            enemy.getInfo();

            if(enemy.isPoisoned > 0){
                enemy.health -= 2;
                enemy.isPoisoned -= 1;
            }

            switch (enemy.getClass().getSimpleName()) {
                case "Goblin" -> enemy.attack(character);
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
        return 1;
    }

    private void endRound(Character character, Enemy enemy){
        if(!character.isAlive()){
            System.out.println("The character " + character.name + " has been defeated!");
            // character = null;
        }
        else if(!enemy.isAlive()) {
            ArrayList drops = enemy.drops();
            if(!drops.isEmpty()){
                System.out.print(character.name + " has gained " + drops.get(0) + " gold! ");
                character.addGold((int) drops.get(0));
                System.out.print(character.name + " has gained " + drops.get(1) + " exp! ");
                character.addExp((int) drops.get(1));

                drops.removeFirst();
                drops.removeFirst();

                if(!drops.isEmpty()) {
                    for (var item : drops) {
                        character.addItemToInventory((Item) item);
                    }
                }
            }
        }
    }
}
