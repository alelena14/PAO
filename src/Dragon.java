public class Dragon extends Enemy {
    public Dragon() {
        super("Dragon", 100, 30, 15);
    }

    public void breatheFire(Character character) {
        character.health = character.defense >= 40 ? character.health : character.health - 40 + character.defense;
        character.isBurned += 1;
        System.out.println("Dragon breathes fire!");
    }
}
