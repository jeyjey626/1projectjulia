package Utilities;

public class NumberCheck {
    public static boolean isNumber(String string){
        boolean numeric;

        numeric = string.matches("\\d+(\\.\\d+)?");
        return numeric;
    }
}
