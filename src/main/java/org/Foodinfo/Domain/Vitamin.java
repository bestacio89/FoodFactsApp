package org.Foodinfo.Domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Vitamins")
public class Vitamin {
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
        Vitamin vitamin = (Vitamin) o;
        return Objects.equals(name, vitamin.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
