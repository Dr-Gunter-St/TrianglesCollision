package General;

import Geometry.*;
import Geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Visualiser {

    private static final double pi = 3.1415d;
    private static JFrame frame;

    public static void main(String[] args) {

        Triangle triangle1 = new Triangle(new Point(1200, GamePanel.HEIGHT/2), 200, 0.0);
        Triangle triangle2 = new Triangle(new Point(100, GamePanel.HEIGHT/2), 200, pi/3.0);
        triangle1.setRotation(1.0d);
        triangle1.setMass(1);
        triangle2.setDirection(new Point(60, 0));
        triangle2.setMass(2);

       Point p1 = new Point(300.0, 300.0);
        Point p2 = new Point(300.0, 000.0);
        Point p3 = new Point(150.0, 150.0);

        //System.out.println(triangle1.project(p1, p2));


       List<Triangle> triangles = new ArrayList<>();
        triangles.add(triangle1);
        triangles.add(triangle2);

        frame = new JFrame("Graphics");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel(triangles);
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
