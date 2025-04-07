import java.util.ArrayList;

public class Dragon extends Enemy {
    private int energy = 35;

    public Dragon() {
        super("Dragon", 100, 30, 15);
    }

    public void breatheFire(Character character) {
        character.health = character.defense >= 40 ? character.health : character.health - 40 + character.defense;
        character.health = Math.max(character.health, 0);
        this.energy -= 18;
        character.isBurned += 1;
        System.out.println("Dragon breathes fire!");
    }

    @Override
    public ArrayList<Object> drops(){
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(100); // gold
        drops.add(120); // exp
        drops.add(new Weapon("Five-Leaf Grimoire", 35, "Grimoire", 100));
        if (Math.random() < 0.15)
            drops.add(new Item("Rare Gem",400));
        if(Math.random() < 0.30)
            drops.add(new Item("Dragon Scale", 70));

        return drops;
    }

    public int getEnergy() {
        return energy;
    }
}
