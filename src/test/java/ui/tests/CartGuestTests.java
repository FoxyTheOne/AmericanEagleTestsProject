package ui.tests;

import org.junit.jupiter.api.*;
import ui.pageObjects.CartPage;
import ui.pageObjects.HomePage;
import ui.pageObjects.ProductPage;
import ui.pageObjects.WomenSkirtsSkortsPage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import static constants.CommonConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        writeHtmlInFile();
        super.tearDown();
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Add item to cart")
    void addItemToCart() {
        // Открываем выпадающее меню
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        // Переходим на другие страницы:
        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();
        womenSkirtsSkortsPage.closeShadowWindow();

        // Выбираем первый товар
        ProductPage productPage = womenSkirtsSkortsPage.selectFirstAvailableProductAndOpenPage(wait5sec);
        productPage.closeShadowWindow();

        productPage.selectFirstAvailableSize(wait5sec, actions);
        productPage.addToCart(wait5sec, actions);

        // Открываем корзину
        CartPage cartPage = productPage.header().openCartPage();

        // Проверяем, добавился ли товар
        assertTrue(cartPage.getCartItemCount(wait5sec) > 0, "Item should be added to cart");
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Change item quantity")
    void changeItemQuantity() {
        // Добавление товара в корзину, как в первом тесте
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();
        womenSkirtsSkortsPage.closeShadowWindow();
        ProductPage productPage = womenSkirtsSkortsPage.selectFirstAvailableProductAndOpenPage(wait5sec);
        productPage.closeShadowWindow();

        productPage.selectFirstAvailableSize(wait5sec, actions);
        productPage.addToCart(wait5sec, actions);

        // Увеличение количества первого товара в корзине
        CartPage cartPage = productPage.header().openCartPage();
        int cartItemCountBeforeEdit = cartPage.getQuantityForFirstItem(wait5sec);
        cartPage.increaseQuantity(wait5sec, actions);
        int cartItemCountAfterEdit = cartPage.getQuantityForFirstItem(wait5sec);

        assertTrue(cartItemCountBeforeEdit < cartItemCountAfterEdit);
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Remove item from cart")
    void removeItemFromCart() {
        // Снова добавляем товар в корзину
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();
        womenSkirtsSkortsPage.closeShadowWindow();
        ProductPage productPage = womenSkirtsSkortsPage.selectFirstAvailableProductAndOpenPage(wait5sec);
        productPage.closeShadowWindow();

        productPage.selectFirstAvailableSize(wait5sec, actions);
        productPage.addToCart(wait5sec, actions);

        // Удаление первого товара в корзине
        CartPage cartPage = productPage.header().openCartPage();
        int cartItemCountBeforeEdit = cartPage.getCartItemCount(wait5sec);
        cartPage.removeFirstItem(wait5sec);

        // TODO без дополнительного ожидания не работает, подумать как заменить
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }

        int cartItemCountAfterEdit = cartPage.getCartItemCount(wait5sec);

        assertTrue(cartItemCountAfterEdit < cartItemCountBeforeEdit, "Item should be removed");
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Verify price in cart")
    void verifyPriceInCart() {
        // Предварительно очистим корзину
        CartPage cartPage = homePage.header().openCartPage();
        cartPage.clearTheBag(wait5sec);

        // Добавим товар в корзину и сохраним его стоимость для дальнейшего сравнения
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();
        womenSkirtsSkortsPage.closeShadowWindow();
        ProductPage productPage = womenSkirtsSkortsPage.selectFirstAvailableProductAndOpenPage(wait5sec);
        productPage.closeShadowWindow();

        double productPriceInCatalog = productPage.getProductPrice(wait5sec);

        productPage.selectFirstAvailableSize(wait5sec, actions);
        productPage.addToCart(wait5sec, actions);

        // Сравниваем цены
        cartPage = productPage.header().openCartPage();
        double productPriceInCart = cartPage.getFirstItemPrice(wait5sec);

        assertEquals(productPriceInCatalog, productPriceInCart, "Prices should match");
    }

    @Test
    @Tag(PO_CRUCIAL_TAG)
    @DisplayName("Unlock free shipping")
    void unlockFreeShipping() {
        // Предварительно очистим корзину
        CartPage cartPage = homePage.header().openCartPage();
        cartPage.clearTheBag(wait5sec);

        // Выберем товар и узнаем его цену
        homePage.header().openWomenMenu(actions);
        homePage.header().waitForSkirtsLink(wait5sec);

        WomenSkirtsSkortsPage womenSkirtsSkortsPage = homePage.header().openWomenSkirtsSkortsPage();
        womenSkirtsSkortsPage.closeShadowWindow();
        ProductPage productPage = womenSkirtsSkortsPage.selectFirstAvailableProductAndOpenPage(wait5sec);
        productPage.closeShadowWindow();

        double productPrice = productPage.getProductPrice(wait5sec);
        productPage.selectFirstAvailableSize(wait5sec, actions);

        // Рассчитываем сколько нужно добавить товаров
        // TODO перенести метод в Utils
        int itemsNeeded = (int) Math.ceil(75 / productPrice);

        // Добавляем товары, пока не разблокируется бесплатная доставка
        for (int i = 1; i <= itemsNeeded; i++) {
            productPage.addToCart(wait5sec, actions);
            productPage.closeShadowWindow();
        }

        // Заходим в корзину и проверяем
        cartPage = productPage.header().openCartPage();
        productPage.closeShadowWindow();

        String actualShippingMessage = cartPage.getshippingMessage();
        assertEquals("Free shipping unlocked — make the most of it!", actualShippingMessage);
    }

    // TODO перенести метод в Utils
    private void writeHtmlInFile() {
        File myFile = new File("src/test/resources/my_txt.txt");
        try {
            boolean isFileCreated = myFile.createNewFile();

            if (isFileCreated) {
                System.out.println("File was created");
            } else {
                myFile.delete();
                myFile.createNewFile();
                System.out.println("File was deleted and created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(myFile, true)) {
            fw.write(Objects.requireNonNull(driver.getPageSource()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
