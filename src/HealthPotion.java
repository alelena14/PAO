
public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(int healAmount, int value) {
        this.name = "Health Potion";
        this.healAmount = healAmount;
        this.value = value;
    }

    @Override
    public void use(Character character) {
        System.out.println("Healed for " + healAmount + " HP.");
        character.heal(this.healAmount);
    }

    public int getHealAmount() {
        return healAmount;
    }
}
