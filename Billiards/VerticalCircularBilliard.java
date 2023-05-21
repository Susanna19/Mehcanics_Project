import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalCircularBilliard {
    public static void main(String[] args) {
        double x = getRandomNumber(-1, 1);  // Initial x-coordinate inside the circle
        double y = Math.sqrt(1 - x * x);    // Corresponding y-coordinate on the circle
        double px = getRandomNumber(5, 10); // Initial x-component of momentum
        double py = Math.sqrt(px * px - getRandomNumber(0, 25)); // Corresponding y-component of momentum

        int reflections = 5; // Number of reflections
        double delta = 0.01;

        List<Point> reflectionPoints = simulateVerticalCircularBilliard(x, y, px, py, reflections);

        // the coordinates of all reflection points
        for (Point point : reflectionPoints) {
            System.out.println("(" + point.x + ", " + point.y + ")");
        }
    }

    public static List<Point> simulateVerticalCircularBilliard(double x, double y, double px, double py, int reflections) {
        double radius = 1.0; // Unit radius
        double g = 10.0; // Gravitational acceleration

        List<Point> reflectionPoints = new ArrayList<>();
        reflectionPoints.add(new Point(x, y)); // Add the initial point

        while (reflections > 0) {
            // Calculate the time until the next reflection
            double a = px * px + py * py;
            double b = -2 * g * y;
            double c = 2 * g * radius - px * px - py * py;
            double discriminant = b * b - 4 * a * c;

            double time;
            if (discriminant < 0) {
                break;
            } else if (discriminant == 0) {
                time = -b / (2 * a);
            } else {
                double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
                double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
                time = (root1 > 0) ? root1 : root2;
            }

            // Determine the next intersection point
            double nextX = x + px * time;
            double nextY = y + py * time - 0.5 * g * time * time;

            // Calculate the reflected momentum vector
            double normalX = nextX / radius;
            double normalY = nextY / radius;
            double dotProduct = px * normalX + py * normalY;
            double reflectedPX = (nextY * nextY - nextX * nextX) * px - 2 * nextX * nextY * py;
            double reflectedPY = -2 * nextX * nextY * px + (nextX * nextX - nextY * nextY) * py;

            // Update position and momentum for the next iteration
            x = nextX;
            y = nextY;
            px = reflectedPX;
            py = reflectedPY;

            // Add the reflection point to the list
            reflectionPoints.add(new Point(nextX, nextY));

            reflections--;
        }

        return reflectionPoints;
    }

    public static double getRandomNumber(double min, double max) {
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    public static boolean isPathReversible(List<Point> path1, List<Point> path2, double delta) {
        // Check if the two paths coincide up to the specified deviation threshold
        int minSize = Math.min(path1.size(), path2.size());

        for (int i = 0; i < minSize; i++) {
            double deltaX = Math.abs(path1.get(i).x - path2.get(i).x);
            double deltaY = Math.abs(path1.get(i).y - path2.get(i).y);

            if (deltaX > delta || deltaY > delta) {
                return false;
            }
        }

        return true;
    }

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}