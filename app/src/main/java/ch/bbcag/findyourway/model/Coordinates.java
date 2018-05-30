package ch.bbcag.findyourway.model;

public class Coordinates {
    private int Type;
    private double X;
    private double Y;

    public Coordinates(double x, double y){
        setX(x);
        setY(y);
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
