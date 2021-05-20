package pharmacy.rmi.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.remoting.rmi.RmiServiceExporter;
import pharmacy.services.IPharmacyServices;

@SpringBootApplication
@ComponentScan("pharmacy")
@EntityScan("pharmacy")
public class PharmacyServerRMI {
    @Bean
    RmiServiceExporter exporter(IPharmacyServices implementation) {
        Class<IPharmacyServices> serviceInterface = IPharmacyServices.class;
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(serviceInterface);
        exporter.setService(implementation);
        exporter.setServiceName(serviceInterface.getSimpleName());
        exporter.setRegistryPort(1099);
        return exporter;
    }

    public static void main(String[] args) {
        SpringApplication.run(PharmacyServerRMI.class, args);
    }


}
