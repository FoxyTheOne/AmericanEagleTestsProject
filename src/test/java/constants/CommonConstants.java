package constants;

/**
 * Класс для констант, используемых в разных классах данного проекта
 */
public class CommonConstants {
    private CommonConstants() {
        // Not called
    }

    // Base tags
    public static final String SMOKE_TAG = "Smoke";
    public static final String EXTENDED_TAG = "Extended";
    public static final String API_TAG = "API";
    public static final String UI_TAG = "UI";

    // Importance level
    public static final String CRUCIAL_TAG = "Crucial";
    public static final String IMPORTANT_TAG = "Important";
    public static final String NICE_TO_HAVE_TAG = "Nice";

    // Positive-negative
    public static final String POSITIVE_TAG = "Positive";
    public static final String NEGATIVE_TAG = "Negative";

    // Flags
    public static final String UNSTABLE_TAG = "Unstable";
    public static final String DEFECT_TAG = "Defect";
    public static final String WITH_RETRY_TAG = "With_retry";
}