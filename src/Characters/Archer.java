package Characters;

import Database.InventoryService;
import Items.*;
import Enemies.*;

import java.sql.SQLException;
import java.util.List;

public class Archer extends Character {
    private int energy;
    private int shootsDouble = -10;

    public Archer(String name){
        this.name = name;
        this.energy = 30;
        this.health = 25;
        this.attack = 8;
        this.defense = 6;
        this.weapon = null;
    }

    public Archer(int id, String name, int health, int attack, int defense, Weapon weapon,
                  int level, int exp, int gold, int isBurned, int energy) {
        super(id, name, health, attack, defense, weapon, level, exp, gold, isBurned);
        this.energy = energy;
    }


    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy){
        this.energy += energy;
    }

    public int getShootsDouble() {
        return shootsDouble;
    }

    public void setShootsDouble(int shootsDouble) {
        this.shootsDouble = shootsDouble;
    }

    public void doubleArrows(Enemy enemy){
        this.energy -= 16;
        this.attack *= 2;
        this.shootsDouble = 1;
        enemy.setHealth(enemy.getDefense() >= attack ? enemy.getHealth() : enemy.getHealth() - attack + enemy.getDefense());
        System.out.println("Archer is shooting Double Arrows!");
    }

    public void gainEnergy(){
        this.energy += 20;
        System.out.println("Archer has gained 20 Energy!");
    }

    public void enhanceBow() throws SQLException {
        boolean found = false;
        for (Item item : InventoryService.getInstance().getInventoryForCharacter(this.id)) {
            if (item.getName().equals("Rare Gem")) {
                found = true;
                System.out.println("Bow enhanced with Rare Gem!");
                weapon.setDamage(weapon.getDamage() + 20);
                inventory.removeItem(item);
                break;
            }
        }
        if(!found)
            System.out.println("You do not have a Rare Gem!");
    }
}
