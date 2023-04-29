import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpringArray {

    public Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        for (int i = 0; i < springExpr.length(); i++) {
            char c = springExpr.charAt(i);
            if (c == '[' || c == '{') {
                stack.push(null);
            } else if (c == ']') {
                List<Spring> springs = new ArrayList<>();
                Spring current;
                while ((current = stack.pop()) != null) {
                    springs.add(0, current);
                }
                stack.pop();
                stack.push(Spring.inParallel(springs.get(0)));
            } else if (c == '}') {
                List<Spring> springs = new ArrayList<>();
                Spring current;
                while ((current = stack.pop()) != null) {
                    springs.add(0, current);
                }
                stack.pop();
                stack.push(Spring.inSeries(springs.get(0)));
            } else {

            }
        }
        return stack.pop();
    }

}
