import java.util.ArrayList;

public class Goblin extends Enemy {
    public Goblin() {
        super("Goblin", 30, 8, 2);
    }

    @Override
    public ArrayList<Object> drops(){
        ArrayList<Object> drops = new ArrayList<>();
        drops.add(10); // gold
        drops.add(15); // exp
        drops.add(new Weapon("rusty dagger", 5, "Sword", 2));
        if(Math.random() < 0.01)
            drops.add(new Item("Rare Gem",400));

        return drops;
    }
}
