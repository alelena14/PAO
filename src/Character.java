import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class Character {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected Weapon weapon;
    protected int isBurned = 0;
    protected Inventory inventory = new Inventory();
    protected int level = 1;
    protected int exp = 0;
    protected int gold = 0;
    private final Map<Battle.Difficulty, Integer> battlesWon = new EnumMap<>(Battle.Difficulty.class);

    public Character(){}

    public void setStats(Character other) {
        this.name = other.name;
        this.health = other.health;
        this.attack = other.attack;
        this.defense = other.defense;
        this.weapon = other.weapon;
        this.isBurned = other.isBurned;
        this.inventory = new Inventory(other.inventory.getItems());
        this.level = other.level;
        this.exp = other.exp;
        this.gold = other.gold;
    }

    public Character chooseCharacter() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your Character:");
        System.out.println("1)🧙 Mage");
        System.out.println("2)🏹 Archer");
        System.out.println("3)⚔️ Warrior");

        int opt = scanner.nextInt();

        System.out.print("Choose the name for your character: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        return switch (opt) {
            case 1 -> new Mage(name);
            case 2 -> new Archer(name);
            case 3 -> new Warrior(name);
            default -> {
                System.out.println("Invalid option, defaulting to Warrior.");
                yield new Warrior(name);
            }
        };
    }

    public void addGold(int gold){
        this.gold += gold;
    }

    public void addExp(int exp){
        this.exp += exp;

        if(exp >= 100){
            level += 1;
            this.exp = exp % 100;
            this.health += 10;
            this.attack += 10;
            switch (this) {
                case Mage _ -> ((Mage) this).addMana(10);
                case Warrior _ -> ((Warrior) this).addSpeed(10);
                case Archer _ -> ((Archer) this).addEnergy(10);
                default -> {
                }
            }
            System.out.println(name + " has now level " + level + "!");
        }
    }

    public void heal(int healAmount){
        this.health += healAmount;
        System.out.println("Health: " + health);
    }

    public void equipWeapon(Weapon weapon){
        this.weapon = weapon;
        this.attack += weapon.getDamage();
    }

    public void upgradeWeapon(){
        if(this.gold < 50){
            System.out.println("You don't have enough gold!");
        }else {
            this.gold -= 50;
            weapon.setLevel(weapon.getLevel() + 1);
            weapon.setDamage(weapon.getDamage() + 8);
            System.out.println(weapon.name + " is now level " + weapon.getLevel() + "!");
        }
    }

    public void attack(Enemy enemy){
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack + enemy.defense;
        enemy.health = Math.max(enemy.health, 0);
        switch (this) {
            case Mage _ -> System.out.println("Mage used Attack!");
            case Warrior _ -> System.out.println("Warrior used Attack!");
            case Archer _ -> System.out.println("Archer used Attack!");
            default -> {
            }
        }
    }

    public boolean isAlive(){
        return health > 0;
    }

    public void getInfo(){
        System.out.println("\n🧙 CHARACTER STATUS:");
        System.out.println("📛 Name: " + this.name);
        System.out.println("❤️ HP: " + this.health);
        System.out.println("🗡️ Attack: " + this.attack);
        switch (this) {
            case Mage _ -> System.out.println("💧 Mana: " + ((Mage) this).getMana());
            case Archer _ -> System.out.println("🏹 Energy: " + ((Archer) this).getEnergy());
            case Warrior _ -> System.out.println("⚡ Speed: " + ((Warrior) this).getSpeed());
            default -> {
            }
        }
        if(weapon!=null)
            System.out.println("🗡️ Weapon: " + this.weapon.name);

    }

    public void printBattlesWon() {
        for (Map.Entry<Battle.Difficulty, Integer> entry : battlesWon.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void incrementBattlesWon(Battle.Difficulty difficulty) {
        battlesWon.merge(difficulty, 1, Integer::sum);
    }

    /////////////////// Inventory //////////////////////

    public void addItemToInventory(Item item) {
        inventory.addItem(item);
        System.out.println(item.name + " added to inventory.");
    }

    public void sellItem(Item item){
        inventory.removeItem(item);
        this.gold += item.value;
        System.out.println(item.name + " has been sold! +" + item.value + " gold");
    }

    public void showInventory() {
        ArrayList<Item> items = inventory.getItems();

        System.out.println("\n🧳 Inventory:");
        if (items.isEmpty()) {
            System.out.println(" - empty -");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            if (currentItem instanceof HealthPotion hp) {
                System.out.println(i + ": 🧪 " + hp.name + " | Heals: " + hp.getHealAmount() + " ❤️ | 💰 " + hp.getValue() + " gold");
            } else if (currentItem instanceof Weapon wp) {
                System.out.println(i + ": 🗡️ " + wp.name + " | Level: " + wp.getLevel() + " | 💰 " + wp.getValue() + " gold");
            } else {
                System.out.println(i + ": 📦 " + currentItem.getName() + " | 💰 " + currentItem.getValue() + " gold");
            }
        }
    }

    public ArrayList showPotions() {
        ArrayList items = inventory.getItems();
        ArrayList potions = new ArrayList();
        if (items.isEmpty()) {
            return potions;
        }
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = (Item) items.get(i);
            if(currentItem instanceof HealthPotion hp){
                potions.add(hp);
                System.out.println(i + ": " + hp.name + ", heal amount: " + hp.getHealAmount() + ", value: " + hp.getValue());
            }
        }
        return potions;
    }



}
