package org.Foodinfo.Domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String brand;

    private String name;

    @Column(name = "nutrition_score")
    private char nutritionScore;

    @ElementCollection
    @CollectionTable(name = "product_ingredients", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "ingredient")
    private List<String> ingredientsList;

    @Column(name = "energy_per_100g")
    private int energyPer100g;

    @Column(name = "fat_quantity_per_100g")
    private int fatQuantityPer100g;

    @ElementCollection
    @CollectionTable(name = "product_allergens", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "allergen")
    private List<String> allergensList;

    @ElementCollection
    @CollectionTable(name = "product_additives", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "additive")
    private List<String> additivesList;

    @Column(name = "has_palm_oil")
    private boolean hasPalmOil;

    @ElementCollection
    @CollectionTable(name = "product_vitamins", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "vitamin")
    private List<String> vitamins;

    @ElementCollection
    @CollectionTable(name = "product_minerals", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "mineral")
    private List<String> minerals;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getNutritionScore() {
        return nutritionScore;
    }

    public void setNutritionScore(char nutritionScore) {
        this.nutritionScore = nutritionScore;
    }

    public List<String> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<String> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getEnergyPer100g() {
        return energyPer100g;
    }

    public void setEnergyPer100g(int energyPer100g) {
        this.energyPer100g = energyPer100g;
    }

    public int getFatQuantityPer100g() {
        return fatQuantityPer100g;
    }

    public void setFatQuantityPer100g(int fatQuantityPer100g) {
        this.fatQuantityPer100g = fatQuantityPer100g;
    }

    public List<String> getAllergensList() {
        return allergensList;
    }

    public void setAllergensList(List<String> allergensList) {
        this.allergensList = allergensList;
    }

    public List<String> getAdditivesList() {
        return additivesList;
    }

    public void setAdditivesList(List<String> additivesList) {
        this.additivesList = additivesList;
    }

    public boolean getHasPalmOil() {
        return hasPalmOil;
    }

    public void setHasPalmOil(boolean hasPalmOil) {
        this.hasPalmOil = hasPalmOil;
    }

    public List<String> getVitamins() {
        return vitamins;
    }

    public void setVitamins(List<String> vitamins) {
        this.vitamins = vitamins;
    }

    public List<String> getMinerals() {
        return minerals;
    }

    public void setMinerals(List<String> minerals) {
        this.minerals = minerals;
    }
}
