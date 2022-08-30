package land;

public class Land extends Location {
    public Land(int y, int x, String symbol) {
        super(y, x, symbol);
    }

    public Land(int y, int x) {
        super(y, x, "*");
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public String getSymbol() {
        return this.symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y){
        this.y = y;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "x = " + this.getY() + ", " + "y = " + this.getX() + "\n";
        return result;
    }
}
