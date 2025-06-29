package api.tests.cart;

import api.controllers.cartController.CartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseCartApiTests {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseCartApiTests.class);
    static final String TEST_SKU_ID = "0042501858";
    static final String TEST_SKU_ID_2 = "0043270792"; // AE V-Neck Side Tie Midi Dress
    static final String TEST_SKU_ID_3 = "0043345776"; // AE Snoopy Pumpkin Scuff Slipper
    static final String TEST_PRODUCT_ID_4 = "2351_6335_915"; // AE Crew Neck Button-Up Denim Vest
    static final double SHIPPING_PRICE = 7.95;
    static final double PRICE_FOR_UNLOCKING_FREE_SHIPPING = 75;

    final CartController cartController = new CartController();
}
