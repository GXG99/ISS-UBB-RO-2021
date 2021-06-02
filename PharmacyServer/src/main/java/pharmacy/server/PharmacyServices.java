package pharmacy.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy.model.Doctor;
import pharmacy.model.Medication;
import pharmacy.model.Pharmacist;
import pharmacy.model.User;
import pharmacy.persistence.repository.jpa.DoctorRepository;
import pharmacy.persistence.repository.jpa.MedicationRepository;
import pharmacy.persistence.repository.jpa.PharmacistRepository;
import pharmacy.persistence.repository.jpa.UserRepository;
import pharmacy.services.IPharmacyObserver;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PharmacyServices implements IPharmacyServices {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PharmacistRepository pharmacistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MedicationRepository medicationRepository;

    private static final Logger logger = LogManager.getLogger();
    private Map<Long, IPharmacyObserver> loggedDoctors;

    public PharmacyServices() {
        loggedDoctors = new ConcurrentHashMap<>();
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
    public List<Medication> getTop20OrderByCommercialName() throws PharmacyException {
        return medicationRepository.findTop20OrderByCommercialName();
    }

    @Override
    public Doctor loginDoctor(String email, String password, IPharmacyObserver client) throws PharmacyException {
        logger.info("Entering doctor login sequence");
        Doctor doctor = doctorRepository.findByEmailAndPassword(email, password);
        logger.trace("Doctor: {}", doctor);
        if (doctor != null) {
            if (loggedDoctors.get(doctor.getId()) != null) {
                throw new PharmacyException("Doctor already logged in");
            }
            loggedDoctors.put(doctor.getId(), client);
        } else throw new PharmacyException("Authentication failed");
        return doctor;
    }

    @Override
    public User loginUser(String email, String password) throws PharmacyException {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Medication> getMedicationsContainingCommercialName(String commercialName) throws PharmacyException {
        return medicationRepository.findByCommercialNameContaining(commercialName);
    }

    @Override
    public List<Medication> getAllMedications() {
        return (List<Medication>) medicationRepository.findAll();
    }

    @Override
    public Long getStocksById(Long id) {
        return medicationRepository.getMedicationById(id).getStocks();
    }
}
