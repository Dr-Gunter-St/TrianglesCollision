package Geometry;

public class Line {

    private double m;
    private double b;

    public Line(int m, int b){
        this.m = m;
        this.b = b;
    }

    public Line(Point a, Point b){

        if (a.getX() == b.getX()) m = 0.0f;
        else m = (float) (b.getY() - a.getY()) /(float) (b.getX() - a.getX());

        this.b = a.getY() - m*a.getX();


    }

}
