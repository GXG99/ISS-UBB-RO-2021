package pharmacy.client.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pharmacy.model.Doctor;

public class DoctorController {
    private Doctor loggedDoctor;

    @FXML // fx:id="loggedDoctorLabel"
    private Label loggedDoctorLabel; // Value injected by FXMLLoader

    public void setDoctor(Doctor doctor) {
        this.loggedDoctor = doctor;
    }

    public void init() {
        loggedDoctorLabel.setText(loggedDoctor.getDoctorFullName().toUpperCase());
    }

    public void exitClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
