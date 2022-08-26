package land;

public class Land extends Location {

    public Land(int[] position){
        super(position,"*");
    }
    public Land(int[] position,String symbol) {
        super(position, symbol);
    }

    @Override
    public String toString() {
        String result = "";
        result += "Symbol: " + this.getSymbol() + "\n";
        result += "Position: " + "x = " + this.getPosition()[1] + ", " + "y = " + this.getPosition()[0] + "\n";
        return result;
    }
}
