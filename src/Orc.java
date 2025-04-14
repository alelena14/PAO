import java.util.ArrayList;

public class Orc extends Enemy {
    private int energy = 20;

    public Orc() {
        super("Orc", 45, 15, 5);
    }

    public void throwAxe(Character character){
        character.health = character.defense >= 2 * attack ? character.health : character.health - 2 * attack + character.defense;
        character.health = Math.max(character.health, 0);
        this.energy -= 11;
        System.out.println("💥 The orc threw his axe!");
    }

    @Override
    public ArrayList<Object> drops(){
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(40); // gold
        drops.add(50); // exp
        drops.add(new Weapon("Goblin's Axe", 15, "Sword", 30));
        if(Math.random() < 0.25)
            drops.add(new Item("Orc Eye", 20));

        return drops;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

}
