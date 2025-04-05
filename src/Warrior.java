public class Warrior extends Character{
    int speed;

    public Warrior(){
        this.speed = 30;
        this.health = 30;
        this.attack = 5;
        this.defense = 10;
        this.weapon = null;
    }

    @Override
    public void getInfo(){
        System.out.println("Warrior: attack " + this.attack + ", health " + this.health + ", mana " + this.speed + ", weapon: " + this.weapon.getName());
    }
    // warrior va folosi speed pentru a si adauga inca doua ture la batalie
}
