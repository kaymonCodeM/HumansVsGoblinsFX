package land;

public abstract class Location {
    private int x;
    private int y;
    private String symbol;

    public Location(int y, int x, String symbol) {
        this.y = y;
        this.x = x;
        this.symbol = symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
