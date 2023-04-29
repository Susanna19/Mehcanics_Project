public class Spring {
    private static double k = 1.0;

    public Spring(){

    }

    public Spring(double k){
        this.k = k;
    }

    public double getStiffness(){
        return k;
    }

    private void setStiffness(double k){
        this.k = k;
    }


    public double[] move(double t, double dt, double x0, double v0){
        int steps = (int) Math.ceil(t / dt);
        double[] arr = new double[steps];
        double x = x0; // initial position
        double v = v0; // initial velocity
        for (int i = 0; i < steps; i++){
            double a = -(k * x); // acceleration
            v += a * dt; // update velocity
            x += v * dt; // update position
            arr[i] = x; // store position
        }
        return arr;
    }

    public double[] move(double t, double dt, double x0) {
        double[] arr = new double[(int) (t / dt) + 1];
        double x = x0; // initial position
        double v = 0.0; // initial velocity
        for (int i = 0; i < arr.length; i++){
            arr[i] = x; // store position
            double a = -k * x; // acceleration
            x += v * dt; // update position
            v += a * dt; // update velocity
        }
        return arr;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        int n = (int) ((t1 - t0) / dt);
        double[] x = new double[n+1];
        x[0] = x0;
        double v = v0;
        for (int i = 1; i <= n; i++) {
            double a = -k * x[i-1];
            x[i] = x[i-1] + v * dt;
            v += a * dt;
        }
        return x;
    }

    public static double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        int numSteps = (int) ((t1 - t0) / dt);
        double[] result = new double[numSteps + 1];
        result[0] = x0;
        double x = x0, v = v0;
        for (int i = 1; i <= numSteps; i++) {
            double a = -k / m * x;
            x += v * dt;
            v += a * dt;
            result[i] = x;
        }
        return result;
    }

    public static Spring inSeries(Spring that) {
        double equivalentSpring = k + that.getStiffness();
        return new Spring(equivalentSpring);
    }

    public static Spring inParallel(Spring that) {
        double equivalentSpring  = 1.0 / (1.0 / k + 1.0 / that.getStiffness());
        return new Spring(equivalentSpring );
    }

}
