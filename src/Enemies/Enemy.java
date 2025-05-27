package Enemies;

import Characters.Character;

import java.util.ArrayList;

public abstract class Enemy {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected int isPoisoned = 0;

    public Enemy(String name, int health, int attack, int defense) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void attack(Character character){
        character.setHealth(character.getDefense() >= attack ? character.getHealth() : character.getHealth() - attack + character.getDefense());
        character.setHealth(Math.max(character.getHealth(), 0));
        System.out.println(name + " attacked!");
    }

    public ArrayList<Object> drops(){return null;}

    public void getInfo(){
        System.out.println("\n👹 ENEMY:");
        System.out.println("📛 " + this.getClass().getSimpleName());
        System.out.println("❤️ HP: " + this.health);
        System.out.println("🗡️ Attack: " + this.attack);

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

    public int getIsPoisoned() {
        return isPoisoned;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setIsPoisoned(int isPoisoned) {
        this.isPoisoned = isPoisoned;
    }
}
