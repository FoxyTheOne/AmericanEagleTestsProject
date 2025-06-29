package api.models.cartModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Data
public class InventoryCheckResponse {
    private InventoryData data;
    private InventoryError error;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    public static class InventoryData {
        public String id;
        public String currencyCode;
        public Flags flags;
        public Summary summary;
        public List<Item> items;
        public List<InventoryWarning> warnings;
        public String userType;
        public String profileId;
        public int itemCount;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @lombok.Data
        public static class Flags {
            public String afterPayDetails;
            public String klarnaDetails;
            public boolean afterPayEligible;
            public boolean afterPayExpressEligible;
            public boolean klarnaEligible;
            public boolean cashAppPayEligible;
            public boolean giftCardPayEligible;
            public boolean shopRunnerExpressEligible;
            public boolean hasCharityItem;
            public boolean hasGiftCardItem;
            public boolean hasPickup;
            public boolean hasShipTo;
            public boolean hasVirtual;
            public boolean isBopis;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @lombok.Data
        public static class Summary {
            public String id;
            public double shipping;
            public double shippingTax;
            public double subtotal;
            public double discount;
            public double donation;
            public double subtotalMinusDiscount;
            public double tax;
            public double shippingItemsCost;
            public double pickupItemsCost;
            public double total;
            public double amountUntilFreeShipping;
            public double freeShippingThreshold;
            public double giftCardTotal;
            public double giftCardStandardTotal;
            public double giftCardInstantCreditTotal;
            public List<AppliedCoupon> appliedCoupons;
            public List<AppliedPromotion> appliedPromotions;
            public OrderSummarySavings orderSummarySavings;
            public double creditSavingsAmount;
            public double netTotal;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class AppliedCoupon {
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class AppliedPromotion {
                public String id;
                public String name;
                public String message;
                public double discount;
                public boolean qualified;
                public int type;
                public String discountType;
                public int channel;
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class OrderSummarySavings {
                public double listPriceTotal;
                public double saleMarkdown;
                public double discounts;
                public double rawShipping;
                public double subTotalBeforeDiscount;
                public double savings;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @lombok.Data
        public static class Item {
            public String itemId;
            public int quantity;
            public double discount;
            public double originalPrice;
            public double price;
            public double tax;
            public boolean discounted;
            public int discountPercent;
            public boolean gwp;
            public String itemType;
            public String productId;
            public String productStyle;
            public String productName;
            public String size;
            public String color;
            public List<Promotion> promotions;
            public String sku;
            public double unitListPrice;
            public double unitSalePrice;
            public boolean available;
            public List<ClosenessQualifier> closenessQualifiers;
            public boolean onDemandItem;
            public String brand;
            public boolean editable;
            public int availabilityStatus;
            public DeliveryOptions deliveryOptions;
            public String addItemTime;
            public ProductDetails productDetails;
            public double returnValue;
            public double discountAdjustment;
            public List<PriceDetail> priceDetails;
            public boolean clearance;
            public boolean nonreturnable;
            public boolean hazmat;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class Promotion {
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class ClosenessQualifier {
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class DeliveryOptions {
                public boolean shippingAvailable;
                public Shipping shipping;
                public boolean pickupAvailable;
                public Pickup pickup;

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class Shipping {
                    public boolean selected;
                    public boolean expeditedAvailable;
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class Pickup {
                    public boolean selected;
                    public boolean availableNearbyToday;
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class ProductDetails {
                public String brand;
                public String productName;
                public String productType;
                public String productDept;
                public String productStyle;
                public String productColor;
                public String productClass;
                public String skuSize;
                public String productId;
                public boolean onHold;
                public String taxCode;
                public List<ProductExcludedSite> productExcludedSites;
                public List<String> skuExcludedSites;
                public List<ProductClearanceSite> productClearanceSites;
                public boolean productNonReturnable;
                public ReturnInfo returnInfo;
                public boolean isProductHazmat;
                public boolean isProductThirdParty;
                public boolean isSkuOnDemand;

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class ProductExcludedSite {
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class ProductClearanceSite {
                }

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class ReturnInfo {
                }
            }

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class PriceDetail {
                public boolean discounted;
                public double amount;
                public int quantity;
                public double returnValue;
                public double orderDiscountShare;
                public double highBound;
                public double lowBound;
                public List<Adjustment> adjustments;

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class Adjustment {
                    public double quantityAdjusted;
                    public double adjustment;
                    public String adjustmentDescription;
                    public double totalAdjustment;
                    public double returnValueAdjustmentAmount;
                }
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        @lombok.Data
        public static class InventoryWarning {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    public static class InventoryError {
    }
}



