package org.Foodinfo;

import org.Foodinfo.Domain.Product;
import org.Foodinfo.Persistence.ProductRepository;
import org.Foodinfo.Service.ProductService;
import org.Foodinfo.Helpers.ParsingHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FoodinfoPersistenceUnit");
        EntityManager entityManager = emf.createEntityManager();

        try {
            ProductRepository productRepository = new ProductRepository();
            productRepository.setEntityManager(entityManager);
            ProductService productService = new ProductService(entityManager, productRepository);

            // Load products from CSV file (if available)
            String csvFileName = "products.csv";
            List<Product> productsFromCsv = ParsingHelper.parseCsvFile(csvFileName, new String[]{"category", "brand", "name", "nutritionScore"});
            if (!productsFromCsv.isEmpty()) {
                System.out.println("Saving products from CSV...");
                for (Product product : productsFromCsv) {
                    if (productService.findProductByName(product.getName()) == null) {
                        productService.saveProduct(product);
                    }
                }
            }

            // Interactive input from user
            boolean continueInput = true;
            Scanner scanner = new Scanner(System.in);
            while (continueInput) {
                System.out.println("\nMenu:");
                System.out.println("1. Enter product details");
                System.out.println("2. Find best product in category");
                System.out.println("3. Find products by category and brand");
                System.out.println("4. Show most common allergens");
                System.out.println("5. Show products by allergen");
                System.out.println("6. Show most common additives");
                System.out.println("7. Show products by additive");
                System.out.println("8. Show all products");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Category: ");
                        String category = scanner.nextLine();
                        System.out.print("Brand: ");
                        String brand = scanner.nextLine();
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Nutrition Score (A/B/C/D/E/F): ");
                        char nutritionScore = scanner.nextLine().toUpperCase().charAt(0);

                        Product newProduct = new Product();
                        newProduct.setCategory(category);
                        newProduct.setBrand(brand);
                        newProduct.setName(name);
                        newProduct.setNutritionScore(nutritionScore);

                        System.out.print("Energy per 100g: ");
                        int energyPer100g = scanner.nextInt();
                        newProduct.setEnergyPer100g(energyPer100g);

                        System.out.print("Fat quantity per 100g: ");
                        int fatQuantityPer100g = scanner.nextInt();
                        newProduct.setFatQuantityPer100g(fatQuantityPer100g);

                        System.out.print("Has Palm Oil (true/false): ");
                        boolean hasPalmOil = scanner.nextBoolean();
                        newProduct.setHasPalmOil(hasPalmOil);

                        // Consume newline character left by nextInt() and nextBoolean()
                        scanner.nextLine();

                        System.out.print("Allergens (comma-separated list): ");
                        String allergensInput = scanner.nextLine();
                        newProduct.setAllergensList(List.of(allergensInput.split(",")));

                        System.out.print("Additives (comma-separated list): ");
                        String additivesInput = scanner.nextLine();
                        newProduct.setAdditivesList(List.of(additivesInput.split(",")));

                        // Save product
                        System.out.println("\nSaving product...");
                        Product savedProduct = productService.saveProduct(newProduct);
                        System.out.println("Saved product: " + savedProduct);
                        break;
                    case 2:
                        System.out.print("Enter category: ");
                        String bestCategory = scanner.nextLine();
                        Product bestProduct = productService.findBestProductInCategory(bestCategory);
                        if (bestProduct != null) {
                            System.out.println("Best product in category " + bestCategory + ": " + bestProduct);
                        } else {
                            System.out.println("No products found in category " + bestCategory);
                        }
                        break;
                    case 3:
                        System.out.print("Enter category: ");
                        String prodCategory = scanner.nextLine();
                        System.out.print("Enter brand: ");
                        String prodBrand = scanner.nextLine();
                        List<Product> productsByCatAndBrand = productService.findProductsByCategoryAndBrand(prodCategory, prodBrand);
                        if (!productsByCatAndBrand.isEmpty()) {
                            productsByCatAndBrand.forEach(System.out::println);
                        } else {
                            System.out.println("No products found for category " + prodCategory + " and brand " + prodBrand);
                        }
                        break;
                    case 4:
                        Map<String, Long> commonAllergens = productService.findMostCommonAllergens();
                        System.out.println("Most common allergens:");
                        commonAllergens.forEach((allergen, count) -> System.out.println(allergen + ": " + count + " products"));
                        break;
                    case 5:
                        System.out.print("Enter allergen: ");
                        String allergen = scanner.nextLine();
                        List<Product> productsByAllergen = productService.findProductsByAllergen(allergen);
                        if (!productsByAllergen.isEmpty()) {
                            productsByAllergen.forEach(System.out::println);
                        } else {
                            System.out.println("No products found containing allergen " + allergen);
                        }
                        break;
                    case 6:
                        Map<String, Long> commonAdditives = productService.findMostCommonAdditives();
                        System.out.println("Most common additives:");
                        commonAdditives.forEach((additive, count) -> System.out.println(additive + ": " + count + " products"));
                        break;
                    case 7:
                        System.out.print("Enter additive: ");
                        String additive = scanner.nextLine();
                        List<Product> productsByAdditive = productService.findProductsByAdditive(additive);
                        if (!productsByAdditive.isEmpty()) {
                            productsByAdditive.forEach(System.out::println);
                        } else {
                            System.out.println("No products found containing additive " + additive);
                        }
                        break;
                    case 8:
                        List<Product> allProducts = productService.findAllProducts();
                        System.out.println("All products:");
                        allProducts.forEach(System.out::println);
                        break;
                    case 9:
                        continueInput = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
            emf.close();
        }
    }
}
