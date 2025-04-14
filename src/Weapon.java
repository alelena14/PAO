
public class Weapon extends Item {
    private int damage;
    private final String type;
    private int level = 1;

    public Weapon(String name, int damage, String type, int value) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.value = value;
    }

    @Override
    public int use(Character character) {
        switch (character) {
            case Mage _ when "Grimoire".equals(this.type) -> {
                if(character.weapon != null){
                    character.inventory.addItem(character.weapon);
                    System.out.println("🔁 You already have a weapon equipped: " + character.weapon.name);
                    System.out.println("It will be returned to your inventory.");
                    character.attack -= character.weapon.getDamage();
                }
                character.equipWeapon(this);
                return 0;
            }
            case Warrior _ when "Sword".equals(this.type) -> {
                if(character.weapon != null){
                    character.inventory.addItem(character.weapon);
                    System.out.println("🔁 You already have a weapon equipped: " + character.weapon.name);
                    System.out.println("It will be returned to your inventory.");
                    character.attack -= character.weapon.getDamage();
                }
                character.equipWeapon(this);
                return 0;
            }
            case Archer _ when "Bow".equals(this.type) -> {
                if(character.weapon != null){
                    character.inventory.addItem(character.weapon);
                    System.out.println("🔁 You already have a weapon equipped: " + character.weapon.name);
                    System.out.println("It will be returned to your inventory.");
                    character.attack -= character.weapon.getDamage();
                }
                character.equipWeapon(this);
                return 0;
            }
            default -> {
                System.out.println("❌ Cannot equip this weapon type!");
                return -1;
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
