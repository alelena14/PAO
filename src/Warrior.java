public class Warrior extends Character{
    private int speed;

    public Warrior(String name){
        this.name = name;
        this.speed = 30;
        this.health = 30;
        this.attack = 5;
        this.defense = 7;
        this.weapon = null;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void addSpeed(int speed){
        this.speed += speed;
    }

    public void useSpeed(){
        this.speed -= 16;
    }

    public void gainSpeed(){
        this.speed += 20;
        System.out.println("Warrior has gained 20 Speed!");
    }

    public void enhanceSword(){
        boolean found = false;
        for (Item item : inventory.getItems()) {
            if (item.name.equals("Dragon Scale")) {
                found = true;
                System.out.println("Sword enhanced with Dragon Scale!");
                weapon.setDamage(weapon.getDamage() + 12);
                inventory.removeItem(item);
                break;
            }
        }
        if(!found)
            System.out.println("You do not have a Dragon Scale!");
    }

}
