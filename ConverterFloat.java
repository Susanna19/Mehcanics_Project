public class ConverterFloat extends Converter{
    @Override
    public Spring[] returningSprings(int[] bits) {
        return new Spring[0];
    }

    @Override
    public double decimalValue(double[] frequencyAmplitudes) {
        return 0;
    }
}
