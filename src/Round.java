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
            if(character instanceof Archer archer){
                int isShooting = archer.getShootsDouble();

                if (isShooting >= 0) {
                    ((Archer) character).setShootsDouble(isShooting - 1);
                    isShooting -= 1;
                }

                if (isShooting == -1) {
                    character.attack /= 2;
                    archer.setShootsDouble(-10);
                }
            }
            character.getInfo();

            if (character.isBurned > 0) {
                character.isBurned -= 1;
                character.health -= 2;
                System.out.println("🔥 You are burned! Lost 2 HP.");
            }


            // Mage
            switch (character) {
                case Mage mage -> {
                    boolean canUseFireSpell = mage.getMana() >= 10;
                    boolean canUsePoisonSpell = mage.getMana() >= 12;

                    System.out.println("\n🎯 Choose your action:");
                    System.out.println("0️⃣ Quit battle");
                    System.out.println("1️⃣ 🗡️ Attack");
                    System.out.println("2️⃣ 💧 Gain Mana");
                    if (character.hasPotions()) System.out.println("3️⃣ 🧪 Use Healing Potion");
                    if (canUseFireSpell) System.out.println("4️⃣ 🔥 Use Fire Potion");
                    if (canUsePoisonSpell) System.out.println("5️⃣ ☠️ Use Poison Potion");

                    int opt = scanner.nextInt();

                    switch (opt) {
                        case 0 -> {
                            return 0;
                        }
                        case 1 -> character.attack(enemy);
                        case 2 -> ((Mage) character).gainMana();
                        case 3 -> {
                            ArrayList<HealthPotion> potions = character.showPotions();
                            if (!potions.isEmpty()) {
                                character.showPotions();
                                System.out.println("\nChoose potion: ");
                                scanner.nextLine();
                                int op = scanner.nextInt();
                                HealthPotion hp = potions.get(op - 1);
                                hp.use(character);
                                character.inventory.removeItem(hp);
                            } else {
                                System.out.println("Invalid option, defaulting to Attack.");
                                character.attack(enemy);
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


                }
                // Archer
                case Archer archer -> {

                    boolean canUseDoubleArrows = archer.getEnergy() >= 16;

                    System.out.println("\n🎯 Choose your action:");
                    System.out.println("0️⃣ Quit battle");
                    System.out.println("1️⃣ 🗡️ Attack");
                    System.out.println("2️⃣ 🏹 Gain Energy");
                    if (character.hasPotions()) System.out.println("3️⃣ 🧪 Use Healing Potion");
                    if (canUseDoubleArrows) System.out.println("4️⃣ 🏹 Use Double Arrows");

                    int opt = scanner.nextInt();

                    switch (opt) {
                        case 0 -> {
                            return 0;
                        }
                        case 1 -> character.attack(enemy);

                        case 2 -> ((Archer) character).gainEnergy();
                        case 3 -> {
                            ArrayList<HealthPotion> potions = character.showPotions();
                            if (!potions.isEmpty()) {
                                System.out.println("\nChoose potion: ");
                                scanner.nextLine();
                                int op = scanner.nextInt();
                                HealthPotion hp = potions.get(op - 1);
                                hp.use(character);
                                character.inventory.removeItem(hp);
                            } else {
                                System.out.println("Invalid option, defaulting to Attack.");
                                character.attack(enemy);
                            }
                        }
                        case 4 -> {
                            if (canUseDoubleArrows) {
                                ((Archer) character).doubleArrows(enemy);
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
                // Warrior
                case Warrior warrior -> {

                    boolean canSpeedUp = warrior.getSpeed() >= 16;


                    System.out.println("\n🎯 Choose your action:");
                    System.out.println("0️⃣ Quit battle");
                    System.out.println("1️⃣ 🗡️ Attack");
                    System.out.println("2️⃣ ⚡ Gain Energy");
                    if (character.hasPotions()) System.out.println("3️⃣ 🧪 Use Healing Potion");
                    if (canSpeedUp) System.out.println("4️⃣ ⚡ Speed Up");

                    int opt = scanner.nextInt();

                    switch (opt) {
                        case 0 -> {
                            return 0;
                        }
                        case 1 -> character.attack(enemy);
                        case 2 -> ((Warrior) character).gainSpeed();
                        case 3 -> {
                            ArrayList<HealthPotion> potions = character.showPotions();
                            if (!potions.isEmpty()) {
                                character.showPotions();
                                System.out.println("\nChoose potion: ");
                                scanner.nextLine();
                                int op = scanner.nextInt();
                                HealthPotion hp = potions.get(op - 1);
                                hp.use(character);
                                character.inventory.removeItem(hp);
                            } else {
                                System.out.println("Invalid option, defaulting to Attack.");
                                character.attack(enemy);
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
                default -> throw new IllegalStateException("Unexpected value: " + character);
            }

            characterRound -= 1;

            if(!enemy.isAlive())
                return endRound(character, enemy);
        }

        // enemy round
        while(enemyRound > 0 && enemy.isAlive()){
            enemy.getInfo();

            if (enemy.isPoisoned > 0) {
                enemy.isPoisoned -= 1;
                enemy.health -= 3;
                System.out.println("☠️ Enemy is poisoned! -3 HP.");
            }


            switch (enemy.getClass().getSimpleName()) {
                case "Goblin" -> enemy.attack(character);
                case "Orc" -> {
                    if (((Orc) enemy).getEnergy() >= 11)
                        ((Orc) enemy).throwAxe(character);
                    else
                        enemy.attack(character);
                    ((Orc) enemy).setEnergy(((Orc) enemy).getEnergy() + 3); // gets 3 energy at the end of the round
                }
                case "Dragon" -> {
                    if (((Dragon) enemy).getEnergy() >= 18)
                        ((Dragon) enemy).breatheFire(character);
                    else
                        enemy.attack(character);
                    ((Dragon) enemy).setEnergy(((Dragon) enemy).getEnergy() + 3);
                }
                default -> System.out.println("Unknown enemy type.");

            }

            enemyRound -= 1;
            if(!character.isAlive())
                return endRound(character, enemy);

        }
        return 1;
    }

    private int endRound(Character character, Enemy enemy){
        if(!character.isAlive()){
            System.out.println("☠️ " + character.name + " has fallen in battle...");
            return -1;
        }
        else if(!enemy.isAlive()) {
            ArrayList<Object> drops = enemy.drops();
            System.out.println("🎉 " + enemy.getClass().getSimpleName() + " defeated!");

            if(!drops.isEmpty()){
                System.out.println("💰 Rewards:");
                System.out.println("   🪙 Gold: " + drops.get(0));
                character.addGold((int) drops.get(0));
                System.out.println("   ⭐ EXP: " + drops.get(1));
                character.addExp((int) drops.get(1));

                drops.removeFirst();
                drops.removeFirst();

                if(!drops.isEmpty()) {
                    for (var item : drops) {
                        character.addItemToInventory((Item) item);
                    }
                }
            }
            return 1;
        }
        return 0;
    }
}
