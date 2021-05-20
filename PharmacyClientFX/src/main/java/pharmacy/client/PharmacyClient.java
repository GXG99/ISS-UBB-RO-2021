package pharmacy.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pharmacy.client.gui.LoginController;
import pharmacy.services.IPharmacyServices;

import java.io.IOException;

public class PharmacyClient extends Application {

    // Dragging variables
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        IPharmacyServices server = getServer();
        // Initialize
        Scene loginScene = initializeLoginView(primaryStage, server);

        // Primary Stage initializers
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private IPharmacyServices getServer() {
        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        return (IPharmacyServices) factory.getBean("pharmacyService");
    }


    private Scene initializeLoginView(Stage primaryStage, IPharmacyServices server) throws IOException {
        // Load FXML
        FXMLLoader loginLoader = getLoginLoader();
        Parent loginRoot = loginLoader.load();

        // Get login controller
        LoginController loginController = loginLoader.getController();
        loginController.setServer(server);

        // Function to make UNDECORATED stage draggable
        makeDraggable(primaryStage, loginRoot);
        return new Scene(loginRoot);
    }

    private FXMLLoader getLoginLoader() {
        return new FXMLLoader(getClass().getClassLoader().getResource("fxml/LoginView.fxml"));
    }

    private void makeDraggable(Stage primaryStage, Parent loginRoot) {
        loginRoot.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        loginRoot.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }
}
