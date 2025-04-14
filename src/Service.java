import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Service {
    List<Character> characters = new ArrayList<Character>();
    Character currentCharacter = null;

    void newCharacter(){
        Character character = new Character().chooseCharacter();
        characters.add(character);
        currentCharacter = character;
    }

    void getInfo(Character character){
        character.getInfo();
        System.out.println("Level: " + character.level);
        System.out.println("Exp: " + character.exp);
        System.out.println("Gold: " + character.gold);
    }

    void viewAllCharacters(){
        if(characters.isEmpty()){
            System.out.println("❌ No characters.");
            return;
        }
        for(Character character: characters){
            getInfo(character);
            System.out.println();
        }
    }

    void chooseCurrentCharacter() {
        Scanner scanner = new Scanner(System.in);

        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }

        System.out.println("\n🎮 Choose your character:");
        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            String type = c.getClass().getSimpleName();
            String emoji = switch (type) {
                case "Mage" -> "🧙";
                case "Archer" -> "🏹";
                default -> "⚔️";
            };

            System.out.println(i + ") " + emoji + " " + c.name + " - ❤️ " + c.health + " HP");
        }

        int choice = -1;
        while (choice < 0 || choice >= characters.size()) {
            System.out.print("Enter the number of the character you want to play with: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clean buffer
                System.out.println("❗ Please enter a valid number.");
                return;
            }
        }

        currentCharacter = characters.get(choice);
        System.out.println("✅ You selected: " + currentCharacter.name + " the " + currentCharacter.getClass().getSimpleName() + "!");
    }

    void upgradeWeapon() {
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        if(currentCharacter.weapon != null){
            System.out.println("Choose option:");
            System.out.println("1) Level up your weapon");
            System.out.println("2) Enhance your weapon");

            Scanner scanner = new Scanner(System.in);
            int opt = scanner.nextInt();

            switch (opt) {
                case 1 -> {
                    currentCharacter.attack -= currentCharacter.weapon.getDamage();
                    currentCharacter.upgradeWeapon();
                    currentCharacter.attack += currentCharacter.weapon.getDamage();
                }
                case 2 ->{
                    switch (currentCharacter){
                        case Mage _ -> {
                            currentCharacter.attack -= currentCharacter.weapon.getDamage();
                            ((Mage) currentCharacter).enhanceGrimoire();
                            currentCharacter.attack += currentCharacter.weapon.getDamage();
                        }
                        case Archer _ -> {
                            currentCharacter.attack -= currentCharacter.weapon.getDamage();
                            ((Archer) currentCharacter).enhanceBow();
                            currentCharacter.attack += currentCharacter.weapon.getDamage();
                        }
                        default -> {
                            currentCharacter.attack -= currentCharacter.weapon.getDamage();
                            ((Warrior) currentCharacter).enhanceSword();
                            currentCharacter.attack += currentCharacter.weapon.getDamage();
                        }
                    }
                }
            }
        }else {
            System.out.println("You have no weapon equipped.");
        }
    }

    void viewInventory(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        currentCharacter.showInventory();
    }

    void equipWeapon() {
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        ArrayList<Item> items = currentCharacter.inventory.getItems();
        List<Weapon> weapons = new ArrayList<>();

        if (items.isEmpty()) {
            System.out.println("You have no weapons to equip!");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            if (currentItem instanceof Weapon wp) {
                weapons.add(wp);
                System.out.println(i + ": " + wp.name + ", damage: " + wp.getDamage() + ", level: " + wp.getLevel()
                        + ", value: " + wp.getValue());
            }
        }

        if (weapons.isEmpty()) {
            System.out.println("❌ No weapons found in your inventory.");
            return;
        }

        System.out.println("🛡️ Choose a weapon to equip (enter the index):");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();

        if (index < 0 || index >= items.size() || !(items.get(index) instanceof Weapon selectedWeapon)) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        if(selectedWeapon.use(currentCharacter) == 0) {
            currentCharacter.inventory.removeItem(selectedWeapon);
            System.out.println("✅ Equipped " + selectedWeapon.name + " successfully!");
        }
    }

    void buyHealingPotion() {
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🧪 Available Healing Potions:");
        System.out.println("1) Small Potion 🧴 - Cost: 10💰, Heal: 5❤️, Value: 5");
        System.out.println("2) Medium Potion 🧪 - Cost: 15💰, Heal: 10❤️, Value: 10");
        System.out.println("3) Large Potion 🧫 - Cost: 25💰, Heal: 20❤️, Value: 20");
        System.out.println("0) Cancel");

        System.out.print("Choose a potion to buy (enter the number): ");
        int choice = scanner.nextInt();

        int cost = 0;
        HealthPotion potion = null;

        switch (choice) {
            case 1 -> {
                cost = 10;
                potion = new HealthPotion(5, 5);
            }
            case 2 -> {
                cost = 15;
                potion = new HealthPotion(10, 10);
            }
            case 3 -> {
                cost = 25;
                potion = new HealthPotion(20, 20);
            }
            case 0 -> {
                System.out.println("❌ Purchase cancelled.");
                return;
            }
            default -> {
                System.out.println("❌ Invalid option.");
                return;
            }
        }

        if (currentCharacter.gold < cost) {
            System.out.println("💸 Not enough gold!");
            return;
        }

        currentCharacter.addGold(-cost);
        currentCharacter.inventory.addItem(potion);
        System.out.println("✅ You bought a " + potion.getName() + "! It's been added to your inventory.");
    }

    void buyWeapon(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🏹 Available Bows:");
        System.out.println("1) Wooden Bow 🪵 - Damage: 5⚔️, Cost: 15💰, Value: 10");
        System.out.println("2) Elven Bow 🍃 - Damage: 12⚔️, Cost: 40💰, Value 30");
        System.out.println("3) Dragonbone Bow 🐉 - Damage: 25⚔️, Cost: 90💰, Value: 75");

        System.out.println("\n🗡️ Available Swords:");
        System.out.println("4) Rusty Sword 🧷 - Damage: 4⚔️, Cost: 12💰, Value: 8");
        System.out.println("5) Steel Blade ⚒️ - Damage: 11⚔️, Cost: 35💰, Value: 25");
        System.out.println("6) Shadowfang 🗡️ - Damage: 24⚔️, Cost: 85💰, Value: 60");

        System.out.println("\n📖 Available Grimoires:");
        System.out.println("7) Torn Grimoire 📜 - Spell Power: 6✨, Cost: 16💰, Value: 10");
        System.out.println("8) Arcane Codex 📘 - Spell Power: 14✨, Cost: 48💰, Value: 40");
        System.out.println("9) Necronomicon ☠️ - Spell Power: 30✨, Cost: 100💰, Value: 85");
        System.out.println("0) Cancel");


        System.out.print("Choose a weapon to buy (enter the number): ");
        int choice = scanner.nextInt();

        int cost = 0;
        Weapon weapon = null;

        switch (choice) {
            case 1 -> {
                cost = 15;
                weapon = new Weapon("Wooden Bow", 5, "Bow", 10);
            }
            case 2 -> {
                cost = 40;
                weapon = new Weapon("Elven Bow", 12, "Bow", 30);
            }
            case 3 -> {
                cost = 90;
                weapon = new Weapon("Dragonbone Bow", 25, "Bow", 75);
            }
            case 4 -> {
                cost = 12;
                weapon = new Weapon("Rusty Sword", 4, "Sword", 8);
            }
            case 5 -> {
                cost = 35;
                weapon = new Weapon("Steel Blade", 11, "Sword", 25);
            }
            case 6 -> {
                cost = 85;
                weapon = new Weapon("Shadowfang", 24, "Sword", 60);
            }
            case 7 -> {
                cost = 16;
                weapon = new Weapon("Torn Grimoire", 6, "Grimoire", 10);
            }
            case 8 -> {
                cost = 48;
                weapon = new Weapon("Arcane Codex", 14, "Grimoire", 40);
            }
            case 9 -> {
                cost = 100;
                weapon = new Weapon("Necronomicon", 30, "Grimoire", 85);
            }
            case 0 -> {
                System.out.println("❌ Purchase cancelled.");
                return;
            }
            default -> {
                System.out.println("❌ Invalid option.");
                return;
            }
        }


        if (currentCharacter.gold < cost) {
            System.out.println("💸 Not enough gold!");
            return;
        }

        currentCharacter.addGold(-cost);
        currentCharacter.inventory.addItem(weapon);
        System.out.println("✅ You bought a " + weapon.getName() + "! It's been added to your inventory.");
    }


    void sellItemFromInventory(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        currentCharacter.showInventory();

        if(!currentCharacter.inventory.getItems().isEmpty()) {
            int index = -1;

            while (index < 0 || index >= currentCharacter.inventory.getItems().size()) {
                System.out.print("🔢 Choose the index of the item you want to sell (or -1 to cancel): ");
                try {
                    index = scanner.nextInt();
                    if (index == -1) {
                        System.out.println("❌ Sell cancelled.");
                        return;
                    }
                } catch (InputMismatchException e) {
                    scanner.nextLine(); // clear buffer
                    System.out.println("❗ Please enter a valid number.");
                }
            }
            currentCharacter.sellItem(currentCharacter.inventory.getItems().get(index));
        }
    }

    void newBattle(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        Battle battle = new Battle(currentCharacter);
        if(battle.newBattle(currentCharacter) == -1)
            characters.remove(currentCharacter);
    }

    void viewBattleVictories(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        currentCharacter.printBattlesWon();
    }

    void displayBattleTypes() {
        System.out.println("🗺️ Available Battle Difficulties:\n");

         System.out.print("🔸  Super-Easy");
         System.out.println(" - Contents: 1-3 Goblins");

         System.out.print("🔸  Easy");
         System.out.println(" - Contents: 4-7 Goblins");

         System.out.print("🔸  Normal");
         System.out.println(" - Contents: 2 Orcs or 1-2 Goblins and 1 Orc");

         System.out.print("🔸  Hard");
         System.out.println(" - Contents: 2-5 Orcs");

         System.out.print("🔸  Very-Hard");
         System.out.println(" - Contents: 3 Orcs and 1 Dragon");

         System.out.print("🔸  Nightmare");
         System.out.println(" - Contents: 2-3 Dragons");
    }

    void getEnemyInfo(){
        new Goblin().getInfo();
        System.out.println();
        new Orc().getInfo();
        System.out.println();
        new Dragon().getInfo();
        System.out.println();
    }

    public void showCharacterInfo() {
        System.out.println("📜 === Character Classes ===");

        // Archer
        System.out.println("\n🏹 Archer:");
        System.out.println(" - Health: 25 ❤️");
        System.out.println(" - Attack: 8 ⚔️");
        System.out.println(" - Defense: 6 🛡️");
        System.out.println(" - Energy: 30 ⚡");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Double Arrows: consumes 16 energy, doubles attack (damage = " + (8 * 2) + " before defense)");
        System.out.println("   • Gain Energy: restores 20 energy");
        System.out.println("   • Enhance Bow: +20 damage if you have a 'Rare Gem'");

        // Mage
        System.out.println("\n🧙 Mage:");
        System.out.println(" - Health: 20 ❤️");
        System.out.println(" - Attack: 10 ⚔️");
        System.out.println(" - Defense: 5 🛡️");
        System.out.println(" - Mana: 30 ✨");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Fire Spell: -10 mana, +5 damage bonus (15 total damage before defense), ineffective vs Dragons");
        System.out.println("   • Poison Spell: -12 mana, applies poison (-3 HP for 2 turns), normal attack damage");
        System.out.println("   • Gain Mana: restores 20 mana");
        System.out.println("   • Enhance Grimoire: +20 damage if you have a 'Rare Gem'");

        // Warrior
        System.out.println("\n⚔️ Warrior:");
        System.out.println(" - Health: 30 ❤️");
        System.out.println(" - Attack: 5 ⚔️");
        System.out.println(" - Defense: 7 🛡️");
        System.out.println(" - Speed: 30 🌀");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Use Speed: consumes 16 speed (attacks for 2 more rounds)");
        System.out.println("   • Gain Speed: restores 20 speed");
        System.out.println("   • Enhance Sword: +12 damage if you have a 'Dragon Scale'");
    }


}
