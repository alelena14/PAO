import java.util.ArrayList;

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

    public void attack(Character character){
        character.health = character.defense >= attack ? character.health : character.health - attack +character.defense;
        character.health = Math.max(character.health, 0);
        System.out.println(name + " attacked!");
    }

    public ArrayList drops(){return null;}

    public void getInfo(){
        System.out.println("\n👹 ENEMY:");
        System.out.println("📛 " + this.getClass().getSimpleName());
        System.out.println("❤️ HP: " + this.health);
        System.out.println("🗡️ Attack: " + this.attack);

    }

}
