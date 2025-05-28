package Enemies;

import Characters.Character;
import Database.ItemService;
import Items.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Orc extends Enemy {
    private int energy = 20;

    public Orc() {
        super("Orc", 45, 15, 5);
    }

    public void throwAxe(Character character){
        character.setHealth(character.getDefense() >= 2 * attack ? character.getHealth() : character.getHealth() - 2 * attack + character.getDefense());
        character.setHealth(Math.max(character.getHealth(), 0));
        this.energy -= 11;
        System.out.println("💥 The orc threw his axe!");
    }

    @Override
    public ArrayList<Object> drops() throws SQLException {
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(40); // gold
        drops.add(50); // exp
        Weapon w = new Weapon("Goblin's Axe", 15, "Sword", 30);
        drops.add(w);
        ItemService.getInstance().createItem(w);
        if(Math.random() < 0.25) {
            Item i = new Item("Orc Eye", 20);
            drops.add(i);
            ItemService.getInstance().createItem(i);
        }

        return drops;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
