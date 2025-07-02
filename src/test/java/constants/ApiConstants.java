package constants;

import api.models.cartModels.Item;

public class ApiConstants {
    private ApiConstants() {
        // Not called
    }

    public static final double SHIPPING_PRICE = 7.95;
    public static final double PRICE_FOR_UNLOCKING_FREE_SHIPPING = 75;

    // Items
    public static final Item MAN_CONVERSE = Item.builder()
            .itemId("563ddb44-50b9-4531-a416-baffc3f88569")
            .quantity(1)
            .originalPrice(65.0)
            .price(65.0)
            .discounted(false)
            .discountPercent(0)
            .itemType("REGULAR")
            .productId("7412_6837_001")
            .productName("Converse Chuck Taylor All Star High-Top Sneaker")
            .size("M4/W6")
            .color("Black")
            .sku("0042066233")
            .onDemandItem(false)
            .brand("AE")
            .editable(true)
            .availabilityStatus(0)
            .build();

    public static final Item MAN_JEANS = Item.builder()
            .itemId("c6f8239d-64e1-4f26-ba70-f173646a314c")
            .quantity(1)
            .originalPrice(49.95)
            .price(34.96)
            .discounted(true)
            .discountPercent(30)
            .itemType("REGULAR")
            .productId("0116_6848_001")
            .productName("AE AirFlex+ Slim Straight Jean")
            .size("28 X 32")
            .color("Black")
            .sku("0041370776")
            .onDemandItem(false)
            .brand("AE")
            .editable(true)
            .availabilityStatus(0)
            .build();

    public static final Item WOMAN_SKIRT = Item.builder()
            .itemId("91442db3-f333-4c03-95e0-a97bd402a86e")
            .quantity(1)
            .originalPrice(49.95)
            .price(19.98)
            .discounted(true)
            .discountPercent(60)
            .itemType("REGULAR")
            .productId("0313_5152_100")
            .productName("AE High-Waisted Tiered Mini Skirt")
            .size("XL")
            .color("White")
            .sku("0042516641")
            .onDemandItem(false)
            .brand("AE")
            .editable(true)
            .availabilityStatus(0)
            .build();

    public static final Item WOMAN_PUMPKIN_SLIPPER = Item.builder()
            .itemId("25424597-3b97-40f2-b05b-9c11a8125a0b")
            .quantity(1)
            .originalPrice(79.9)
            .price(79.9)
            .discounted(false)
            .discountPercent(0)
            .itemType("REGULAR")
            .productId("4412_6938_161")
            .productName("AE Snoopy Pumpkin Scuff Slipper")
            .size("9")
            .color("Ivory")
            .sku("0043345776")
            .onDemandItem(false)
            .brand("AE")
            .editable(true)
            .availabilityStatus(0)
            .build();

//    static final String TEST_SKU_ID = "0042501858";
//    static final String TEST_SKU_ID_2 = "0043270792"; // AE V-Neck Side Tie Midi Dress
//    static final String TEST_SKU_ID_3 = "0043345776"; // AE Snoopy Pumpkin Scuff Slipper
//    static final String TEST_PRODUCT_ID_4 = "2351_6335_915"; // AE Crew Neck Button-Up Denim Vest
}
