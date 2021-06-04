package pharmacy.persistence.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pharmacy.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
