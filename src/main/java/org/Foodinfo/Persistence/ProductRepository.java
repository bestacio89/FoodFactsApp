package org.Foodinfo.Persistence;

import org.Foodinfo.Domain.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductRepository {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Product product) {
        entityManager.persist(product);
    }

    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    public Product findByName(String name) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class);
        query.setParameter("name", name);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product update(Product product) {
        return entityManager.merge(product);
    }

    public void delete(Product product) {
        entityManager.remove(entityManager.contains(product) ? product : entityManager.merge(product));
    }

    public List<Product> findByCategory(String category) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :category", Product.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    public List<Product> findByCategoryAndBrand(String category, String brand) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.category = :category AND p.brand = :brand", Product.class);
        query.setParameter("category", category);
        query.setParameter("brand", brand);
        return query.getResultList();
    }

    public List<Product> findByAllergen(String allergen) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT DISTINCT p FROM Product p JOIN p.allergensList a WHERE a = :allergen", Product.class);
        query.setParameter("allergen", allergen);
        return query.getResultList();
    }

    public List<Product> findByAdditive(String additive) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT DISTINCT p FROM Product p JOIN p.additivesList a WHERE a = :additive", Product.class);
        query.setParameter("additive", additive);
        return query.getResultList();
    }
}
