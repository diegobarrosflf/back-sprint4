package br.com.rchlo.store.repository;

import br.com.rchlo.store.domain.Product;
import br.com.rchlo.store.dto.ProductByColorDto;
import br.com.rchlo.store.projection.ProductByColorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // essa query pode ter problemas de performance: @see https://vladmihalcea.com/hibernate-multiplebagfetchexception/ */
    @Query("select distinct p from Product p left join fetch p.images join fetch p.category left join fetch p.availableSizes order by p.name")
    public List<Product> findAllWithImagesCategoryAndSizesOrderByName();

    /*public List<ProductByColorDto> productsByColor() {
        return entityManager.createQuery("select new br.com.rchlo.store.dto.ProductByColorDto(p.color, count(p)) from Product p group by p.color", ProductByColorDto.class).getResultList();
    }*/
    @Query(value = "select color, count(color) as amount from product p group by color ", nativeQuery = true)
    public List<ProductByColorProjection> productsByColor();



}
