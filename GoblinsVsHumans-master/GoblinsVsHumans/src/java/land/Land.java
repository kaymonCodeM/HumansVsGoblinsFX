package land;

public class Land {

    private int y;
    private int x;
    private String symbol;
    public Land(int y, int x, String symbol) {
        this.y=y;
        this.x=x;
        this.symbol = symbol;
    }

    public Land(int y, int x) {
        this.y = y;
        this.x = x;
        this.symbol = "*";
    }


    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "x = " + this.getY() + ", " + "y = " + this.getX() + "\n";
        return result;
    }
}
