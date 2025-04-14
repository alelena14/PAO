

public class Item {
    protected String name;
    protected int value;

    public Item(){}

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int use(Character character){return 0;}

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
