package pharmacy.client.gui;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import pharmacy.model.Doctor;
import pharmacy.model.Medication;
import pharmacy.model.Order;
import pharmacy.model.Pharmacist;
import pharmacy.services.IPharmacyObserver;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.function.Predicate;

public class PharmacistController extends UnicastRemoteObject implements IPharmacyObserver, Serializable {

    private Pharmacist loggedPharmacist;
    private ObservableList<Order> orders;

    // Server
    private IPharmacyServices server;

    public PharmacistController() throws RemoteException {
    }

    public void setServer(IPharmacyServices server) {
        this.server = server;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.loggedPharmacist = pharmacist;
    }


    public void exitClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }

    @FXML
    private Label loggedPharmacistLabel;
    @FXML
    private Pane pnStocks;
    @FXML
    private Pane pnOrders;
    @FXML
    private JFXTextField searchMedsTextField;
    @FXML
    private TableView<Medication> tableMeds;
    @FXML
    private TableColumn<?, ?> idMedColumn;
    @FXML
    private TableColumn<?, ?> commercialColumn;
    @FXML
    private TableColumn<?, ?> concentrationColumn;
    @FXML
    private TableColumn<?, ?> therapeuticColumn;
    @FXML
    private TableColumn<?, ?> prescriptionColumn;
    @FXML
    private TableColumn<?, ?> shelfLifeColumn;
    @FXML
    private Button stocksButton;
    @FXML
    private Button ordersButton;
    @FXML
    private Label stocksLabel;
    @FXML
    private TableView<Order> tableOrders;
    @FXML
    private Label doctorOrderLabel;
    @FXML
    private TableColumn<?, ?> idOrderColumn;

    @FXML
    private TableColumn<?, ?> medsIdsColumn;

    @FXML
    private TableColumn<?, ?> doctorIdColumn;

    @FXML
    private TableColumn<?, ?> priorityColumn;


    private Predicate<Medication> createPredicate(String searchText) {
        return medication -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsMedication(medication, searchText);
        };
    }

    private boolean searchFindsMedication(Medication medication, String searchText) {
        return medication.getCommercialName().contains(searchText.toUpperCase());
    }

    public void onTableClicked() {
        Long id = tableMeds.getSelectionModel().getSelectedItem().getId();
        stocksLabel.setText("Stocuri: " + server.getStocksById(id));
    }

    public void init() {
        loggedPharmacistLabel.setText(loggedPharmacist.getPharmacistFullName().toUpperCase());

        initializeMedsTableColumns();

        initializeOrderTableColumns();

        ObservableList<Medication> medications = FXCollections.observableList(server.getAllMedications());

        FilteredList<Medication> filteredData = new FilteredList<>(FXCollections.observableList(medications));

        searchMedsTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        tableMeds.setItems(filteredData);
        refreshOrderTable();
    }

    private void refreshOrderTable() {
        orders = FXCollections.observableArrayList(server.getAllOrders()).filtered(order -> !order.getCompleted());
        tableOrders.setItems(orders);
    }

    private void initializeOrderTableColumns() {
        idOrderColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        medsIdsColumn.setCellValueFactory(new PropertyValueFactory<>("medsIds"));
        doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("urgent"));
    }

    private void initializeMedsTableColumns() {
        initializeColumns(idMedColumn, commercialColumn, concentrationColumn, therapeuticColumn, prescriptionColumn, shelfLifeColumn);
    }

    private static void initializeColumns(TableColumn<?, ?> idMedColumn, TableColumn<?, ?> commercialColumn, TableColumn<?, ?> concentrationColumn, TableColumn<?, ?> therapeuticColumn, TableColumn<?, ?> prescriptionColumn, TableColumn<?, ?> shelfLifeColumn) {
        idMedColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        commercialColumn.setCellValueFactory(new PropertyValueFactory<>("commercialName"));
        concentrationColumn.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        therapeuticColumn.setCellValueFactory(new PropertyValueFactory<>("therapeuticEffect"));
        prescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("prescription"));
        shelfLifeColumn.setCellValueFactory(new PropertyValueFactory<>("shelfLife"));
    }

    public void handleViewChange(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == ordersButton) {
            refreshOrderTable();
            pnOrders.toFront();
        }
        if (mouseEvent.getSource() == stocksButton) {
            refreshOrderTable();
            pnStocks.toFront();
        }
    }

    public void onTableOrdersClick(MouseEvent mouseEvent) {
        Order order = tableOrders.getSelectionModel().getSelectedItem();
        Doctor orderDoctor = server.getDoctorById(order.getDoctorId());
        doctorOrderLabel.setText("Comanda efectuata de Dr. " + orderDoctor.getDoctorFullName());
    }


    public void finalizeOrderClick(MouseEvent mouseEvent) {
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            Order toFinalize = tableOrders.getSelectionModel().getSelectedItem();
            toFinalize.setCompleted(true);
            server.finalizeOrder(toFinalize);
            refreshOrderTable();
            showFinalizedOrderAlert();
        }
    }

    private void showFinalizedOrderAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Comanda a fost onorata cu succes!");
        alert.setTitle("Comanda onorata");
        alert.show();
    }

    @Override
    public void orderFinished(Order order) throws PharmacyException, RemoteException {

    }

    public void cancelOrderClick(MouseEvent mouseEvent) {
        if (tableOrders.getSelectionModel().getSelectedItem() != null) {
            Order toCancel = tableOrders.getSelectionModel().getSelectedItem();
            server.cancerOrder(toCancel);
            refreshOrderTable();
        }
    }
}
