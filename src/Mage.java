
public class Mage extends Character {
    int mana;

    public Mage(){
        this.mana = 30;
        this.health = 20;
        this.attack = 10;
        this.defense = 40;
        this.weapon = null;
    }
    public void useFireSpell(Enemy enemy){
        if(enemy instanceof Dragon){
            this.mana -= 10;
            System.out.println("The dragon is immune to fire!");
        }else {
            this.mana -= 10;
            enemy.health = enemy.defense >= 15 ? enemy.health : enemy.health - 15 +enemy.defense;
            System.out.println("Mage used Fire Spell!");
        }
    }

    public void usePoisonSpell(Enemy enemy){
        this.mana -= 12;
        enemy.health = enemy.defense >= 11 ? enemy.health : enemy.health - 11 +enemy.defense;;
        enemy.isPoisoned += 2;
        System.out.println("Mage used Poison Spell!");
    }

    public void gainMana(){
        this.mana += 20;
        System.out.println("Mage gained 20 Mana!");
    }

    @Override
    public void getInfo(){
        System.out.println("Mage: attack " + this.attack + ", health " + this.health + ", mana " + this.mana + ", weapon: " + this.weapon.getName());
    }
}
