package app.validators;

public class PayUserValidate {
    public static boolean isValidInput(String payment) {
        try {
            int payment1 = Integer.parseInt(payment);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidPayment(int amount, int balance) {
        return (amount >= 0 || balance + amount >= 0);
    }
}
