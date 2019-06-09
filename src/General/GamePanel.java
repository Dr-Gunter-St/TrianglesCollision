package General;

import Geometry.*;
import Geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel implements Runnable{

        private boolean isRunning;

        public static final int HEIGHT = 1080;
        public static final int WIDTH = 1920;

        private static final int FPS = 60;

        private List<Triangle> triangles;


        public GamePanel(List<Triangle> triangles){

            this.triangles = triangles;

            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            start();
        }

        private void start(){
            isRunning = true;
            Thread thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {

            long start, wait, elapsed, totalTime;
            int frameCount = 0;
            totalTime = 0L;
            while (isRunning){

                start = System.nanoTime();

                tick(1f/FPS);
                repaint();

                elapsed = (System.nanoTime() - start)/1000000;
                wait = 1000/FPS - elapsed;

                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                totalTime += System.nanoTime() - start;
                frameCount++;

                if (frameCount == 60){
                    double avarageFPS = (1000/((totalTime/frameCount)/1000000));
                    frameCount = 0;
                    totalTime = 0L;
                }
            }


        }


        private void tick(double fraction){

            for (int i = 0; i < triangles.size(); i++) {

                for (int j = 0; j < triangles.size(); j++) {

                    if (i == j) continue;

                    if (trianglesOverlapSAT(triangles.get(i), triangles.get(j))){
                        triangles.get(i).setColor(Color.RED);
                        triangles.get(i).collide(triangles.get(j));
                    } else {
                        triangles.get(i).setColor(Color.black);
                    }

                }

            }

            for (Triangle tr: triangles) {
                tr.tick(fraction);
            }

        }

        private boolean trianglesOverlapSAT(Triangle t1, Triangle t2){

            List<Point> pointsT1 = t1.getPoints();
            List<Point> pointsT2 = t2.getPoints();

            for (int i = 0; i < 2; i++) {

                if (i == 1) {
                    pointsT1 = t2.getPoints();
                    pointsT2 = t1.getPoints();
                }

                for (int a = 0; a < pointsT1.size(); a++) {

                    int b = (a + 1) % pointsT1.size();

                    Point vectorAxisProjection = new Point( - (pointsT1.get(b).getY() - pointsT1.get(a).getY()) , pointsT1.get(b).getX() - pointsT1.get(a).getX());

                    double min_r1 = Double.MAX_VALUE, max_r1 = Double.MIN_VALUE;

                    for (Point aPointsT1 : pointsT1) {

                        double q = (aPointsT1.getX() * vectorAxisProjection.getX() + aPointsT1.getY() * vectorAxisProjection.getY());
                        min_r1 = Math.min(min_r1, q);
                        max_r1 = Math.max(max_r1, q);
                    }

                    double min_r2 = Double.MAX_VALUE, max_r2 = Double.MIN_VALUE;
                    for (Point aPointsT2 : pointsT2) {

                        double q = ( aPointsT2.getX() * vectorAxisProjection.getX() + aPointsT2.getY() * vectorAxisProjection.getY());
                        min_r2 = Math.min(min_r2, q);
                        max_r2 = Math.max(max_r2, q);
                    }

                    if (!(max_r2 >= min_r1 && max_r1 >= min_r2))
                        return false;


                }




            }

            return true;

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.clearRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);

            for (Triangle tr: triangles) {
                g.setColor(tr.getColor());
                g.drawPolygon(tr.getXPoints(), tr.getYPoints(), 3);
            }



        }


}
