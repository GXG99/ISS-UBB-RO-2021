package pharmacy.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy.model.Doctor;
import pharmacy.model.Pharmacist;
import pharmacy.model.User;
import pharmacy.persistence.repository.jpa.DoctorRepository;
import pharmacy.persistence.repository.jpa.PharmacistRepository;
import pharmacy.persistence.repository.jpa.UserRepository;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

@Service
public class PharmacyServices implements IPharmacyServices {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private UserRepository userRepository;


    public PharmacyServices() {
    }

    @Override
    public String testString() {
        return "Hello MxG";
    }

    @Override
    public Pharmacist loginPharmacist(String email, String password) throws PharmacyException {
        return pharmacistRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Doctor loginDoctor(String email, String password) throws PharmacyException {
        return doctorRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User loginUser(String email, String password) throws PharmacyException {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
