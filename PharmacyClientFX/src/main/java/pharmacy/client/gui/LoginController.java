package pharmacy.client.gui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class LoginController {

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

    @FXML
    void handleViewChange(MouseEvent event) {
        if (event.getSource() == phLoginViewButton) pnPharmacist.toFront();
        if (event.getSource() == doctorLoginViewButton) pnDoctor.toFront();
        if (event.getSource() == userLoginViewButton) pnUser.toFront();
    }
}
