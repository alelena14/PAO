package Items;

import Characters.Character;
import Database.ItemService;

import java.sql.SQLException;

public class Item {
    protected int id;
    protected String name;
    protected int value;

    public Item(){}

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Item(int id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int use(Character character) throws SQLException {return 0;}

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
