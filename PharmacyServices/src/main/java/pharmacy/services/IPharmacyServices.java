package pharmacy.services;

import pharmacy.model.Doctor;
import pharmacy.model.Medication;
import pharmacy.model.Pharmacist;
import pharmacy.model.User;

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
}
