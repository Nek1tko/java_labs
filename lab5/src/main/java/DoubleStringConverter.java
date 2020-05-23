import javafx.util.StringConverter;

public class DoubleStringConverter extends StringConverter<Double> {

    @Override
    public String toString(Double number) {
        if (number != null) {
            return number.toString();
        }
        return null;
    }

    @Override
    public Double fromString(String string) {
        if (string != null) {
            try {
                double number = Double.parseDouble(string);
                return number;
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
