package Geometry;

import java.util.Map;

public class Line {

    private double a;
    private double b;
    private double c;

    private Point first;
    private Point second;

    private Triangle triangle;

    public Line(Point a, Point b, Triangle triangle){

        this.a = a.getY() - b.getY();
        this.b = b.getX() - a.getX();
        this.c = a.getX()*b.getY() - b.getX()*a.getY();

        first = a;
        second = b;

        this.triangle = triangle;
    }

    public double dist(Point p){
        return Math.abs(a*p.getX() + b*p.getY() + c)/Math.sqrt(a*a + b*b);
    }

    public Point closestPoint(Point p){
        double x, y;

        x = (b*(b*p.getX() - a*p.getY()) - a*c)
                /(a*a + b*b);

        y = (a*(-b*p.getX() + a*p.getY()) - b*c)
                /(a*a + b*b);

        return new Point(x, y);
    }

}
