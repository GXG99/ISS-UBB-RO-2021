package pharmacy.persistence.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pharmacy.model.Pharmacist;

@Repository
public interface PharmacistRepository extends CrudRepository<Pharmacist, Long> {
    Pharmacist findByEmailAndPassword(String email, String password);
}
