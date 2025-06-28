package utils;

import api.models.cartModels.CartResponse;
import io.qameta.allure.Step;

import java.util.List;

public class AeItemsUtils {
    @Step("Calculating item quantity")
    public static int getItemQuantity(List<CartResponse.Item> items, String itemId) {
        CartResponse.Item itemToCheck = null;
        for (CartResponse.Item item : items) {
            if (item.getItemId().equals(itemId)) {
                itemToCheck = item;
            }
        }
        return itemToCheck != null ? itemToCheck.getQuantity() : 0;
    }
}
