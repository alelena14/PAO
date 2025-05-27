package Items;

import Characters.Archer;
import Characters.Character;
import Characters.Mage;
import Characters.Warrior;
import Database.CharacterService;
import Database.InventoryService;

import java.sql.SQLException;

public class Weapon extends Item {
    private int damage;
    private final String type;
    private int level = 1;

    public Weapon(int id, String name, int damage, String type, int value, int level) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.level = level;
        this.value = value;
    }

    public Weapon(String name, int damage, String type, int value) {
        this.name = name;
        this.damage = damage;
        this.type = type;
        this.value = value;
    }

    @Override
    public int use(Character character) throws SQLException {
        InventoryService inventoryService = InventoryService.getInstance();
        CharacterService characterService = CharacterService.getInstance();
        switch (character) {
            case Mage _ when "Grimoire".equals(this.type) -> {
                if(character.getWeapon() != null){
                    // this = noua arma care se afla in mom asta in inventar
                    // ii dau update si in locul ei o pun pe cea pe care o am equipped
                    inventoryService.updateInventoryEntry(character.getId(), this.getId(), character.getWeapon().getId());
                    System.out.println("🔁 You already have a weapon equipped: " + character.getWeapon().name);
                    System.out.println("It will be returned to your inventory.");
                    character.setAttack(character.getAttack() - character.getWeapon().getDamage());
                }
                // dau equip la noua arma
                character.equipWeapon(this);
                characterService.updateCharacter(character);
                return 0;
            }
            case Warrior _ when "Sword".equals(this.type) -> {
                if(character.getWeapon() != null){
                    inventoryService.updateInventoryEntry(character.getId(), this.getId(), character.getWeapon().getId());
                    System.out.println("🔁 You already have a weapon equipped: " + character.getWeapon().name);
                    System.out.println("It will be returned to your inventory.");
                    character.setAttack(character.getAttack() - character.getWeapon().getDamage());
                }
                character.equipWeapon(this);
                characterService.updateCharacter(character);
                return 0;
            }
            case Archer _ when "Bow".equals(this.type) -> {
                if(character.getWeapon() != null){
                    inventoryService.updateInventoryEntry(character.getId(), this.getId(), character.getWeapon().getId());
                    System.out.println("🔁 You already have a weapon equipped: " + character.getWeapon().name);
                    System.out.println("It will be returned to your inventory.");
                    character.setAttack(character.getAttack() - character.getWeapon().getDamage());
                }
                character.equipWeapon(this);
                characterService.updateCharacter(character);
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

    public String getType() {
        return type;
    }
}
