public class Main {
    public static void main(String[] args) {
//        Mage player = new Mage();
//
//        Weapon book2 = new Weapon("okish magic book", 10,"Grimoire");
//        player.equipWeapon(book2);
//        player.getInfo();
//
//        Dragon goblin = new Dragon();
//        goblin.status();
//
//        player.usePoisonSpell(goblin);
//        goblin.status();
//
//        goblin.breatheFire(player);
//        player.getInfo();

//        Player player = new Player();
//        Weapon book = new Weapon("book", 17, "Grimoire");
//        Weapon sword = new Weapon("sword", 20, "Sword");
//        Weapon bow = new Weapon("bow", 15, "Bow");
//        Item hp = new HealthPotion(15);
//        Item hp2 = new HealthPotion(5);
//
//        player.addItemToInventory(book);
//        player.addItemToInventory(hp);
//        player.addItemToInventory(sword);
//        player.addItemToInventory(hp2);
//        player.showInventory();
//        player.inventory.removeItem(hp);
//        player.showInventory();
//        player.character.getInfo();

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

        Enemy enemy = new Dragon();

        Round round = new Round(character, enemy);
        round.newRound();
        character.getInfo();
        enemy.getInfo();
        character.showInventory();
    }
}