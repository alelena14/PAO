package Enemies;

import Database.ItemService;
import Items.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 30, 8, 2);
    }

    @Override
    public ArrayList<Object> drops() throws SQLException {
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(10); // gold
        drops.add(15);
        Weapon w = new Weapon("rusty dagger", 3, "Sword", 2);
        drops.add(w);
        ItemService.getInstance().createItem(w);
        if(Math.random() < 0.01) {
            Item i = new Item("Rare Gem", 400);
            drops.add(i);
            ItemService.getInstance().createItem(i);
        }
        return drops;
    }
}
