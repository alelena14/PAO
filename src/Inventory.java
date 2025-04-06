import java.util.ArrayList;

public class Inventory {
    public ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.name + " was removed from inventory.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }


}
