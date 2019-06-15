package Geometry;

public class Point implements Comparable<Point>{

    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Point(Point a, Point b){
        x = b.x - a.x;
        y = b.y - a.y;
    }

    public double getVectorLength(){
        return Math.sqrt(x*x + y*y);
    }

    public Point multiply(double d){
        double newX = x*d;
        double newY = y * d;

        return new Point(newX, newY);
    }

    public static Point v_summ(Point a, Point b){
        return new Point(a.x + b.x, a.y + b.y);
    }

    public static double scalar_product(Point a, Point b){
        return a.x*b.x + a.y*b.y;
    }

    public static Point clone(Point p){
        return new Point(p.x, p.y);
    }

    public void setLength(double d){
        double len = Math.sqrt(x*x + y*y);

        x = (x/len)*d;
        y = (y/len)*d;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        if (this.x > o.x) return 1;
        if (this.x == o.x && this.y > o.y) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        return x == point.x && y == point.y;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
