public class Converter8Bit extends Converter {
    private final int bitsPerByte = 8;
    @Override
    public Spring[] returningSprings(int[] bits) {
        int n = bits.length / bitsPerByte;
        Spring[] springs = new Spring[n];
        for (int i = 0; i < n; i++) {
            int byteValue = 0;
            for (int j = 0; j < bitsPerByte; j++) {
                byteValue += bits[i * bitsPerByte + j] * Math.pow(2, bitsPerByte - j - 1);
            }
            springs[i] = new Spring(byteValue);
        }
        return springs;
    }

    @Override
    public double decimalValue(double[] frequencyAmplitudes) {
        return 0;
    }
}
