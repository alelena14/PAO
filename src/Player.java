import java.util.Scanner;

public class Player {
    int level;
    int exp;
    int gold;
    Character character;
    Inventory inventory;

    public Player(){
        this.level = 1;
        this.exp = 0;

        System.out.println("Choose your Character:");
        System.out.println("1)Mage");
        System.out.println("2)Archer");
        System.out.println("3)Warrior");

        Scanner scanner = new Scanner(System.in);
        int opt = scanner.nextInt();

        switch (opt) {
            case 1: character = new Mage(); break;
            case 2: character = new Archer(); break;
            case 3: character = new Warrior(); break;
        }

        inventory = new Inventory();
    }

    public void addItemToInventory(Item item) {
        inventory.items.add(item);
        System.out.println(item.getName() + " added to inventory.");
    }

    public void showInventory() {
        System.out.println("Inventory:");
        if (inventory.items.isEmpty()) {
            System.out.println(" - empty -");
        }
        for (int i = 0; i < inventory.items.size(); i++) {
            if(inventory.items.get(i) instanceof HealthPotion){
                HealthPotion hp = (HealthPotion) inventory.items.get(i);
                System.out.println(i + ": " + hp.name + ", heal amount: " + hp.getHealAmount());
            }else
                System.out.println(i + ": " + inventory.items.get(i).getName());
        }
    }
}
