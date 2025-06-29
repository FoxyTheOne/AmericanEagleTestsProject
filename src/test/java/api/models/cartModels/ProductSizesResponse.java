package api.models.cartModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ProductSizesResponse {
    private ProductData data;
    private ProductError error;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    public static class ProductData {
        public List<Record> records;

        @JsonIgnoreProperties(ignoreUnknown = true)
        @lombok.Data
        public static class Record {
            public Sizes sizes;

            @JsonIgnoreProperties(ignoreUnknown = true)
            @lombok.Data
            public static class Sizes {
                public List<Sku> skus;
                public String productId;
                public String productName;

                @JsonIgnoreProperties(ignoreUnknown = true)
                @lombok.Data
                public static class Sku {

                    public int displaySeq;
                    public String size;
                    public double salePrice;
                    public int inventoryStatus;
                    public boolean onlineOnly;
                    public String sizeCode;
                    public String skuId;
                    public double listPrice;
                }
            }
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @lombok.Data
    public static class ProductError {
    }
}
