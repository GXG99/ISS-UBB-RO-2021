package pharmacy.persistence.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pharmacy.model.Medication;

import java.util.List;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Long> {
    @Query(value = "SELECT * FROM public.medications ORDER BY commercial_name LIMIT 20", nativeQuery = true)
    List<Medication> findTop20OrderByCommercialName();
    List<Medication> findByCommercialNameContaining(String commercialName);
    Medication getMedicationById(Long id);
}
