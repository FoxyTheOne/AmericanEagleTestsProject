package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.ProductPage;
import ui.pageObjects.WomenSkirtsSkortsPage;

import static constants.CommonConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({@Tag(UI_TAG), @Tag(EXTENDED_TAG)})
class CartGuestTests extends BaseTestSettings {
    HomePage homePage;

    @Override
    @BeforeEach
    void setup() {
        super.setup();
        homePage = new HomePage(driver);
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Add item to cart")
    void addItemToCart() {
        homePage.header().openWomenMenu();

        ProductPage productPage = homePage.header()
                .openWomenSkirtsSkortsPage()
                .selectFirstAvailableProductAndOpenPage();

        productPage.selectFirstAvailableSize();
        productPage.addToCart();

        CartPage cartPage = productPage.header().openCartPage();

        assertThat("Item should be added to cart",
                cartPage.getCartItemCount(),
                greaterThan(0));
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Change item quantity")
    void changeItemQuantity() {
        homePage.header().openWomenMenu();

        ProductPage productPage = homePage.header()
                .openWomenSkirtsSkortsPage()
                .selectFirstAvailableProductAndOpenPage();

        productPage.selectFirstAvailableSize();
        productPage.addToCart();

        CartPage cartPage = productPage.header().openCartPage();
        int cartItemCountBeforeEdit = cartPage.getQuantityForFirstItem();
        cartPage.increaseQuantity();
        int cartItemCountAfterEdit = cartPage.getQuantityForFirstItem();

        assertThat(cartItemCountBeforeEdit, lessThan(cartItemCountAfterEdit));
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Remove item from cart")
    void removeItemFromCart() {
        homePage.header().openWomenMenu();

        ProductPage productPage = homePage.header()
                .openWomenSkirtsSkortsPage()
                .selectFirstAvailableProductAndOpenPage();

        productPage.selectFirstAvailableSize();
        productPage.addToCart();

        CartPage cartPage = productPage.header().openCartPage();
        int cartItemCountBeforeEdit = cartPage.getCartItemCount();
        cartPage.removeFirstItem(cartItemCountBeforeEdit);

        int cartItemCountAfterEdit = cartPage.getCartItemCount();

        assertThat("Item should be removed",
                cartItemCountAfterEdit,
                lessThan(cartItemCountBeforeEdit));
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Verify price in cart")
    void verifyPriceInCart() {
        CartPage cartPage = homePage.header().openCartPage();
        cartPage.clearTheBag();

        homePage.header().openWomenMenu();

        ProductPage productPage = homePage.header()
                .openWomenSkirtsSkortsPage()
                .selectFirstAvailableProductAndOpenPage();

        double productPriceInCatalog = productPage.getProductPrice();
        productPage.selectFirstAvailableSize();
        productPage.addToCart();
        cartPage = productPage.header().openCartPage();
        double productPriceInCart = cartPage.getFirstItemPrice();

        double totalProductPriceInCart = Math.round((productPriceInCatalog + cartPage.getShippingPrice()) * 100.0) / 100.0;
        double totalSumInCart = cartPage.getSubTotalPrice();

        assertAll(
                () -> assertEquals(productPriceInCatalog, productPriceInCart, "Prices should match"),
                () -> assertEquals(totalProductPriceInCart, totalSumInCart, "Prices should match")
        );
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Unlock free shipping")
    void unlockFreeShipping() {
        CartPage cartPage = homePage.header().openCartPage();
        cartPage.clearTheBag();

        homePage.header().openWomenMenu();

        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header()
                .openWomenSkirtsSkortsPage();

        womenSkirtsSkortsPage.addItemsToUnlockFreeShipping();

        cartPage = womenSkirtsSkortsPage.header().openCartPage();
        String actualShippingMessage = cartPage.getShippingMessage();

        assertEquals("Free shipping unlocked — make the most of it!", actualShippingMessage);
    }
}
