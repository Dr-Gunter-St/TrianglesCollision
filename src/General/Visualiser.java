package General;

import Geometry.*;
import Geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Visualiser {

    private static JFrame frame;

    public static void main(String[] args) {

        Triangle triangle1 = new Triangle(400, 100, 200, 400, 600, 400);
        Triangle triangle2 = new Triangle(400, 500, 200, 200, 600, 200);
        triangle1.setRotation(1f);
        triangle2.setDirection(new Point(60, 0));

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
