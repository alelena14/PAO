
public class Mage extends Character {
    private int mana;

    public Mage(String name){
        this.name = name;
        this.mana = 30;
        this.health = 20;
        this.attack = 10;
        this.defense = 5;
        this.weapon = null;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void addMana(int mana){
        this.mana += mana;
    }

    public void useFireSpell(Enemy enemy){
        if(enemy instanceof Dragon){
            this.mana -= 10;
            System.out.println("The dragon is immune to fire!");
        }else {
            this.mana -= 10;
            enemy.health = enemy.defense >= attack + 5 ? enemy.health : enemy.health - (attack + 5) + enemy.defense;
            enemy.health = Math.max(enemy.health, 0);
            System.out.println("Mage used Fire Spell!");
        }
    }

    public void usePoisonSpell(Enemy enemy){
        this.mana -= 12;
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack + enemy.defense;
        enemy.health = Math.max(enemy.health, 0);
        enemy.isPoisoned += 2;
        System.out.println("Mage used Poison Spell!");
    }


    public void gainMana(){
        this.mana += 20;
        System.out.println("Mage has gained 20 Mana!");
    }

    public void enhanceGrimoire(){
        boolean found = false;
        for (Item item : inventory.getItems()) {
            if (item.name.equals("Rare Gem")) {
                found = true;
                System.out.println("Book enhanced with Rare Gem!");
                weapon.setDamage(weapon.getDamage() + 20);
                inventory.removeItem(item);
                break;
            }
        }
        if(!found)
            System.out.println("You do not have a Rare Gem!");
    }
}
