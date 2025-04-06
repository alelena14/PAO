import java.util.Scanner;

public class Character {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected String attackType;
    protected Weapon weapon;
    protected int isBurned = 0;
    public Inventory inventory = new Inventory();
    int level = 1;
    int exp;
    int gold = 0;

    public Character(){}

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
        inventory.items.add(item);
        System.out.println(item.name + " added to inventory.");
    }

    public void showInventory() {
        System.out.println("Inventory:");
        if (inventory.items.isEmpty()) {
            System.out.println(" - empty -");
        }
        for (int i = 0; i < inventory.items.size(); i++) {
            if(inventory.items.get(i) instanceof HealthPotion){
                HealthPotion hp = (HealthPotion) inventory.items.get(i);
                System.out.println(i + ": " + hp.name + ", heal amount: " + hp.getHealAmount());
            }else
                System.out.println(i + ": " + inventory.items.get(i).name);
        }
    }

}
