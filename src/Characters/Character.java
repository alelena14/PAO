package Characters;

import Database.BattlesWonService;
import Database.CharacterService;
import Items.*;
import Enemies.*;
import Battles.*;

import java.sql.SQLException;
import java.util.*;

public class Character {
    protected int id;
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

    public Character(int id, String name, int health, int attack, int defense, Weapon weapon, int level, int exp, int gold, int isBurned) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.weapon = weapon;
        this.level = level;
        this.exp = exp;
        this.gold = gold;
        this.isBurned = isBurned;
    }

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

    public void addExp(int exp) throws SQLException {
        this.exp += exp;

        if(this.exp >= 100){
            level += 1;
            this.exp = this.exp % 100;
            this.health += 10;
            this.attack += 10;
            switch (this) {
                case Mage _ -> ((Mage) this).addMana(10);
                case Warrior _ -> ((Warrior) this).addSpeed(10);
                case Archer _ -> {
                    ((Archer) this).addEnergy(10);
                    if(((Archer) this).getShootsDouble() > 0)
                        this.attack += 10;
                }
                default -> {
                }
            }
            System.out.println(name + " has now level " + level + "!");
            CharacterService.getInstance().updateCharacter(this);
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
        if(this.gold < 25){
            System.out.println("You don't have enough gold!");
        }else {
            this.gold -= 25;
            weapon.setLevel(weapon.getLevel() + 1);
            weapon.setDamage(weapon.getDamage() + 8);
            System.out.println(weapon.getName() + " is now level " + weapon.getLevel() + "!");
        }
    }

    public void attack(Enemy enemy){
        enemy.setHealth(enemy.getDefense() >= attack ? enemy.getHealth() : enemy.getHealth() - attack + enemy.getDefense());
        enemy.setHealth(Math.max(enemy.getHealth(), 0));
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
        System.out.println("#" + this.getId());
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
            System.out.println("🗡️ Weapon: " + this.weapon.getName());

    }

    public void printBattlesWon() throws SQLException {
        Map<String, Integer> battles = BattlesWonService.getInstance().getWinsForCharacter(this.getId());

        if (battles.isEmpty()) {
            System.out.println("📭 No victories found.");
            return;
        }

        List<String> difficulties = new ArrayList<>(battles.keySet());
        System.out.println("🏆 Victories:");
        for (int i = 0; i < difficulties.size(); i++) {
            String difficulty = difficulties.get(i);
            int wins = battles.get(difficulty);
            System.out.printf("%d. Difficulty: %s | Wins: %d\n", i, difficulty, wins);
        }
    }

    public void incrementBattlesWon(Battle.Difficulty difficulty) throws SQLException {
        if(BattlesWonService.getInstance().getWinsForDifficulty(this.getId(), String.valueOf(difficulty)) > 0){
            BattlesWonService.getInstance().updateWins(this.getId(), String.valueOf(difficulty), 1);
        } else {
            BattlesWonService.getInstance().addBattleWinEntry(this.getId(), String.valueOf(difficulty), 1);
        }
    }

    /////////////////// Inventory //////////////////////

    public void sellItem(Item item){
        this.gold += item.getValue();
    }

    public ArrayList<HealthPotion> showPotions() {
        ArrayList<Item> items = inventory.getItems();
        ArrayList<HealthPotion> potions = new ArrayList<>();
        if (items.isEmpty()) {
            return potions;
        }
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            if(currentItem instanceof HealthPotion hp){
                potions.add(hp);
                System.out.println(i + ") " + hp.getName() + ", Heal: " + hp.getHealAmount() + ", Value: " + hp.getValue());
            }
        }
        return potions;
    }

    public boolean hasPotions(){
        ArrayList<Item> items = inventory.getItems();
        if (items.isEmpty()) {
            return false;
        }
        for (Item item : items) {
            if (item instanceof HealthPotion) {
                return true;
            }
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getIsBurned() {
        return isBurned;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getGold() {
        return gold;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setIsBurned(int isBurned) {
        this.isBurned = isBurned;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getId() { return id;}

    public void setId(int id) { this.id = id;}
}
