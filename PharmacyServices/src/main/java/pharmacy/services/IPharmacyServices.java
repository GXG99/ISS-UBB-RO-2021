package pharmacy.services;

import pharmacy.model.Doctor;
import pharmacy.model.Pharmacist;
import pharmacy.model.User;


public interface IPharmacyServices {

    String testString();

    Pharmacist loginPharmacist(String email, String password) throws PharmacyException;

    Doctor loginDoctor(String email, String password) throws PharmacyException;

    User loginUser(String email, String password) throws PharmacyException;
}
