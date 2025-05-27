package service;

import Battles.*;
import Characters.*;
import Characters.Character;
import Database.CharacterService;
import Database.InventoryService;
import Database.ItemService;
import Enemies.*;
import Items.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Service {
    static CharacterService characterService;

    static {
        try {
            characterService = CharacterService.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ItemService itemService = ItemService.getInstance();
    InventoryService inventoryService = InventoryService.getInstance();
    static Characters.Character currentCharacter = null;

    public Service() throws SQLException {
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public void newCharacter() throws SQLException {
        Character character = new Characters.Character().chooseCharacter();
        characterService.createCharacter(character);
        currentCharacter = character;
    }

    public static void getInfo(Characters.Character character){
        character.getInfo();
        System.out.println("⭐ Level: " + character.getLevel());
        System.out.println("🧫 Exp: " + character.getExp());
        System.out.println("💰 Gold: " + character.getGold());
    }

    public void viewAllCharacters() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        characterService.showAllCharacters();
    }

    public static void chooseCurrentCharacter() {
        Scanner scanner = new Scanner(System.in);

        List<Characters.Character> charactersFromDb;
        try {
            charactersFromDb = characterService.getAllCharacters();
        } catch (SQLException e) {
            System.out.println("❌ Failed to load characters from database.");
            return;
        }

        if (charactersFromDb.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }

        System.out.println("\n🎮 Choose your character:");
        for (int i = 0; i < charactersFromDb.size(); i++) {
            Characters.Character c = charactersFromDb.get(i);
            String type = c.getClass().getSimpleName();
            String emoji = switch (type) {
                case "Mage" -> "🧙";
                case "Archer" -> "🏹";
                default -> "⚔️";
            };

            System.out.println(i + ") " + emoji + " " + c.getName() + " - ❤️ " + c.getHealth() + " HP");
        }

        int choice = -1;
        while (choice < 0 || choice >= charactersFromDb.size()) {
            System.out.print("Enter the number of the character you want to play with: ");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine(); // clean buffer
                System.out.println("❗ Please enter a valid number.");
                return;
            }
        }

        Characters.Character selected = charactersFromDb.get(choice);

        try {
            currentCharacter = characterService.readCharacter(selected.getId());
            System.out.println("✅ You selected: " + currentCharacter.getName() + " the " + currentCharacter.getClass().getSimpleName() + "!");
        } catch (SQLException e) {
            System.out.println("❌ Failed to load selected character from DB.");
        }
    }

    public void upgradeWeapon() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        if(currentCharacter.getWeapon() != null){
            System.out.println("Choose option:");
            System.out.println("1) Level up your weapon");
            System.out.println("2) Enhance your weapon");

            Scanner scanner = new Scanner(System.in);
            int opt = scanner.nextInt();

            switch (opt) {
                case 1 -> {
                    currentCharacter.setAttack(currentCharacter.getAttack() - currentCharacter.getWeapon().getDamage());
                    currentCharacter.upgradeWeapon();
                    currentCharacter.setAttack(currentCharacter.getAttack() + currentCharacter.getWeapon().getDamage());
                }
                case 2 ->{
                    switch (currentCharacter){
                        case Mage _ -> {
                            currentCharacter.setAttack(currentCharacter.getAttack() - currentCharacter.getWeapon().getDamage());
                            ((Mage) currentCharacter).enhanceGrimoire();
                            currentCharacter.setAttack(currentCharacter.getAttack() + currentCharacter.getWeapon().getDamage());
                        }
                        case Archer _ -> {
                            currentCharacter.setAttack(currentCharacter.getAttack() - currentCharacter.getWeapon().getDamage());
                            ((Archer) currentCharacter).enhanceBow();
                            currentCharacter.setAttack(currentCharacter.getAttack() + currentCharacter.getWeapon().getDamage());
                        }
                        default -> {
                            currentCharacter.setAttack(currentCharacter.getAttack() - currentCharacter.getWeapon().getDamage());
                            ((Warrior) currentCharacter).enhanceSword();
                            currentCharacter.setAttack(currentCharacter.getAttack() + currentCharacter.getWeapon().getDamage());
                        }
                    }
                }
            }
            itemService.updateItem(currentCharacter.getWeapon());
        }else {
            System.out.println("You have no weapon equipped.");
        }
    }

    public void viewInventory() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        try {
            List<Item> items = inventoryService.getInventoryForCharacter(currentCharacter.getId());

            System.out.println("\n🧳 Characters.Inventory:");
            if (items.isEmpty()) {
                System.out.println(" - empty -");
                return;
            }

            for (int i = 0; i < items.size(); i++) {
                Item currentItem = items.get(i);
                if (currentItem instanceof HealthPotion hp) {
                    System.out.println(i + ": 🧪 " + hp.getName() + " | Heals: " + hp.getHealAmount() + " ❤️ | 💰 " + hp.getValue() + " gold");
                } else if (currentItem instanceof Weapon wp) {
                    System.out.println(i + ": 🗡️ " + wp.getName() + " | Level: " + wp.getLevel() + " | Damage: " + wp.getDamage() + " | 💰 " + wp.getValue() + " gold");
                } else {
                    System.out.println(i + ": 📦 " + currentItem.getName() + " | 💰 " + currentItem.getValue() + " gold");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to load inventory.");
            e.printStackTrace();
        }
    }

    public void equipWeapon() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }

        List<Item> items = inventoryService.getInventoryForCharacter(currentCharacter.getId());
        List<Weapon> weapons = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Weapon w) {
                weapons.add(w);
            }
        }

        if (weapons.isEmpty()) {
            System.out.println("You have no weapons to equip!");
            return;
        }

        System.out.println("Available weapons:");
        for (int i = 0; i < weapons.size(); i++) {
            Weapon w = weapons.get(i);
            System.out.println(i + ": " + w.getName() + ", damage: " + w.getDamage() + ", level: " + w.getLevel() + ", value: " + w.getValue());
        }

        System.out.println("🛡️ Choose a weapon to equip (enter the index):");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();

        if (index < 0 || index >= weapons.size()) {
            System.out.println("❌ Invalid selection.");
            return;
        }

        Weapon selectedWeapon = weapons.get(index);

        if(selectedWeapon.use(currentCharacter) == 0) {
            System.out.println("✅ Equipped " + selectedWeapon.getName() + " successfully!");
        }
    }

    public void buyHealingPotion() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
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

        int cost;
        HealthPotion potion;

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

        if (currentCharacter.getGold() < cost) {
            System.out.println("💸 Not enough gold!");
            return;
        }

        currentCharacter.addGold(-cost);
        characterService.updateCharacter(currentCharacter);
        inventoryService.addInventoryEntry(currentCharacter.getId(), potion.getId());
        itemService.createItem(potion);
        System.out.println("✅ You bought a " + potion.getName() + "! It's been added to your inventory.");
    }

    public void buyWeapon() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
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

        int cost;
        Weapon weapon;

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


        if (currentCharacter.getGold() < cost) {
            System.out.println("💸 Not enough gold!");
            return;
        }

        currentCharacter.addGold(-cost);
        characterService.updateCharacter(currentCharacter);
        inventoryService.addInventoryEntry(currentCharacter.getId(), weapon.getId());
        itemService.createItem(weapon);
        System.out.println("✅ You bought a " + weapon.getName() + "! It's been added to your inventory.");
    }


    public void sellItemFromInventory() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        this.viewInventory();

        if(!currentCharacter.getInventory().getItems().isEmpty()) {
            int index = -1;

            while (index < 0 || index >= currentCharacter.getInventory().getItems().size()) {
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
            inventoryService.deleteInventoryEntry(currentCharacter.getId(), currentCharacter.getInventory().getItems().get(index).getId());
        }
    }

    public void newBattle() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        Battle battle = new Battle(currentCharacter);
        if(battle.newBattle(currentCharacter) == -1)
            characterService.deleteCharacter(currentCharacter.getId());
    }

    public void viewBattleVictories() throws SQLException {
        if(characterService.countCharacters() == 0){
            System.out.println("❌ No characters.");
            return;
        }
        currentCharacter.printBattlesWon();
    }

    public void displayBattleTypes() {
        System.out.println("🗺️ Available Battles.Battle Difficulties:\n");

         System.out.print("🔸  Super-Easy");
         System.out.println(" - Contents: 1-3 Goblins");

         System.out.print("🔸  Easy");
         System.out.println(" - Contents: 4-7 Goblins");

         System.out.print("🔸  Normal");
         System.out.println(" - Contents: 2 Orcs or 1-2 Goblins and 1 Enemies.Orc");

         System.out.print("🔸  Hard");
         System.out.println(" - Contents: 2-5 Orcs");

         System.out.print("🔸  Very-Hard");
         System.out.println(" - Contents: 3 Orcs and 1 Enemies.Dragon");

         System.out.print("🔸  Nightmare");
         System.out.println(" - Contents: 2-3 Dragons");
    }

    public void getEnemyInfo(){
        new Goblin().getInfo();
        System.out.println();
        new Orc().getInfo();
        System.out.println();
        new Dragon().getInfo();
        System.out.println();
    }

    public void showCharacterInfo() {
        System.out.println("📜 === Characters.Character Classes ===");

        // Characters.Archer
        System.out.println("\n🏹 Characters.Archer:");
        System.out.println(" - Health: 25 ❤️");
        System.out.println(" - Attack: 8 ⚔️");
        System.out.println(" - Defense: 6 🛡️");
        System.out.println(" - Energy: 30 ⚡");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Double Arrows: consumes 16 energy, doubles attack (damage = " + (8 * 2) + " before defense)");
        System.out.println("   • Gain Energy: restores 20 energy");
        System.out.println("   • Enhance Bow: +20 damage if you have a 'Rare Gem'");

        // Characters.Mage
        System.out.println("\n🧙 Characters.Mage:");
        System.out.println(" - Health: 20 ❤️");
        System.out.println(" - Attack: 10 ⚔️");
        System.out.println(" - Defense: 5 🛡️");
        System.out.println(" - Mana: 30 ✨");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Fire Spell: -10 mana, +5 damage bonus, ineffective vs Dragons");
        System.out.println("   • Poison Spell: -12 mana, applies poison (-3 HP for 2 turns), normal attack damage");
        System.out.println("   • Gain Mana: restores 20 mana");
        System.out.println("   • Enhance Grimoire: +20 damage if you have a 'Rare Gem'");

        // Characters.Warrior
        System.out.println("\n⚔️ Characters.Warrior:");
        System.out.println(" - Health: 30 ❤️");
        System.out.println(" - Attack: 5 ⚔️");
        System.out.println(" - Defense: 7 🛡️");
        System.out.println(" - Speed: 30 🌀");

        System.out.println("🔸 Abilities:");
        System.out.println("   • Use Speed: consumes 16 speed (attacks for 2 more rounds)");
        System.out.println("   • Gain Speed: restores 20 speed");
        System.out.println("   • Enhance Sword: +12 damage if you have a 'Enemies.Dragon Scale'");
    }


}
