package pharmacy.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "specialtymeds", schema = "public")
public class SpecialtyMedication implements Identifiable<Long>, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_med")
    private Long medId;
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
    @Column(name = "doctor_id")
    private Long doctorId;

    public SpecialtyMedication(Long medId, String commercialName, String concentration, String therapeuticEffect, String prescription, String shelfLife) {
        this.medId = medId;
        this.commercialName = commercialName;
        this.concentration = concentration;
        this.therapeuticEffect = therapeuticEffect;
        this.prescription = prescription;
        this.shelfLife = shelfLife;
    }

    public SpecialtyMedication() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedId() {
        return medId;
    }

    public void setMedId(Long medId) {
        this.medId = medId;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SpecialtyMedication{" +
                "id=" + id +
                ", medId=" + medId +
                ", commercialName='" + commercialName + '\'' +
                ", concentration='" + concentration + '\'' +
                ", therapeuticEffect='" + therapeuticEffect + '\'' +
                ", prescription='" + prescription + '\'' +
                ", shelfLife='" + shelfLife + '\'' +
                ", stocks=" + stocks +
                ", doctorId=" + doctorId +
                '}';
    }
}
