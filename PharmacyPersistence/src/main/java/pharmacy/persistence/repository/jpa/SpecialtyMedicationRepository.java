package pharmacy.persistence.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pharmacy.model.SpecialtyMedication;

import java.util.List;

@Repository
public interface SpecialtyMedicationRepository extends CrudRepository<SpecialtyMedication, Long> {
    SpecialtyMedication findByMedId(Long id);
}
