package org.max.home;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.max.home.AbstractTest.getSession;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsTest extends AbstractTest{

    @Order(1)
    @Test
    void addValue(){
        //given
        ProductsEntity entity = new ProductsEntity();
        entity.setProductId((short) 11);
        entity.setMenuName("Lasagna");
        entity.setPrice("600");
        //when
        Session session = getSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();

        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id="+11).addEntity(ProductsEntity.class);
        ProductsEntity productsEntity = (ProductsEntity) query.uniqueResult();
        //then
        Assertions.assertNotNull(productsEntity);
    }

    @Order(2)
    @Test
    void deleteValue() {
        //given
        final Query query = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id="+11).addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productsEntityBeforeDelete = query.uniqueResultOptional();
        Assertions.assertTrue(productsEntityBeforeDelete.isPresent());

        //when
        Session session = getSession();
        session.beginTransaction();
        session.delete(productsEntityBeforeDelete);
        session.getTransaction().commit();

        //then
        final Query queryAfterDelete = getSession()
                .createSQLQuery("SELECT * FROM products WHERE product_id="+11).addEntity(ProductsEntity.class);
        Optional<ProductsEntity> productsEntityAfterDelete = queryAfterDelete.uniqueResultOptional();
        Assertions.assertFalse(productsEntityAfterDelete.isPresent());

    }
}
