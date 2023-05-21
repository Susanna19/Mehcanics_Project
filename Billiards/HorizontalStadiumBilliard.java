import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorizontalStadiumBilliard {
    public static void main(String[] args) {
        double x0 = getRandomNumber(-1, 1);
        double y0 = Math.sqrt(1 - x0 * x0);
        double px0 = getRandomNumber(-1, 1);
        double py0 = Math.sqrt(1 - px0 * px0);

        double[] LValues = {0.5, 0.75, 1.0};
        int[] reflectionsValues = {5, 10, 15};

        for (double L : LValues) {
            for (int reflections : reflectionsValues) {
                List<Point> reflectionPoints = simulateHorizontalStadiumBilliard(x0, y0, px0, py0, L, reflections);
                System.out.println("L = " + L + ", " +
                        "Reflections = " + reflections);
                System.out.println("Reflection Points:");
                for (int i = 0; i < reflectionPoints.size(); i++) {
                    Point point = reflectionPoints.get(i);
                    System.out.println("Reflection " + (i + 1) + ": (" + point.x + ", " + point.y + ")");
                }
            }
        }
    }

    public static List<Point> simulateHorizontalStadiumBilliard(double x, double y, double px, double py,
                                                                double L, int reflections) {
        List<Point> reflectionPoints = new ArrayList<>();

        for (int i = 0; i < reflections; i++) {
            double nextX = 0;
            double nextY = 0;
            double nextPX = 0;
            double nextPY = 0;

            //which side the particle will hit
            if (x > 0 && y >= -L && y <= L) {
                // particle hits the right semicircle
                double xc = L;
                double pxPrime = (y * y - (x - xc) * (x - xc)) * px - 2 * (x - xc) * y * py;
                double pyPrime = -2 * (x - xc) * y * px + ((x - xc) * (x - xc) - y * y) * py;
                nextX = x;
                nextY = y;
                nextPX = pxPrime;
                nextPY = pyPrime;
            } else if (x < 0 && y >= -L && y <= L) {
                // particle hits the left semicircle
                double xc = 0;
                double pxPrime = (y * y - (x - xc) * (x - xc)) * px - 2 * (x - xc) * y * py;
                double pyPrime = -2 * (x - xc) * y * px + ((x - xc) * (x - xc) - y * y) * py;
                nextX = x;
                nextY = y;
                nextPX = pxPrime;
                nextPY = pyPrime;
            } else if (x >= -1 && x <= 1 && y > L) {
                // particle hits the top line segment
                nextX = x + (y - L) * px / py;
                nextY = L;
                nextPX = px;
                nextPY = -py;
            } else if (x >= -1 && x <= 1 && y < -L) {
                // particle hits the bottom line segment
                nextX = x - (y + L) * px / py;
                nextY = -L;
                nextPX = px;
                nextPY = -py;
            }

            reflectionPoints.add(new Point(nextX, nextY));

            // updating position and momentum for the next iteration
            x = nextX;
            y = nextY;
            px = nextPX;
            py = nextPY;
        }

        return reflectionPoints;
    }

    public static double getRandomNumber(double min, double max) {
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}