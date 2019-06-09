package Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Triangle {

    private static final double pi = 3.1415d;

    private Point a;
    private Point b;
    private Point c;

    private Point collisionPoint;
    private Line collisionSide;

    private int mass;
    private double rotation; // rad per second

    private Point direction;

    private Color color;

    private double velocity;
    private double momentum;
    private double kEn;
    private double momentOfInertia; // for Equilateral triangles equals mass * side_length^2 / 12
    private double rotationalMomentum;
    private double rotKEn;

    //public Triangle(Point a, Point b, Point c, int mass, int velocity, int rotation, Vector direction)


    @Deprecated
    public Triangle(Point a, Point b, Point c, int mass, int rotation, Point direction) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.mass = mass;
        this.rotation = rotation;
        this.direction = direction;
    }

    @Deprecated
    public Triangle(Point a, Point b, Point c){
        this.a = a;
        this.b = b;
        this.c = c;

        mass = 0;
        rotation = 0;
        direction = new Point(0, 0);
    }

    @Deprecated
    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3){
        this.a = new Point((double) x1, (double) y1);
        this.b = new Point((double) x2, (double) y2);
        this.c = new Point((double) x3, (double) y3);

        mass = 0;
        rotation = 0;
        direction = new Point(0, 0);
    }

    public Triangle(Point centroid, double radius, double angle){
        a = new Point(centroid.getX(), centroid.getY() - radius);

        a = rotatePoint(a, centroid, angle);
        b = rotatePoint(a, centroid, 2*pi/3.0);
        c = rotatePoint(b, centroid, 2*pi/3.0);

        mass = 0;
        rotation = 0;
        direction = new Point(0, 0);
    }

    public void collide(Triangle triangle){
        //TODO: apply impulse-based collision response model

        this.findCollisionPoint(triangle);
        this.setSecondCollisionPoint(triangle);

    }

    //Super stupid
    //Little better - finds side and point respectively
    private void findCollisionPoint(Triangle triangle){
        double dist = Double.MAX_VALUE;
        double d;

        for (Point p: triangle.getPoints()) {
            for (Line side: getSides()) {
                d = side.dist(p);

                if (d < dist){
                    dist = d;
                    this.collisionSide = side;
                    this.collisionPoint = null;
                    triangle.collisionPoint = p;
                    triangle.collisionSide = null;
                }

            }
        }

        for (Point p: this.getPoints()){
            for (Line side: triangle.getSides()) {
                d = side.dist(p);

                if (d < dist){
                    dist = d;
                    triangle.collisionSide = side;
                    triangle.collisionPoint = null;
                    this.collisionPoint = p;
                    this.collisionSide = null;
                }
            }
        }



    }

    //sets collision point on the collision side of the respective triangle
    private void setSecondCollisionPoint(Triangle triangle){
        if (this.collisionPoint == null){
            this.collisionPoint = this.collisionSide.closestPoint(triangle.collisionPoint);
        } else {
            triangle.collisionPoint = triangle.collisionSide.closestPoint(this.collisionPoint);
        }
    }

    public void tick(double fraction){
        rotate(fraction);
        translate(fraction);
    }

    public void translate(double fraction){

        a.setX(a.getX() + direction.getX()*fraction);
        a.setY(a.getY() + direction.getY()*fraction);

        b.setX(b.getX() + direction.getX()*fraction);
        b.setY(b.getY() + direction.getY()*fraction);

        c.setX(c.getX() + direction.getX()*fraction);
        c.setY(c.getY() + direction.getY()*fraction);

    }

    public void rotate(double fraction){


        Point centroid = getCentroid();
        double temp1, temp2;

        temp1 = (centroid.getX() + (a.getX() - centroid.getX()) * Math.cos(rotation * fraction) - (a.getY() - centroid.getY()) * Math.sin(rotation * fraction));
        temp2 = (centroid.getY() + (a.getY() - centroid.getY()) * Math.cos(rotation * fraction) + (a.getX() - centroid.getX()) * Math.sin(rotation * fraction));

        a.setX(temp1);
        a.setY(temp2);

        temp1 = (centroid.getX() + (b.getX() - centroid.getX()) * Math.cos(rotation * fraction) - (b.getY() - centroid.getY()) * Math.sin(rotation * fraction));
        temp2 = (centroid.getY() + (b.getY() - centroid.getY()) * Math.cos(rotation * fraction) + (b.getX() - centroid.getX()) * Math.sin(rotation * fraction));

        b.setX(temp1);
        b.setY(temp2);

        temp1 = (centroid.getX() + (c.getX() - centroid.getX()) * Math.cos(rotation * fraction) - (c.getY() - centroid.getY()) * Math.sin(rotation * fraction));
        temp2 = (centroid.getY() + (c.getY() - centroid.getY()) * Math.cos(rotation * fraction) + (c.getX() - centroid.getX()) * Math.sin(rotation * fraction));

        c.setX(temp1);
        c.setY(temp2);

    }

    public void setMass(int mass) {
        this.mass = mass;
        this.momentum = mass*velocity;
        this.kEn = momentum*velocity/2.0;
        this.momentOfInertia = mass*getSideL()*getSideL()/12;
        this.rotationalMomentum = momentOfInertia*rotation;
        this.rotKEn = momentOfInertia*rotation*rotation/2.0;
    }

    public void setRotation(double radPerSec) {
        this.rotation = radPerSec;
        this.rotationalMomentum = momentOfInertia*rotation;
        this.rotKEn = momentOfInertia*rotation*rotation/2.0;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
        this.velocity = direction.getVectorLength();
        this.momentum = mass*velocity;
        this.kEn = momentum*velocity/2.0;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private double perimeter(){
        double perimeter = 0.0;

        perimeter += Math.sqrt((b.getX()-a.getX())*(b.getX()-a.getX()) + (b.getY()-a.getY()) * (b.getY()-a.getY()));
        perimeter += Math.sqrt((c.getX()-b.getX())*(c.getX()-b.getX()) + (c.getY()-b.getY()) * (c.getY()-b.getY()));
        perimeter += Math.sqrt((a.getX()-c.getX())*(a.getX()-c.getX()) + (a.getY()-c.getY()) * (a.getY()-c.getY()));

        return perimeter;
    }

    private Point rotatePoint(Point a, Point center, double angle){
        double temp1, temp2;

        temp1 = (center.getX() + (a.getX() - center.getX()) * Math.cos(angle) - (a.getY() - center.getY()) * Math.sin(angle));
        temp2 = (center.getY() + (a.getY() - center.getY()) * Math.cos(angle) + (a.getX() - center.getX()) * Math.sin(angle));


        return new Point(temp1, temp2);

    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }

    private double getSideL(){
        return Math.sqrt((b.getX()-a.getX())*(b.getX()-a.getX()) + (b.getY()-a.getY()) * (b.getY()-a.getY()));
    }

    public Point getCentroid(){
        return new Point((a.getX() + b.getX() + c.getX())/3, (a.getY() + b.getY() + c.getY())/3);
    }

    public List<Point> getPoints() {
        List<Point> points =  new ArrayList<>();
        points.add(a);
        points.add(b);
        points.add(c);
        return points;
    }

    public int[] getXPoints() {
        return new int[] {(int)a.getX(), (int)b.getX(), (int)c.getX()};
    }

    public int[] getYPoints() {
        return new int[] {(int)a.getY(), (int)b.getY(), (int)c.getY()};
    }

    public Color getColor() {
        return color;
    }

    public Point getDirection() {
        return direction;
    }

    public double getRotation() {
        return rotation;
    }

    public int getMass() {
        return mass;
    }

    public List<Line> getSides(){
        List<Line> sides = new ArrayList<>();

        sides.add(new Line(a ,b, this));
        sides.add(new Line(b ,c, this));
        sides.add(new Line(c ,a, this));

        return sides;
    }

}
