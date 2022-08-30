package land;

public abstract class Location {
    protected int y;
    protected int x;
    protected String symbol;


    public Location(int y, int x, String symbol) {
        this.y = y;
        this.x = x;
        this.symbol = symbol;
    }

    abstract int getX();

    abstract int getY();

    abstract String getSymbol();

    abstract void setSymbol(String symbol);

    abstract void setX(int x);

    abstract void setY(int y);

}
