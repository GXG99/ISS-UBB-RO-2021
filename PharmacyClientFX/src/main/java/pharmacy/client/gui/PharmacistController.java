package pharmacy.client.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pharmacy.model.Pharmacist;

public class PharmacistController {

    private Pharmacist loggedPharmacist;

    @FXML
    private Label loggedPharmacistLabel;

    public void setPharmacist(Pharmacist pharmacist) {
        this.loggedPharmacist = pharmacist;
    }

    public void init() {
        loggedPharmacistLabel.setText(loggedPharmacist.getPharmacistFullName().toUpperCase());
    }

    public void exitClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
