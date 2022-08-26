package items;



public class Equipment {
    public enum Role{ATTACK,DEFEND}
    public enum Type{HELMET,CHEST,GLOVES,BOOTS,SHIELD,SWORD,AXE,HAMMER}
    private String description;
    private Role role;
    private Type type;
    private int health;
    private int strength;

    public Equipment(String description, Role role, Type type, int health, int strength) {
        this.description = description;
        this.role = role;
        this.type = type;
        this.health = health;
        this.strength = strength;
    }


    public Role getRole() {
        return role;
    }

    public Type getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }


    @Override
    public String toString() {
        String result ="";
        result += "Description: " + this.description + "\n";
        result += "|Role: " + this.getRole() + "| ";
        result += "|Type: " + this.getType() + "| ";
        result += "|Health: " + this.getHealth() + "| ";
        result += "|Strength: " + this.getStrength() + "| ";
        return result;
    }
}
