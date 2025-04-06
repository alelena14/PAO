
public class Weapon extends Item {
    public int damage;
    private String type;
    public int level = 1;

    public Weapon(String name, int damage, String type, int value) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.value = value;
    }

    @Override
    public void use(Character character) {
        if (character instanceof Mage && "Grimoire".equals(this.type)) {
            character.equipWeapon(this);
            System.out.println("Mage equipped magic book!");
        } else if (character instanceof Warrior && "Sword".equals(this.type)) {
            character.equipWeapon(this);
            System.out.println("Warrior equipped sword!");
        } else if (character instanceof Archer && "Bow".equals(this.type)) {
            character.equipWeapon(this);
            System.out.println("Archer equipped bow!");
        } else {
            System.out.println("Cannot equip this weapon type!");
        }
    }

    public int getDamage() {
        return damage;
    }
}
