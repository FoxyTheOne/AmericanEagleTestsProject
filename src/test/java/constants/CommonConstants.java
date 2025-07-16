package constants;

/**
 * Класс для констант, используемых в разных классах данного проекта
 */
public class CommonConstants {
    private CommonConstants() {
        // Not called
    }

    public static final double PRICE_FOR_UNLOCKING_FREE_SHIPPING = 75;
    public static final double SHIPPING_PRICE = 7.95;

    // Base tags
    public static final String SMOKE_TAG = "Smoke";
    public static final String EXTENDED_TAG = "Extended";
    public static final String API_TAG = "API";
    public static final String UI_TAG = "UI";

    // Importance level
    public static final String PO_CRUCIAL_TAG = "PO";
    public static final String P1_IMPORTANT_TAG = "P1";
    public static final String P2_NICE_TO_HAVE_TAG = "P2";

    // Positive-negative
    public static final String POSITIVE_TAG = "Positive";
    public static final String NEGATIVE_TAG = "Negative";

    // Flags
    public static final String UNSTABLE_TAG = "Unstable";
    public static final String DEFECT_TAG = "Defect";
    public static final String WITH_RETRY_TAG = "With_retry";
    public static final String NO_SCREENSHOT = "No_screenshot";
}