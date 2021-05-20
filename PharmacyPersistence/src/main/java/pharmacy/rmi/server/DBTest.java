package pharmacy.rmi.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pharmacy.model.Doctor;
import pharmacy.persistence.repository.jpa.DoctorRepository;
import pharmacy.persistence.repository.jpa.PharmacistRepository;
import pharmacy.persistence.repository.jpa.UserRepository;

@SpringBootApplication
@ComponentScan("pharmacy")
@EnableJpaRepositories("pharmacy")
@EntityScan("pharmacy")
public class DBTest {
    public static void main(String[] args) {
        SpringApplication.run(DBTest.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            PharmacistRepository pharmacistRepository
    ) {
        return args -> {
            Doctor doctor = doctorRepository.findByEmailAndPassword("maria_nastase@gmail.com", "parolaGXM");
            System.out.println(doctor.toString());
            userRepository.findAll().forEach(user -> System.out.println(user.toString()));
            pharmacistRepository.findAll().forEach(pharmacist -> System.out.println(pharmacist.toString()));
        };
    }
}
