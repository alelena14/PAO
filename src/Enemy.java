import java.security.PublicKey;

public abstract class Enemy {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected int isPoisoned = 0;

    public Enemy(){}

    public Enemy(String name, int health, int attack, int defense) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public String getName() {
        return name;
    }

    public void status() {
        System.out.println(name + " | HP: " + health + ", ATK: " + attack + ", DEF: " + defense +  " Poisoned for " + isPoisoned + " rounds");
    }

    public void attack(Character character){
        character.health = character.defense >= attack ? character.health : character.health - attack +character.defense;
        System.out.println(name + " attacked!");
    }

    public void isDefeated(){}
}
