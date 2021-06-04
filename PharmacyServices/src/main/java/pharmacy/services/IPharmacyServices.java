package pharmacy.services;

import pharmacy.model.*;

import java.util.List;


public interface IPharmacyServices {

    String testString();

    Pharmacist loginPharmacist(String email, String password) throws PharmacyException;

    Doctor loginDoctor(String email, String password, IPharmacyObserver client) throws PharmacyException;

    User loginUser(String email, String password) throws PharmacyException;

    List<Medication> getTop20OrderByCommercialName() throws  PharmacyException;

    List<Medication> getMedicationsContainingCommercialName(String commercialName) throws PharmacyException;

    List<Medication> getAllMedications();

    Long getStocksById(Long id);

    void addOrder(Order order);

    List<Order> getAllOrders();

    Doctor getDoctorById(Long doctorId);

    void finalizeOrder(Order toFinalize);

    void saveSpecialtyMedication(SpecialtyMedication medication);

    Medication findMedicationById(Long id);

    SpecialtyMedication getSpecialtyMedicationById(Long id);

    List<SpecialtyMedication> getAllStocks();

    void cancerOrder(Order order);
}
