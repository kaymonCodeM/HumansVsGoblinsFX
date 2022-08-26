package land;

public abstract class Location {
    private int[]position;
    private String symbol;

    public Location(int[] position, String symbol) {
        this.position = position;
        this.symbol = symbol;
    }

    public int[] getPosition() {
        return position;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
