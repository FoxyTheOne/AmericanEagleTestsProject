package api.tests.cart;

import api.models.cartModels.CartResponse;
import api.tests.extensions.GuestTokenExtension;
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

@Tag(API_TAG)
@ExtendWith(GuestTokenExtension.class)
class CartApiTests extends BaseCartApiTests {
    // TODO fluent
    @Test
    @Tags({@Tag(EXTENDED_TAG), @Tag(POSITIVE_TAG)})
    @DisplayName("Check quantity of items in bag, add items and check that they were added")
    void addItemToCartTest() {
        int qty = 1;
        // Проверяем, сколько в корзине товаров
        CartResponse beforeAdd = cartController.getBag();
        int qtyBeforeAdd = beforeAdd.getData().getItems().size();

        // Добавляем определенное количество товара
        cartController.addItemsToCart(TEST_SKU_ID, qty)
                .statusCodeIs(202);

        // Проверяем соответствие количества
        CartResponse afterAdd = cartController.getBag();
        int actualQtyAfterAdd = afterAdd.getData().getItems().size();

        assertEquals(qtyBeforeAdd + qty, actualQtyAfterAdd);
        assertThat(afterAdd.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, qtyBeforeAdd + qty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qtyBeforeAdd + qty);
                });
    }

    @Test
    @Tags({@Tag(EXTENDED_TAG), @Tag(POSITIVE_TAG)})
    @DisplayName("Add items in bag, edit them and check that they were edited")
    void editItemTest() {
        int initialAddingQuantity = 1;
        int editingQuantity = 3;

        // Добавляем товар в корзину, чтобы точно было что редактировать
        cartController.addItemsToCart(TEST_SKU_ID_2, initialAddingQuantity)
                .statusCodeIs(202);

        // Узнаем item id и затем редактируем количество товара в корзине
        String firstItemsInBagListId = cartController.getBag().getData().getItems().get(0).getItemId();
        cartController.editItemsInCart(TEST_SKU_ID_2, editingQuantity, firstItemsInBagListId)
                .statusCodeIs(202);

        // Проверяем соответствие количества после изменений
        CartResponse afterEdit = cartController.getBag();

        // Найдем в корзине измененный товар и узнаем его количество
        List<CartResponse.Item> items = afterEdit.getData().getItems();
        int actualQtyAfterEdit = AeItemsUtils.getItemQuantity(items, firstItemsInBagListId);

        assertEquals(editingQuantity, actualQtyAfterEdit);
        assertThat(afterEdit.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID_2, editingQuantity)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID_2);
                    assertThat(item.getQuantity()).isEqualTo(editingQuantity);
                });
    }
}