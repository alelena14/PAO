package Characters;

import Items.*;

import java.util.ArrayList;
import java.util.Comparator;

public class Inventory {
    private final ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public Inventory(ArrayList<Item> items) {
        this.items = items;
        this.items.sort(Comparator.comparing(Item::getValue));
    }

    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.getName() + " has been removed from the inventory.");
        } else {
            System.out.println("Items.Item not found in inventory.");
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        items.sort(Comparator.comparingInt(Item::getValue));
    }

}
