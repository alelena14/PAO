public class Orc extends Enemy {
    int energy = 20;

    public Orc() {
        super("Orc", 45, 15, 5);
        ;
    }

    public void throwAxe(Character character){
        character.health -= 2*attack - character.defense;
        this.energy -= 11;
        System.out.println("The orc threw his axe!");
    }
}
