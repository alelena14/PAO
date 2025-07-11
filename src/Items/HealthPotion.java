package Items;

import Characters.Character;

public class HealthPotion extends Item {
    private final int healAmount;

    public HealthPotion(int healAmount, int value) {
        this.name = "Health Potion";
        this.healAmount = healAmount;
        this.value = value;
    }

    public HealthPotion(int id, int healAmount, int value) {
        this.id = id;
        this.name = "Health Potion";
        this.healAmount = healAmount;
        this.value = value;
    }

    @Override
    public int use(Character character) {
        System.out.println("Healed for " + healAmount + " HP.");
        character.heal(this.healAmount);
        return 0;
    }

    public int getHealAmount() {
        return healAmount;
    }
}
