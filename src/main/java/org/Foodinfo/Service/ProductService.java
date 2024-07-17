package org.Foodinfo.Service;

import org.Foodinfo.Domain.Product;
import org.Foodinfo.Persistence.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Product findProductByName(String name) {
        return productRepository.findByName(name);
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

    public Product findBestProductInCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .min((p1, p2) -> Character.compare(p1.getNutritionScore(), p2.getNutritionScore()))
                .orElse(null);
    }

    public List<Product> findProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category, brand);
    }

    public Map<String, Long> findMostCommonAllergens() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .flatMap(product -> product.getAllergensList().stream())
                .collect(Collectors.groupingBy(allergen -> allergen, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Product> findProductsByAllergen(String allergen) {
        return productRepository.findByAllergen(allergen);
    }

    public Map<String, Long> findMostCommonAdditives() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .flatMap(product -> product.getAdditivesList().stream())
                .collect(Collectors.groupingBy(additive -> additive, Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Product> findProductsByAdditive(String additive) {
        return productRepository.findByAdditive(additive);
    }
}
