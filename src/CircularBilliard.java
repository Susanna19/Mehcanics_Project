import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircularBilliard {
    public static void main(String[] args) {
        double x = getRandomNumber(-1, 1);
        double y = Math.sqrt(1 - x * x);
        double px = getRandomNumber(-1, 1);
        double py = Math.sqrt(1 - px * px);

        int reflections = 5;
        double delta = 1e-6;

        List<Point> reflectionPoints = simulateCircularBilliard(x, y, px, py, reflections);

        // reversing the momentum
        double reversedPX = -reflectionPoints.get(reflections).px;
        double reversedPY = -reflectionPoints.get(reflections).py;

        List<Point> reversedReflectionPoints = simulateCircularBilliard(
                reflectionPoints.get(reflections).x,
                reflectionPoints.get(reflections).y,
                reversedPX,
                reversedPY,
                reflections
        );

        // cheking if the reversed path coincides with the straight path within delta
        boolean pathsCoincide = true;
        for (int i = 0; i <= reflections; i++) {
            double deviationX = Math.abs(reflectionPoints.get(i).x - reversedReflectionPoints.get(reflections - i).x);
            double deviationY = Math.abs(reflectionPoints.get(i).y - reversedReflectionPoints.get(reflections - i).y);
            if (deviationX > delta || deviationY > delta) {
                pathsCoincide = false;
                break;
            }
        }

        if (pathsCoincide) {
            System.out.println("The reversed path coincides with the straight path.");
        } else {
            System.out.println("The paths deviate after " + reflections + " reflections.");
        }
    }

    public static List<Point> simulateCircularBilliard(double x, double y, double px, double py, int reflections) {
        double radius = 1.0;

        List<Point> reflectionPoints = new ArrayList<>();
        reflectionPoints.add(new Point(x, y, px, py));

        while (reflections > 0) {
            double slope = py / px;
            double intercept = y - slope * x;

            // the intersection points of the line and the circle
            double a = 1.0 + slope * slope;
            double b = 2.0 * (slope * intercept - slope * y - x);
            double c = x * x + intercept * intercept - radius * radius;

            double discriminant = b * b - 4 * a * c;

            if (discriminant < 0) {
                break;
            }

            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);

            // the next intersection point
            double nextX = (px > 0) ? Math.min(root1, root2) : Math.max(root1, root2);
            double nextY = slope * nextX + intercept;

            // calculating the reflected momentum vector
            double normalX = nextX / radius;
            double normalY = nextY / radius;
            double dotProduct = px * normalX + py * normalY;
            double reflectedPX = (nextY * nextY - nextX * nextX) * px - 2 * nextX * nextY * py;
            double reflectedPY = -2 * nextX * nextY * px + (nextX * nextX - nextY * nextY) * py;

            // updating position and momentum for the next iteration
            x = nextX;
            y = nextY;
            px = reflectedPX;
            py = reflectedPY;

            // adding the reflection point to the list
            reflectionPoints.add(new Point(nextX, nextY, px, py));

            reflections--;
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
        double px;
        double py;

        Point(double x, double y, double px, double py) {
            this.x = x;
            this.y = y;
            this.px = px;
            this.py = py;
        }
    }
}
