package pharmacy.persistence.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import pharmacy.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
}
