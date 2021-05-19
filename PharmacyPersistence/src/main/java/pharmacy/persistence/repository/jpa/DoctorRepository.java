package pharmacy.persistence.repository.jpa;

import pharmacy.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByEmailAndPassword(String email, String password);
}
