public class Warrior extends Character{
    private int speed;

    public Warrior(String name){
        this.name = name;
        this.speed = 30;
        this.health = 30;
        this.attack = 5;
        this.defense = 10;
        this.weapon = null;
    }

    @Override
    public void getInfo(){
        super.getInfo();
        System.out.println(", speed " + this.speed);
    }

    public int getSpeed() {
        return speed;
    }

    public void throwAxe(Enemy enemy){
        enemy.health = enemy.defense >= attack + 5 ? enemy.health : enemy.health - attack + 5 + enemy.defense;
        this.speed -= 11;
        System.out.println("Warrior threw his axe!");
    }

    public void useSpeed(){
        this.speed -= 16;
    }

    public void gainSpeed(){
        this.speed += 20;
        System.out.println("Warrior gained 20 Speed!");
    }

    public void enhanceSword(){
        for (Item item : inventory.getItems()) {
            if (item.name.equals("Dragon Scale")) {
                weapon.damage += 12;
                inventory.removeItem(item);
                break;
            }
        }
    }

}
