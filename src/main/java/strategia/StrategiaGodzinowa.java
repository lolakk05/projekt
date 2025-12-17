package strategia;

public class StrategiaGodzinowa implements StrategiaCenowa {

    @Override
    public double wyliczKoszt(long czasTrwania, double stawkaBazowa) {
        long liczbaDob = (long) Math.ceil(czasTrwania / (24.0 * 60.0));
        return liczbaDob * stawkaBazowa;
    }
}
