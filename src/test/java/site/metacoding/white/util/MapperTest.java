package site.metacoding.white.util;

import org.junit.jupiter.api.Test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String mcp; // 제조사

    @Builder
    public Product(Integer id, String name, Integer price, Integer qty, String mcp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.mcp = mcp;
    }
}

@Setter
@Getter
class ProductDto {
    private String name;
    private Integer price;
    private Integer qty;

    // Product -> ProductDto 로 변경
    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.qty = product.getQty();
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .qty(qty)
                .build();
    }

}

public class MapperTest {

    // Entity와 DTO의 개념을 이해하고 있는가 ! 한번해보세요 !
    @Test
    public void 매핑하기1() {
        // 1. Product 객체 생성(디폴트) 2. 값넣기
        Product product = Product.builder()
                .id(1)
                .mcp("삼성")
                .name("바나나")
                .price(1000)
                .qty(100)
                .build();

        // 3. ProductDto 객체생성(디폴트) 4. Product -> ProductDto로 옮기기
        ProductDto productDto = new ProductDto(product);

        // 5. ProductDto -> product 변경
        Product product2 = productDto.toEntity();
    }
}
