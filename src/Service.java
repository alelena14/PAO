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

        currentCharacter.setStats(characters.get(choice));
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
                case 1 -> currentCharacter.upgradeWeapon();
                case 2 ->{
                    switch (currentCharacter){
                        case Mage _ -> ((Mage) currentCharacter).enhanceGrimoire();
                        case Archer _ -> ((Archer) currentCharacter).enhanceBow();
                        default -> ((Warrior) currentCharacter).enhanceSword();
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

        if (currentCharacter.weapon != null) {
            System.out.println("🔁 You already have a weapon equipped: " + currentCharacter.weapon.name);
            System.out.println("It will be returned to your inventory.");
            currentCharacter.inventory.addItem(currentCharacter.weapon);
        }

        selectedWeapon.use(currentCharacter);
        currentCharacter.inventory.removeItem(selectedWeapon);
        System.out.println("✅ Equipped " + selectedWeapon.name + " successfully!");
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


    void sellItemFromInventory(){
        if (characters.isEmpty()) {
            System.out.println("❌ No characters available!");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        currentCharacter.showInventory();

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

}
