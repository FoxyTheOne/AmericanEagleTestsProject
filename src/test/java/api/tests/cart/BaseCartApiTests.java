package api.tests.cart;

import api.controllers.cartController.CartController;
import api.steps.CartSteps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseCartApiTests {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseCartApiTests.class);
    final CartController cartController = new CartController();
    final CartSteps cartSteps = new CartSteps(cartController);
}
