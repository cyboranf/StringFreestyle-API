package com.example.StringFreestyleApi.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="generated_strings")
    private String generatedStrings;
    @Column(name = "generated_quantity")
    private Long generatedQuantity;
    @OneToMany
    @JoinColumn(name = "sign_id")
    private List<Sign> signList;

    public User() {
    }

    public User(String name, String generatedStrings, Long generatedQuantity, List<Sign> signList) {
        this.name = name;
        this.generatedStrings = generatedStrings;
        this.generatedQuantity = generatedQuantity;
        this.signList = signList;
    }

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

    public String getGeneratedStrings() {
        return generatedStrings;
    }

    public void setGeneratedStrings(String generatedStrings) {
        this.generatedStrings = generatedStrings;
    }

    public Long getGeneratedQuantity() {
        return generatedQuantity;
    }

    public void setGeneratedQuantity(Long generatedQuantity) {
        this.generatedQuantity = generatedQuantity;
    }

    public List<Sign> getSignList() {
        return signList;
    }

    public void setSignList(List<Sign> signList) {
        this.signList = signList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", generatedStrings='" + generatedStrings + '\'' +
                ", generatedQuantity=" + generatedQuantity +
                ", signList=" + signList +
                '}';
    }
}
