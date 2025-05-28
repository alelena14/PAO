package Enemies;

import Characters.Character;
import Database.ItemService;
import Items.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Dragon extends Enemy {
    private int energy = 35;

    public Dragon() {
        super("Dragon", 100, 30, 15);
    }

    public void breatheFire(Character character) {
        character.setHealth(character.getDefense() >= 40 ? character.getHealth() : character.getHealth() - 40 + character.getDefense());
        character.setHealth(Math.max(character.getHealth(), 0));
        this.energy -= 18;
        character.setIsBurned(character.getIsBurned() + 1);
        System.out.println("🔥 Dragon breathes fire!");
    }

    @Override
    public ArrayList<Object> drops() throws SQLException {
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(100); // gold
        drops.add(120); // exp
        Weapon w = new Weapon("Five-Leaf Grimoire", 35, "Grimoire", 100);
        drops.add(w);
        ItemService.getInstance().createItem(w);
        if (Math.random() < 0.15) {
            Item i = new Item("Rare Gem", 400);
            drops.add(i);
            ItemService.getInstance().createItem(i);
        }
        if(Math.random() < 0.30) {
            Item i = new Item("Dragon Scale", 70);
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
