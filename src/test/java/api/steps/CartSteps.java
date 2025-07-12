package api.steps;

import api.controllers.cartController.CartController;
import api.models.cartModels.CartResponse;
import api.models.cartModels.Item;
import api.models.cartModels.ProductSizesResponse;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AeItemsUtils;

import java.util.List;

import static constants.CommonConstants.PRICE_FOR_UNLOCKING_FREE_SHIPPING;
import static constants.CommonConstants.SHIPPING_PRICE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CartSteps.class);
    CartController cartController;

    public CartSteps(CartController cartController) {
        this.cartController = cartController;
    }

    // CREATE, ADD - post
    @Step("Add item to bag and check status code")
    public CartSteps addItemsToCart(String itemToCheckSku, int qty) {
        cartController.addItemsToCart(itemToCheckSku, qty)
                .statusCodeIs(202);
        return this;
    }

    @Step("Add items to cart until free shipment is ulocked")
    public CartSteps addItemsToCartUntilFreeShipmentIsUlocked(Item item) {
        double totalSumInCart = getSummaryItemsInBagSalePrice();

        while (totalSumInCart < PRICE_FOR_UNLOCKING_FREE_SHIPPING) {
            LOGGER.debug("totalSumInCart: {}", totalSumInCart);
            addItemsToCart(item.sku, 1);
            totalSumInCart = totalSumInCart + item.price;
        }
        LOGGER.debug("totalSumInCart: {}", totalSumInCart);

        return this;
    }

    // GET - get
    @Step("Get list of items in bag")
    public List<CartResponse.Item> getItemsInBagList() {
        return cartController.getBag()
                .getData()
                .getItems();
    }

    @Step("Get the id of the first item in bag")
    public String getIdOfTheFirstItemInBag() {
        return cartController.getBag()
                .getData()
                .getItems()
                .get(0)
                .getItemId();
    }

    @Step("Get by id first product in skus list")
    public ProductSizesResponse.ProductData.Record.Sizes.Sku getByIdFirstProductInSkusList(String productId) {
        return cartController.getProductDetailsById(productId)
                .getData()
                .getRecords()
                .get(0)
                .getSizes()
                .getSkus()
                .get(0);
    }

    @Step("Get summary items in bag sale price")
    public double getSummaryItemsInBagSalePrice() {
        return cartController.getCartInventory()
                .getData()
                .getSummary()
                .netTotal;
    }

    @Step("Get summary shipping price")
    public double getShippingPriceInBag() {
        return cartController.getCartInventory()
                .getData()
                .getSummary()
                .shipping;
    }

    // FULL UPDATE - put

    // PART UPDATE - patch
    @Step("Edit item in bag and check status code")
    public CartSteps editItemsInCart(String itemSku, int editingQuantity, String itemId) {
        cartController.editItemsInCart(itemSku, editingQuantity, itemId)
                .statusCodeIs(202);
        return this;
    }

    // DELETE - delete
    @Step("Delete item in bag and check status code")
    public CartSteps deleteItemsInCart(String firstItemInBagId) {
        cartController.deleteItemsInCart(firstItemInBagId)
                .statusCodeIs(202);
        return this;
    }

    @Step("Delete all items in bag")
    public CartSteps clearTheBag() {
        List<CartResponse.Item> bagItems = getItemsInBagList();

        for (CartResponse.Item item : bagItems) {
            String itemId = item.getItemId();
            LOGGER.debug("item {} will be deleted", itemId);
            deleteItemsInCart(itemId);
        }
        return this;
    }

    // Assertions
    @Step("Check if quantity in bag after add is greater than before by the added amount")
    public void checkItemsQuantityInBagAfterAdd(int allItemsInBagQuantityBeforeAdd, int addingItemQuantityBeforeAdd, int addingQuantity, List<CartResponse.Item> itemsAfterAdd, String itemSku) {
        int itemsInBagQuantityAfterAdd = AeItemsUtils.getAllItemsInBagQuantity(itemsAfterAdd);
        int addingItemQuantityAfterAdd = AeItemsUtils.getItemQuantityBySkuId(itemsAfterAdd, itemSku);

        assertEquals(allItemsInBagQuantityBeforeAdd + addingQuantity, itemsInBagQuantityAfterAdd);
        assertEquals(addingItemQuantityBeforeAdd + addingQuantity, addingItemQuantityAfterAdd);
    }

    @Step("Check if quantity in bag after edit is equal to editing quantity")
    public void checkItemsQuantityInBagAfterEdit(int editingQuantity, List<CartResponse.Item> bagItemsListAfterEditing, String itemSku) {
        int actualQtyAfterEdit = AeItemsUtils.getItemQuantityBySkuId(bagItemsListAfterEditing, itemSku);

        assertEquals(editingQuantity, actualQtyAfterEdit);
    }

    @Step("Check quantity of the deleted item")
    public void checkItemsQuantityAfterDelete(List<CartResponse.Item> itemListAfterDelete, String itemId) {
        int actualQtyAfterEdit = AeItemsUtils.getItemQuantityByItemId(itemListAfterDelete, itemId);

        assertEquals(0, actualQtyAfterEdit);
    }

    @Step("Check price of items and paid shipping is equal with summary price in bag")
    public void checkIfPricesAreEqualWithPaidShipping(double itemsSummaryPrice, double salePriceInBag) {
        assertEquals(Math.round((itemsSummaryPrice + SHIPPING_PRICE) * 100.0) / 100.0,
                Math.round(salePriceInBag * 100.0) / 100.0);

    }

    @Step("Check if shipping is free")
    public void checkIfShippingIsFree(double actualShippingPrice) {
        assertEquals(0, actualShippingPrice);
    }
}
