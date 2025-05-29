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

    public ArrayList<Item> getItems() {
        return items;
    }

}
