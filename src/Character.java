
public class Character {
    protected int health;
    protected int attack;
    protected int defense;
    protected String attackType;
    protected int goldAmmount;
    protected Weapon weapon;
    protected int isBurned = 0;

    public void addGold(int gold){
        this.goldAmmount += gold;
    }

    public void heal(int healAmount){
        this.health += healAmount;
    }

    public void equipWeapon(Weapon weapon){
        this.weapon = weapon;
        this.attack += weapon.getDamage();
    }

    public void getInfo(){};

    public void attack(Enemy enemy){
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack +enemy.defense;
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
}
