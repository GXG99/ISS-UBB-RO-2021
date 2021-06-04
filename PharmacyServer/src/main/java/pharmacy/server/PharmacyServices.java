package pharmacy.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacy.model.*;
import pharmacy.persistence.repository.jpa.*;
import pharmacy.services.IPharmacyObserver;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SpecialtyMedicationRepository specialtyMedicationRepository;

    private static final Logger logger = LogManager.getLogger();
    private final Map<Long, IPharmacyObserver> loggedDoctors;

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
    public synchronized Doctor loginDoctor(String email, String password, IPharmacyObserver client) throws PharmacyException {
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

    @Override
    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        if (doctorRepository.findById(doctorId).isPresent())
            return doctorRepository.findById(doctorId).get();
        return null;
    }

    private final int defaultThreadNo = 5;

    private void notifyDoctorOrderFinalized(Order order) {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadNo);
        for (Doctor doctor : doctors) {
            IPharmacyObserver doctorClient = loggedDoctors.get(doctor.getId());
            if (doctorClient != null) {
                executor.execute(() -> {
                    try {
                        if (order.getDoctorId().equals(doctor.getId())) {
                            System.out.printf("Notifying [%s] order [%s] added%n",
                                    doctor.getId().toString(),
                                    order.getId().toString());
                            doctorClient.orderFinished(order);
                        }
                    } catch (RemoteException | PharmacyException e) {
                        System.err.println("Error notifying doctor " + e);
                    }
                });
            }
        }
        executor.shutdown();
    }

    @Override
    public void finalizeOrder(Order toFinalize) {
        orderRepository.save(toFinalize);
        notifyDoctorOrderFinalized(toFinalize);
    }

    @Override
    public void saveSpecialtyMedication(SpecialtyMedication medication) {
        specialtyMedicationRepository.save(medication);
    }

    @Override
    public Medication findMedicationById(Long id) {
        if (medicationRepository.findById(id).isPresent())
            return medicationRepository.findById(id).get();
        return null;
    }

    @Override
    public SpecialtyMedication getSpecialtyMedicationById(Long id) {
        return specialtyMedicationRepository.findByMedId(id);
    }

    @Override
    public List<SpecialtyMedication> getAllStocks() {
        return (List<SpecialtyMedication>) specialtyMedicationRepository.findAll();
    }

    @Override
    public void cancerOrder(Order order) {
        orderRepository.delete(order);
    }
}
