package util;

import java.math.BigDecimal;

/**
 * @author sangm (sang.mercado@gmail.com)
 */
public enum Currency {
    US, EURO, YEN;

    private static final Double EURO_CONSTANT = 0.79;
    private static final Double YEN_CONSTANT = 94.1;

    public static Double convert(Double amount, Currency currency) {
        if (currency == Currency.US) {
            return amount;
        } else if (currency == Currency.EURO) {
            Double unTruncatedAmount = amount / EURO_CONSTANT;
            return new BigDecimal(unTruncatedAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        } else {
            Double unTruncatedAmount = amount / YEN_CONSTANT;
            return new BigDecimal(unTruncatedAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }
}
