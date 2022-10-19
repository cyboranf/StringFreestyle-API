package com.example.StringFreestyleApi.domain;

import javax.persistence.*;

@Entity
@Table(name = "sign")
public class Sign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "elements")
    private String elementsOfString;

    @Column(name = "wantQuantity")
    private long wantQuantity;

    @Column(name = "maxQuantity")
    private long maxQuantity;

    @Column(name = "minSize")
    private long minSizeString;

    @Column(name = "maxSize")
    private long maxSizeString;



    public Sign(String elementsOfString, long wantQuantity, long maxQuantity, long minSizeString, long maxSizeString) {
        this.elementsOfString = elementsOfString;
        this.wantQuantity = wantQuantity;
        this.maxQuantity = maxQuantity;
        this.minSizeString = minSizeString;
        this.maxSizeString = maxSizeString;
    }

    public Sign(String elementsOfString, long wantQuantity, long maxQuantity) {
        this.elementsOfString = elementsOfString;
        this.wantQuantity = wantQuantity;
        this.maxQuantity = maxQuantity;
    }

    public Sign() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getElementsOfString() {
        return elementsOfString;
    }

    public void setElementsOfString(String elementsOfString) {
        this.elementsOfString = elementsOfString;
    }

    public long getWantQuantity() {
        return wantQuantity;
    }

    public void setWantQuantity(long wantQuantity) {
        this.wantQuantity = wantQuantity;
    }

    public long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public long getMinSizeString() {
        return minSizeString;
    }

    public void setMinSizeString(long minSizeString) {
        this.minSizeString = minSizeString;
    }

    public long getMaxSizeString() {
        return maxSizeString;
    }

    public void setMaxSizeString(long maxSizeString) {
        this.maxSizeString = maxSizeString;
    }

    @Override
    public String toString() {
        return "Sign{" +
                "id=" + id +
                ", elementsOfString='" + elementsOfString + '\'' +
                ", wantQuantity=" + wantQuantity +
                ", maxQuantity=" + maxQuantity +
                ", minSizeString=" + minSizeString +
                ", maxSizeString=" + maxSizeString +
                '}';
    }
}
