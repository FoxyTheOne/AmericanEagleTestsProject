package api.tests.cart;

import api.models.cartModels.CartResponse;
import api.tests.extensions.GuestTokenExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
        cartController.addItemToCart(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        CartResponse afterAdd = cartController.getBag();
        int actualQtyBeforeAdd = afterAdd.getData().getItems().size();

        // Проверяем соответствие количества
        assertEquals(qtyBeforeAdd + qty, actualQtyBeforeAdd);

        assertThat(afterAdd.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, qtyBeforeAdd + qty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qtyBeforeAdd + qty);
                });
    }
}
