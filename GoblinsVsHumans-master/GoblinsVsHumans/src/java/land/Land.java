package land;

public class Land extends Location {

    public Land(int x, int y, String symbol) {
        super(x, y, symbol);
    }
    public Land(int x, int y) {
        super(x, y, "*");
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "x = " + this.getY() + ", " + "y = " + this.getX() + "\n";
        return result;
    }
}
