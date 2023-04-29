public class ConverterInt extends Converter{
    @Override
    public Spring[] returningSprings(int[] bits) {
        int n = bits.length;
        Spring[] springs = new Spring[n];
        for (int i = 0; i < n; i++) {
            double pos = (double) bits[i] / n;
            springs[i] = new Spring(pos);
        }
        return springs;
        }



    @Override
    public double decimalValue(double[] frequencyAmplitudes) {
        double result = 0;
        int n = bits.length;
        for (int i = 0; i < frequencyAmplitudes.length; i++) {
            result += frequencyAmplitudes[i] * Math.cos(2 * Math.PI * i * n / (2 * n));
        }

        return result;
    }
}
