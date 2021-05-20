package pharmacy.client.gui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pharmacy.model.Doctor;
import pharmacy.model.Pharmacist;
import pharmacy.model.User;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

import java.io.IOException;

public class LoginController {

    private IPharmacyServices server;


    // Stage
    private double yOffset = 0;
    private double xOffset = 0;

    @FXML
    void handleViewChange(MouseEvent event) {
        if (event.getSource() == phLoginViewButton) pnPharmacist.toFront();
        if (event.getSource() == doctorLoginViewButton) pnDoctor.toFront();
        if (event.getSource() == userLoginViewButton) pnUser.toFront();
    }


    public void pharmacistLoginClick(MouseEvent mouseEvent) {
        System.out.println("Initializing pharmacist login sequence...");
        String email = emailPhTextField.getText();
        String password = passwdPhTextField.getText();
        try {
            System.out.printf("Email: %s | Password: %s%n", email, password);
            Pharmacist pharmacist = server.loginPharmacist(email, password);
            if (pharmacist != null) {
                Stage pharmacistStage = loadPharmacistView(pharmacist);
                pharmacistStage.show();
                ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (PharmacyException | IOException e) {
            e.printStackTrace();
        }
    }

    private Stage loadPharmacistView(Pharmacist pharmacist) throws IOException {
        FXMLLoader pharmacistLoader =
                new FXMLLoader(getClass().getClassLoader().getResource("fxml/PharmacistView.fxml"));
        Parent pharmacistParent = pharmacistLoader.load();
        PharmacistController pharmacistController = pharmacistLoader.getController();
        pharmacistController.setPharmacist(pharmacist);
        pharmacistController.init();
        Stage pharmacistStage = new Stage();
        pharmacistStage.setScene(new Scene(pharmacistParent));
        makeDraggable(pharmacistStage, pharmacistParent);
        pharmacistStage.initStyle(StageStyle.UNDECORATED);
        return pharmacistStage;
    }

    public void userLoginClick(MouseEvent mouseEvent) {

        System.out.println("Initializing user login sequence...");
        String email = emailUserTextField.getText();
        String password = passwdUserTextField.getText();
        try {
            System.out.printf("Email: %s | Password: %s%n", email, password);
            User user = server.loginUser(email, password);
            if (user != null) {
                Stage userStage = loadUserView(user);
                userStage.show();
                ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (PharmacyException | IOException e) {
            e.printStackTrace();
        }

    }

    private Stage loadUserView(User user) throws IOException {
        FXMLLoader userLoader =
                new FXMLLoader(getClass().getClassLoader().getResource("fxml/UserView.fxml"));
        Parent userParent = userLoader.load();
        UserController userController = userLoader.getController();
        userController.setUser(user);
        userController.init();
        Stage userStage = new Stage();
        userStage.setScene(new Scene(userParent));
        makeDraggable(userStage, userParent);
        userStage.initStyle(StageStyle.UNDECORATED);
        return userStage;
    }

    public void doctorLoginClick(MouseEvent mouseEvent) {
        System.out.println("Initializing doctor login sequence...");
        String email = emailDoctorTextField.getText();
        String password = passwdDoctorTextField.getText();
        try {

            System.out.printf("Email: %s | Password: %s%n", email, password);
            Doctor doctor = server.loginDoctor(email, password);
            if (doctor != null) {
                Stage doctorStage = loadDoctorView(doctor);
                doctorStage.show();
                ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (PharmacyException | IOException e) {
            e.printStackTrace();
        }
    }

    private Stage loadDoctorView(Doctor doctor) throws IOException {
        FXMLLoader doctorLoader =
                new FXMLLoader(getClass().getClassLoader().getResource("fxml/DoctorView.fxml"));
        Parent doctorParent = doctorLoader.load();
        DoctorController doctorController = doctorLoader.getController();
        doctorController.setDoctor(doctor);
        doctorController.init();
        Stage doctorStage = new Stage();
        doctorStage.setScene(new Scene(doctorParent));
        makeDraggable(doctorStage, doctorParent);
        doctorStage.initStyle(StageStyle.UNDECORATED);
        return doctorStage;
    }

    public void setServer(IPharmacyServices server) {
        this.server = server;
    }

    private void makeDraggable(Stage stage, Parent root) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }


    @FXML // fx:id="pnUser"
    private Pane pnUser; // Value injected by FXMLLoader

    @FXML // fx:id="emailUserTextField"
    private TextField emailUserTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwdUserTextField"
    private TextField passwdUserTextField; // Value injected by FXMLLoader

    @FXML // fx:id="userLoginButton"
    private JFXButton userLoginButton; // Value injected by FXMLLoader

    @FXML // fx:id="userRegisterButton"
    private JFXButton userRegisterButton; // Value injected by FXMLLoader

    @FXML // fx:id="pnPharmacist"
    private Pane pnPharmacist; // Value injected by FXMLLoader

    @FXML // fx:id="emailPhTextField"
    private TextField emailPhTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwdPhTextField"
    private TextField passwdPhTextField; // Value injected by FXMLLoader

    @FXML // fx:id="phLoginButton"
    private JFXButton phLoginButton; // Value injected by FXMLLoader

    @FXML // fx:id="phRegisterButton"
    private JFXButton phRegisterButton; // Value injected by FXMLLoader

    @FXML // fx:id="pnDoctor"
    private Pane pnDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="emailDoctorTextField"
    private TextField emailDoctorTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwdDoctorTextField"
    private TextField passwdDoctorTextField; // Value injected by FXMLLoader

    @FXML // fx:id="doctorLoginButton"
    private JFXButton doctorLoginButton; // Value injected by FXMLLoader

    @FXML // fx:id="doctorRegisterButton"
    private JFXButton doctorRegisterButton; // Value injected by FXMLLoader

    @FXML // fx:id="phLoginViewButton"
    private JFXButton phLoginViewButton; // Value injected by FXMLLoader

    @FXML // fx:id="doctorLoginViewButton"
    private JFXButton doctorLoginViewButton; // Value injected by FXMLLoader

    @FXML // fx:id="userLoginViewButton"
    private JFXButton userLoginViewButton; // Value injected by FXMLLoader


}
