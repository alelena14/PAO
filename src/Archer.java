public class Archer extends Character{
    private int energy;
    private int shootsDouble = -10;

    public Archer(String name){
        this.name = name;
        this.energy = 30;
        this.health = 25;
        this.attack = 8;
        this.defense = 6;
        this.weapon = null;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    public void addEnergy(int energy){
        this.energy += energy;
    }

    public int getShootsDouble() {
        return shootsDouble;
    }

    public void setShootsDouble(int shootsDouble) {
        this.shootsDouble = shootsDouble;
    }

    public void doubleArrows(Enemy enemy){
        this.energy -= 16;
        this.attack *= 2;
        this.shootsDouble = 1;
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack + enemy.defense;
        System.out.println("Archer is shooting Double Arrows!");
    }

    public void gainEnergy(){
        this.energy += 20;
        System.out.println("Archer has gained 20 Energy!");
    }

    public void enhanceBow(){
        boolean found = false;
        for (Item item : inventory.getItems()) {
            if (item.name.equals("Rare Gem")) {
                found = true;
                System.out.println("Bow enhanced with Rare Gem!");
                weapon.setDamage(weapon.getDamage() + 20);
                inventory.removeItem(item);
                break;
            }
        }
        if(!found)
            System.out.println("You do not have a Rare Gem!");
    }
}
