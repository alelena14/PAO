package Enemies;

import Characters.Character;
import Items.*;

import java.util.ArrayList;

public class Dragon extends Enemy {
    private int energy = 35;

    public Dragon() {
        super("Enemies.Dragon", 100, 30, 15);
    }

    public void breatheFire(Character character) {
        character.setHealth(character.getDefense() >= 40 ? character.getHealth() : character.getHealth() - 40 + character.getDefense());
        character.setHealth(Math.max(character.getHealth(), 0));
        this.energy -= 18;
        character.setIsBurned(character.getIsBurned() + 1);
        System.out.println("🔥 Enemies.Dragon breathes fire!");
    }

    @Override
    public ArrayList<Object> drops(){
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(100); // gold
        drops.add(120); // exp
        drops.add(new Weapon("Five-Leaf Grimoire", 35, "Grimoire", 100));
        if (Math.random() < 0.15)
            drops.add(new Item("Rare Gem",400));
        if(Math.random() < 0.30)
            drops.add(new Item("Enemies.Dragon Scale", 70));

        return drops;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
