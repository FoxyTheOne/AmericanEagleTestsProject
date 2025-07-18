package api.tests.cart;

import api.extensions.GuestTokenExtension;
import api.models.cartModels.CartResponse;
import api.models.cartModels.Item;
import api.models.cartModels.ProductSizesResponse;
import constants.ApiConstants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.AeItemsUtils;

import java.util.List;

import static constants.CommonConstants.*;

@Epic(value = API_TAG)
@Tags({@Tag(API_TAG), @Tag(EXTENDED_TAG)})
@ExtendWith(GuestTokenExtension.class)
class CartApiExtendedTests extends BaseCartApiTests {
    @Feature(value = "Add, edit, delete items in cart")
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Check quantity of items in bag, add items and check that they were added")
    void addItemToCartTest() {
        Item converseItem = ApiConstants.MAN_CONVERSE;
        int addingQuantity = 1;

        // Узнаём, сколько в корзине товаров
        List<CartResponse.Item> itemsBeforeAdd = cartSteps.getItemsInBagList();
        int allItemsInBagQuantityBeforeAdd = AeItemsUtils.getAllItemsInBagQuantity(itemsBeforeAdd);
        int addingItemQuantityBeforeAdd = AeItemsUtils.getItemQuantityBySkuId(itemsBeforeAdd, converseItem.sku);

        // Добавляем определенное количество товара
        List<CartResponse.Item> itemsAfterAdd = cartSteps
                .addItemsToCart(converseItem.sku, addingQuantity)
                .getItemsInBagList();

        // Проверяем соответствие количества
        cartSteps.checkItemsQuantityInBagAfterAdd(allItemsInBagQuantityBeforeAdd, addingItemQuantityBeforeAdd, addingQuantity, itemsAfterAdd, converseItem.sku);
    }

    @Feature(value = "Add, edit, delete items in cart")
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Add items in bag, edit them and check that they were edited")
    void editItemQuantityTest() {
        Item converseItem = ApiConstants.MAN_CONVERSE;
        int editingQuantity = 3;

        // Добавляем товар в корзину, чтобы точно было что редактировать
        List<CartResponse.Item> bagItemsListBeforeEditing = cartSteps
                .addItemsToCart(converseItem.sku, 1)
                .getItemsInBagList();

        // Узнаем item id и затем редактируем количество товара в корзине (оставляю это динамичным и узнаю актуальный сегодня itemId на сайте на случай изменений)
        converseItem.itemId = AeItemsUtils.getItemIdBySkuId(bagItemsListBeforeEditing, converseItem.sku);

        List<CartResponse.Item> bagItemsListAfterEditing = cartSteps
                .editItemsInCart(converseItem.sku, editingQuantity, converseItem.itemId)
                .getItemsInBagList();

        // Проверяем соответствие количества после изменений. Найдем в корзине измененный товар и узнаем его количество
        cartSteps.checkItemsQuantityInBagAfterEdit(editingQuantity, bagItemsListAfterEditing, converseItem.sku);
    }

    @Feature(value = "Add, edit, delete items in cart")
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Add items in bag, delete them and check that they were deleted")
    void deleteItemTest() {
        // Добавляем товар в корзину, просто чтобы корзина была точно не пустая
        // Узнаем item id любого товара и затем удаляем этот товар. Нам не важно, какой товар проверять, поэтому просто возьмём первый товар в корзине
        String firstItemInBagId = cartSteps
                .addItemsToCart(ApiConstants.WOMAN_SKIRT.sku, 2)
                .getIdOfTheFirstItemInBag();

        List<CartResponse.Item> itemListAfterDelete = cartSteps
                .deleteItemsInCart(firstItemInBagId)
                .getItemsInBagList();

        // Проверяем соответствие количества после изменений. Найдем в корзине измененный товар и узнаем его количество
        cartSteps.checkItemsQuantityAfterDelete(itemListAfterDelete, firstItemInBagId);
    }

    @Feature(value = "Check the price of product in catalog and in bag")
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Clear the bag, get product details by product id, add an item in cart and check that the prices in cart and in product details are equal")
    void checkThePricesInProductDetailsAndInCart() {
        Item pumpkinItem = ApiConstants.WOMAN_PUMPKIN_SLIPPER;
        // Узнаем стоимость товара
        ProductSizesResponse.ProductData.Record.Sizes.Sku productToAdd = cartSteps
                .getByIdFirstProductInSkusList(pumpkinItem.productId);

        // Сохраним sku id и актуальную цену товара для проверки
        pumpkinItem.sku = productToAdd.skuId;
        pumpkinItem.price = productToAdd.salePrice;

        // Предварительно очистим корзину
        // Затем добавим новый товар в корзину и проверим, совпадает ли стоимость
        double actualSalePrice = cartSteps
                .clearTheBag()
                .addItemsToCart(pumpkinItem.sku, 1)
                .getSummaryItemsInBagSalePrice();

        cartSteps.checkIfPricesAreEqualWithPaidShipping(pumpkinItem.price, actualSalePrice);
    }

    @Feature(value = "Check shipping price")
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("open product details, add items in cart until free shipping is unlocked")
    void checkFreeShipping() {
        Item converseItem = ApiConstants.MAN_CONVERSE;
        // Узнаем стоимость товара
        ProductSizesResponse.ProductData.Record.Sizes.Sku productToAdd = cartSteps
                .getByIdFirstProductInSkusList(converseItem.productId);

        // Сохраним sku id и актуальную цену товара
        converseItem.sku = productToAdd.skuId;
        converseItem.price = productToAdd.salePrice;

        // Добавляем товары, пока сумма в корзине не разблокирует бесплатную доставку
        double actualShippingPrice = cartSteps
                .addItemsToCartUntilFreeShipmentIsUlocked(converseItem)
                .getShippingPriceInBag();

        // Проверяем, что доставка стала бесплатноЙ
        cartSteps.checkIfShippingIsFree(actualShippingPrice);
    }
}