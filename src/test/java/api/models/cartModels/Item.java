package api.models.cartModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Item {
    public String itemId;
    public int quantity;
    public double originalPrice;
    public double price;
    public boolean discounted;
    public int discountPercent;
    public String itemType;
    public String productId;
    public String productName;
    public String size;
    public String color;
    public String sku;
    public boolean onDemandItem;
    public String brand;
    public boolean editable;
    public int availabilityStatus;
}
