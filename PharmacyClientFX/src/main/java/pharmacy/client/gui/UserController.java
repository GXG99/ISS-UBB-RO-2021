package pharmacy.client.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import pharmacy.model.User;

public class UserController {

    private User loggedUser;

    @FXML
    private Label loggedUserLabel;

    public void setUser(User user) {
        this.loggedUser = user;
    }

    public void init() {
        loggedUserLabel.setText(loggedUser.getUserFullName().toUpperCase());
    }

    public void exitClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
