public abstract class Converter {
    protected int[] bits;
    protected Spring[] springs;

    public abstract Spring[] returningSprings(int[] bits);
    public abstract double decimalValue(double[] frequencyAmplitudes);



}
