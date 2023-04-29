public class FT {
    private int n;
    private double[] data;

    public FT(double[] data) {
        this.data = data;
        this.n = data.length;
    }

    public double[] getAmplitudes() {
        double[] amplitudes = new double[n/2 + 1];

        for (int j = 0; j <= n/2; j++) {
            double real = 0.0;
            double imag = 0.0;

            for (int i = 0; i < n; i++) {
                real += data[i] * Math.cos(2 * Math.PI * j * i / n);
                imag -= data[i] * Math.sin(2 * Math.PI * j * i / n);
            }

            amplitudes[j] = Math.sqrt(real*real + imag*imag) / n;
        }

        return amplitudes;
    }
}
