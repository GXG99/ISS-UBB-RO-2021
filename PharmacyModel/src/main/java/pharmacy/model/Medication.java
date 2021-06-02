package pharmacy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "medications", schema = "public")
public class Medication implements Identifiable<Long>, Serializable {

    @Id
    @Column(name = "id_med")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "commercial_name")
    private String commercialName;
    @Column(name = "concentration")
    private String concentration;
    @Column(name = "therapeutic_effect")
    private String therapeuticEffect;
    @Column(name = "prescription")
    private String prescription;
    @Column(name = "shelf_life")
    private String shelfLife;
    @Column(name = "stocks")
    private Long stocks;

    public Medication(String commercialName, String concentration, String therapeuticEffect, String prescription, String shelfLife) {
        this.commercialName = commercialName;
        this.concentration = concentration;
        this.therapeuticEffect = therapeuticEffect;
        this.prescription = prescription;
        this.shelfLife = shelfLife;
    }

    public Medication() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getTherapeuticEffect() {
        return therapeuticEffect;
    }

    public void setTherapeuticEffect(String therapeuticEffect) {
        this.therapeuticEffect = therapeuticEffect;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Long getStocks() {
        return stocks;
    }

    public void setStocks(Long stocks) {
        this.stocks = stocks;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", commercialName='" + commercialName + '\'' +
                ", concentration='" + concentration + '\'' +
                ", therapeutic_effect='" + therapeuticEffect + '\'' +
                ", prescription='" + prescription + '\'' +
                ", shelf_life='" + shelfLife + '\'' +
                '}';
    }
}
