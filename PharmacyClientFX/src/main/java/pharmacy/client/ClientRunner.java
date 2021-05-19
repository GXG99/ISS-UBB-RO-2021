package pharmacy.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ClientRunner extends Application {

    // Dragging variables
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize
        Scene loginScene = initializeLoginView(primaryStage);

        // Primary Stage initializers
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private Scene initializeLoginView(Stage primaryStage) throws IOException {
        // Load FXML
        FXMLLoader loginLoader = getLoginLoader();
        Parent loginRoot = loginLoader.load();

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
