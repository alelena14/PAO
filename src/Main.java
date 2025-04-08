public class Main {
    public static void main(String[] args) {

        Character character = new Character().chooseCharacter();
        Weapon book = new Weapon("book", 17, "Grimoire", 10);
        Weapon sword = new Weapon("sword", 20, "Sword", 10);
        Weapon bow = new Weapon("bow", 15, "Bow", 10);
        Item hp = new HealthPotion(15, 10);
        Item item = new Item("Gem", 20);

        character.getInfo();
        character.addItemToInventory(hp);
        character.addItemToInventory(hp);
        character.addItemToInventory(item);

        book.use(character);

        character.getInfo();

        Battle battle = new Battle(character);

        character.getInfo();
        character.showInventory();

    }
}