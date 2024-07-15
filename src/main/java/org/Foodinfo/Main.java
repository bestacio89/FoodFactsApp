import org.Foodinfo.Domain.Product;
import org.Foodinfo.Persistence.ProductRepository;
import org.Foodinfo.Service.ProductService;
import org.Foodinfo.Helpers.ParsingHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FoodinfoPersistenceUnit");
        EntityManager entityManager = emf.createEntityManager();

        try {
            ProductRepository productRepository = new ProductRepository(entityManager);
            ProductService productService = new ProductService(entityManager, productRepository);

            // Load products from CSV file (if available)
            String csvFileName = "products.csv";
            List<Product> productsFromCsv = ParsingHelper.parseCsvFile(csvFileName, new String[]{"category", "brand", "name", "nutritionScore"});
            if (!productsFromCsv.isEmpty()) {
                System.out.println("Saving products from CSV...");
                for (Product product : productsFromCsv) {
                    productService.saveProduct(product);
                }
            }

            // Interactive input from user
            boolean continueInput = true;
            Scanner scanner = new Scanner(System.in);
            while (continueInput) {
                System.out.println("\nEnter product details (or 'exit' to stop):");
                System.out.print("Category: ");
                String category = scanner.nextLine();
                if ("exit".equalsIgnoreCase(category.trim())) {
                    continueInput = false;
                    break;
                }
                System.out.print("Brand: ");
                String brand = scanner.nextLine();
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Nutrition Score (A/B/C/D/E/F): ");
                char nutritionScore = scanner.nextLine().toUpperCase().charAt(0);

                Product newProduct = new Product(category, brand, name, nutritionScore);
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
                List<String> allergensList = parseStringList(allergensInput);
                newProduct.setAllergensList(allergensList);

                // Save product
                System.out.println("\nSaving product...");
                Product savedProduct = productService.saveProduct(newProduct);
                System.out.println("Saved product: " + savedProduct);
            }

            // Display all products
            List<Product> allProducts = productService.findAllProducts();
            System.out.println("\nAll products:");
            for (Product product : allProducts) {
                System.out.println(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
            emf.close();
        }
    }


}
