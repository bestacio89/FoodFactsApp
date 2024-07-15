package org.Foodinfo.Service;

import org.Foodinfo.Domain.Product;
import org.Foodinfo.Persistence.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class ProductService {

    private final EntityManager entityManager;
    private final ProductRepository productRepository;

    public ProductService(EntityManager entityManager, ProductRepository productRepository) {
        this.entityManager = entityManager;
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            productRepository.save(product);
            transaction.commit();
            return product;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save product: " + e.getMessage());
        }
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product updatedProduct = productRepository.update(product);
            transaction.commit();
            return updatedProduct;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Product productToDelete = productRepository.findById(id);
            if (productToDelete != null) {
                productRepository.delete(productToDelete);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }

    // Add more business logic methods as needed
}
