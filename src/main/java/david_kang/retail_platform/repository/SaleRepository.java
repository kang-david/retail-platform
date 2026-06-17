package david_kang.retail_platform.repository;

import david_kang.retail_platform.entity.Sale;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository
        extends JpaRepository<Sale, Long> {

    List<Sale> findByProductId(Long productId);

}