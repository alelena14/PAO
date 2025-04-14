
public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(int healAmount, int value) {
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
