package org.Foodinfo.Domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "allergens")
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allergen allergen = (Allergen) o;
        return Objects.equals(name, allergen.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
