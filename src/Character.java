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
    // private Map<Battle.Difficulty, Integer> battlesWon = new EnumMap<>(Battle.Difficulty.class);

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
        System.out.println("1) Mage");
        System.out.println("2) Archer");
        System.out.println("3) Warrior");

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
        System.out.println("Balance: " + gold);
    }

    public void addExp(int exp){
        this.exp += exp;

        if(exp >= 100){
            level += 1;
            exp = exp % 100;
            System.out.println(name + " has now level " + level + "!");
        }
        System.out.println("Exp: " + exp);
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
            weapon.level += 1;
            weapon.damage += 8;
            System.out.println(weapon.name + " is now level " + weapon.level + "!");
        }
    }

    public void attack(Enemy enemy){
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack + enemy.defense;
        enemy.health = Math.max(enemy.health, 0);
        if (this instanceof Mage) {
            System.out.println("Mage used Attack!");
        } else if (this instanceof Warrior) {
            System.out.println("Warrior used Attack!");
        } else if (this instanceof Archer) {
            System.out.println("Archer used Attack!");
        }
    }

    public boolean isAlive(){
        return health > 0;
    }

    public void getInfo(){
        System.out.print(this.name + ": attack " + this.attack + ", health " + this.health);
        if(weapon!=null)
            System.out.print(", weapon: " + this.weapon.name);
    }

    /////////////////// Inventory //////////////////////

    public void addItemToInventory(Item item) {
        inventory.addItem(item);
        System.out.println(item.name + " added to inventory.");
    }

    public void showInventory() {
        System.out.println("Inventory:");
        ArrayList items = inventory.getItems();
        if (items.isEmpty()) {
            System.out.println(" - empty -");
        }
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = (Item) items.get(i);
            if(currentItem instanceof HealthPotion hp){
                System.out.println(i + ": " + hp.name + ", heal amount: " + hp.getHealAmount() + ", value: " + hp.getValue());
            }else
                System.out.println(i + ": " + currentItem.getName() + ", value: " + currentItem.getValue());
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
