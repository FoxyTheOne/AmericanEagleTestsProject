package api.tests.cart;

import api.models.cartModels.CartResponse;
import api.models.cartModels.ProductSizesResponse;
import api.tests.extensions.RoleBasedExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.AeItemsUtils;

import java.util.List;

import static constants.CommonConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tags({@Tag(API_TAG), @Tag(EXTENDED_TAG)})
@ExtendWith(RoleBasedExtension.class)
class CartApiExtendedTests extends BaseCartApiTests {
    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Check quantity of items in bag, add items and check that they were added")
    void addItemToCartTest() {
        int qty = 1;
        // Проверяем, сколько в корзине товаров
        List<CartResponse.Item> itemsBeforeAdd = cartController.getBag()
                .getData()
                .getItems();
        int allItemsInBagQuantityBeforeAdd = AeItemsUtils.getAllItemsInBagQuantity(itemsBeforeAdd);
        int addingItemQuantityBeforeAdd = AeItemsUtils.getItemQuantityBySkuId(itemsBeforeAdd, TEST_SKU_ID);

        // Добавляем определенное количество товара
        cartController.addItemsToCart(TEST_SKU_ID, qty)
                .statusCodeIs(202);

        // Проверяем соответствие количества
        List<CartResponse.Item> itemsAfterAdd = cartController.getBag()
                .getData()
                .getItems();
        int itemsInBagQuantityAfterAdd = AeItemsUtils.getAllItemsInBagQuantity(itemsAfterAdd);
        int addingItemQuantityAfterAdd = AeItemsUtils.getItemQuantityBySkuId(itemsAfterAdd, TEST_SKU_ID);


        assertEquals(allItemsInBagQuantityBeforeAdd + qty, itemsInBagQuantityAfterAdd);
        assertEquals(addingItemQuantityBeforeAdd + qty, addingItemQuantityAfterAdd);
        assertThat(itemsAfterAdd)
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, addingItemQuantityBeforeAdd + qty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(addingItemQuantityBeforeAdd + qty);
                });
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Add items in bag, edit them and check that they were edited")
    void editItemTest() {
        int initialAddingQuantity = 1;
        int editingQuantity = 3;

        // Добавляем товар в корзину, чтобы точно было что редактировать
        cartController.addItemsToCart(TEST_SKU_ID_2, initialAddingQuantity)
                .statusCodeIs(202);

        // Узнаем item id и затем редактируем количество товара в корзине
        List<CartResponse.Item> bagItemsListBeforeEditing = cartController.getBag()
                .getData()
                .getItems();
        String itemId = AeItemsUtils.getItemIdBySkuId(bagItemsListBeforeEditing, TEST_SKU_ID_2);

        cartController.editItemsInCart(TEST_SKU_ID_2, editingQuantity, itemId)
                .statusCodeIs(202);

        // Проверяем соответствие количества после изменений. Найдем в корзине измененный товар и узнаем его количество
        List<CartResponse.Item> bagItemsListAfterEditing = cartController.getBag()
                .getData()
                .getItems();
        int actualQtyAfterEdit = AeItemsUtils.getItemQuantityBySkuId(bagItemsListAfterEditing, TEST_SKU_ID_2);

        assertEquals(editingQuantity, actualQtyAfterEdit);
        assertThat(bagItemsListAfterEditing)
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID_2, editingQuantity)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID_2);
                    assertThat(item.getQuantity()).isEqualTo(editingQuantity);
                });
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Add items in bag, delete them and check that they were deleted")
    void deleteItemTest() {
        int initialAddingQuantity = 2;

        // Добавляем товар в корзину, чтобы точно было что редактировать
        cartController.addItemsToCart(TEST_SKU_ID_3, initialAddingQuantity)
                .statusCodeIs(202);

        // Узнаем item id и затем удаляем этот товар
        String firstItemsInBagListId = cartController.getBag()
                .getData()
                .getItems()
                .get(0)
                .getItemId();
        cartController.deleteItemsInCart(firstItemsInBagListId)
                .statusCodeIs(202);

        // Проверяем соответствие количества после изменений. Найдем в корзине измененный товар и узнаем его количество
        List<CartResponse.Item> items = cartController.getBag()
                .getData()
                .getItems();
        int actualQtyAfterEdit = AeItemsUtils.getItemQuantityByItemId(items, firstItemsInBagListId);

        assertEquals(0, actualQtyAfterEdit);
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("Clear the bag, open quick shop, add an item in cart and check that the prices in cart and in quick shop are equal")
    void checkThePricesInQuickShopAndInCart() {
        // Откроем быстрый просмотр товара и узнаем его нынешнюю стоимость
        ProductSizesResponse.ProductData.Record.Sizes.Sku productToAdd = cartController.openQuickShopById(TEST_PRODUCT_ID_4)
                .statusCodeIs(200)
                .getProductSizesResponse()
                .getData()
                .getRecords()
                .get(0)
                .getSizes()
                .getSkus()
                .get(0);

        // Сохраним sku id и актуальную цену товара
        String newSkuId = productToAdd.skuId;
        double expectedSalePrice = productToAdd.salePrice;
        LOGGER.debug(String.valueOf(expectedSalePrice));
        LOGGER.debug(String.valueOf(expectedSalePrice + SHIPPING_PRICE));

        // Предварительно очистим корзину
        List<CartResponse.Item> bagItems = cartController.getBag()
                .getData()
                .getItems();

        for (CartResponse.Item item : bagItems) {
            String itemId = item.getItemId();
            LOGGER.debug("item {} will be deleted", itemId);
            cartController.deleteItemsInCart(itemId)
                    .statusCodeIs(202);
        }

        // Теперь добавим новый товар в корзину и проверим, совпадает ли стоимость
        cartController.addItemsToCart(newSkuId, 1)
                .statusCodeIs(202);

        double actualSalePrice = cartController.getCartInventory()
                .statusCodeIs(200)
                .getInventoryCheckResponse()
                .getData()
                .getSummary()
                .netTotal;
        LOGGER.debug(String.valueOf(actualSalePrice));

        assertEquals(Math.round((expectedSalePrice + SHIPPING_PRICE) * 100.0) / 100.0,
                Math.round(actualSalePrice * 100.0) / 100.0);
    }

    @Test
    @Tags({@Tag(POSITIVE_TAG), @Tag(PO_CRUCIAL_TAG)})
    @DisplayName("open quick shop, add items in cart until free shipping is unlocked")
    void checkFreeShipping() {
        // Откроем быстрый просмотр товара и узнаем его нынешнюю стоимость
        ProductSizesResponse.ProductData.Record.Sizes.Sku productToAdd = cartController.openQuickShopById(TEST_PRODUCT_ID_4)
                .statusCodeIs(200)
                .getProductSizesResponse()
                .getData()
                .getRecords()
                .get(0)
                .getSizes()
                .getSkus()
                .get(0);

        // Сохраним sku id и актуальную цену товара
        String selectedProductSkuId = productToAdd.skuId;
        double selectedProductSalePrice = productToAdd.salePrice;

        // Добавляем товары, пока сумма в корзине не разблокирует бесплатную доставку
        double totalSumInCart = cartController.getCartInventory()
                .statusCodeIs(200)
                .getInventoryCheckResponse()
                .getData()
                .getSummary()
                .netTotal;

        while (totalSumInCart < PRICE_FOR_UNLOCKING_FREE_SHIPPING) {
            LOGGER.debug("totalSumInCart: {}", totalSumInCart);
            cartController.addItemsToCart(selectedProductSkuId, 1)
                    .statusCodeIs(202);
            totalSumInCart = totalSumInCart + selectedProductSalePrice;
        }
        LOGGER.debug("totalSumInCart: {}", totalSumInCart);

        // Проверяем, что доставка стала бесплатной
        double actualShippingPrice = cartController.getCartInventory()
                .statusCodeIs(200)
                .getInventoryCheckResponse()
                .getData()
                .getSummary()
                .shipping;

        assertEquals(0, actualShippingPrice);
    }
}