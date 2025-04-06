
public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(int healAmount, int value) {
        this.name = "Health Potion";
        this.healAmount = healAmount;
        this.value = value;
    }

    @Override
    public void use(Character character) {
        character.heal(this.healAmount);
        System.out.println("Healed for " + healAmount + " HP.");
    }

    public int getHealAmount() {
        return healAmount;
    }
}
