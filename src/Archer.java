public class Archer extends Character{
    int energy;

    public Archer(){
        this.energy = 30;
        this.health = 25;
        this.attack = 8;
        this.defense = 7;
        this.weapon = null;
    }

    public void doubleArrows(){
        this.energy -= 16;
        this.attack *= 2;
        System.out.println("Archer is shooting Double Arrows!");
    }

    public void gainEnergy(){
        this.energy += 20;
        System.out.println("Archer gained 20 Energy!");
    }

    @Override
    public void getInfo(){
        System.out.println("Archer: attack " + this.attack + ", health " + this.health + ", mana " + this.energy + ", weapon: " + this.weapon.getName());
    }
}
