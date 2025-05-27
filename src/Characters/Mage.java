package Characters;

import Enemies.*;
import Items.*;

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

    public Mage(int id, String name, int health, int attack, int defense, Weapon weapon,
                int level, int exp, int gold, int isBurned, int mana) {
        super(id, name, health, attack, defense, weapon, level, exp, gold, isBurned);
        this.mana = mana;
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
            enemy.setHealth(enemy.getDefense() >= attack + 5 ? enemy.getHealth() : enemy.getHealth() - (attack + 5) + enemy.getDefense());
            enemy.setHealth(Math.max(enemy.getHealth(), 0));
            System.out.println("Characters.Mage used Fire Spell!");
        }
    }

    public void usePoisonSpell(Enemy enemy){
        this.mana -= 12;
        enemy.setHealth(enemy.getDefense() >= attack ? enemy.getHealth() : enemy.getHealth() - attack + enemy.getDefense());
        enemy.setHealth(Math.max(enemy.getHealth(), 0));
        enemy.setIsPoisoned(enemy.getIsPoisoned() + 2);
        System.out.println("Characters.Mage used Poison Spell!");
    }


    public void gainMana(){
        this.mana += 20;
        System.out.println("Characters.Mage has gained 20 Mana!");
    }

    public void enhanceGrimoire(){
        boolean found = false;
        for (Item item : inventory.getItems()) {
            if (item.getName().equals("Rare Gem")) {
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
