public class Archer extends Character{
    private int energy;
    private int shootsDouble = 0;

    public Archer(String name){
        this.name = name;
        this.energy = 30;
        this.health = 25;
        this.attack = 8;
        this.defense = 7;
        this.weapon = null;
    }

    @Override
    public void getInfo(){
        super.getInfo();
        System.out.println(", energy " + this.energy);
    }

    public int getEnergy() {
        return energy;
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
        shootsDouble = 2;
        enemy.health = enemy.defense >= attack ? enemy.health : enemy.health - attack + enemy.defense;
        System.out.println("Archer is shooting Double Arrows!");
    }

    public void gainEnergy(){
        this.energy += 20;
        System.out.println("Archer gained 20 Energy!");
    }

    public void enhanceBow(){
        for (Item item : inventory.getItems()) {
            if (item.name.equals("Rare Gem")) {
                weapon.damage += 20;
                inventory.removeItem(item);
                break;
            }
        }
    }
}
