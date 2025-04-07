import java.util.ArrayList;

public class Inventory {
    private final ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void removeItem(Item item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println(item.name + " has been removed from the inventory.");
        } else {
            System.out.println("Item not found in inventory.");
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }


}
