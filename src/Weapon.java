
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
    public void use(Character character) {
        switch (character) {
            case Mage _ when "Grimoire".equals(this.type) -> {
                character.equipWeapon(this);
                System.out.println("Mage equipped magic book!");
            }
            case Warrior _ when "Sword".equals(this.type) -> {
                character.equipWeapon(this);
                System.out.println("Warrior equipped sword!");
            }
            case Archer _ when "Bow".equals(this.type) -> {
                character.equipWeapon(this);
                System.out.println("Archer equipped bow!");
            }
            case null, default -> System.out.println("Cannot equip this weapon type!");
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
