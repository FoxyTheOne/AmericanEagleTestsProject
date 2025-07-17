package utils;

import api.models.cartModels.CartResponse;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.tests.BaseTestSettings;

import java.util.List;

public class AeItemsUtils {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTestSettings.class);

    @Step("Get item id by sku id")
    public static String getItemIdBySkuId(List<CartResponse.Item> items, String skuId) {
        String itemId = "";
        for (CartResponse.Item item : items) {
            if (item.getSku().equals(skuId)) {
                itemId = item.getItemId();
            }
        }
        return itemId;
    }

    @Step("Calculating item quantity")
    public static int getItemQuantityByItemId(List<CartResponse.Item> items, String itemId) {
        CartResponse.Item itemToCheck = null;
        for (CartResponse.Item item : items) {
            if (item.getItemId().equals(itemId)) {
                itemToCheck = item;
            }
        }
        return itemToCheck != null ? itemToCheck.getQuantity() : 0;
    }

    @Step("Calculating item quantity")
    public static int getItemQuantityBySkuId(List<CartResponse.Item> items, String skuId) {
        CartResponse.Item itemToCheck = null;
        for (CartResponse.Item item : items) {
            if (item.getSku().equals(skuId)) {
                itemToCheck = item;
            }
        }
        return itemToCheck != null ? itemToCheck.getQuantity() : 0;
    }

    @Step("Calculating items in bag quantity")
    public static int getAllItemsInBagQuantity(List<CartResponse.Item> items) {
        int itemsInBagQuantity = 0;
        LOGGER.debug("itemsInBagQuantity = {}", itemsInBagQuantity);
        for (CartResponse.Item item : items) {
            int itemQuantity = item.getQuantity();
            itemsInBagQuantity = itemsInBagQuantity + itemQuantity;
            LOGGER.debug("itemsInBagQuantity = {}", itemsInBagQuantity);
        }
        return itemsInBagQuantity;
    }
}
