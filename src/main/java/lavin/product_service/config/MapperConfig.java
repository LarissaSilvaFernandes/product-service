package lavin.product_service.config;

import lavin.product_service.dtos.mappers.ProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ProductMapper productMapper() {
        return ProductMapper.INSTANCE;
    }
}
