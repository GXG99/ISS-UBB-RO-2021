package pharmacy.client.gui;

import com.jfoenix.controls.JFXButton;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pharmacy.model.Doctor;
import pharmacy.model.Medication;
import pharmacy.model.Order;
import pharmacy.model.SpecialtyMedication;
import pharmacy.services.IPharmacyObserver;
import pharmacy.services.IPharmacyServices;
import pharmacy.services.PharmacyException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class DoctorController extends UnicastRemoteObject implements IPharmacyObserver, Serializable {
    private Doctor loggedDoctor;
    @FXML
    private Label loggedDoctorLabel;
    private ObservableList<SpecialtyMedication> stocks;

    // Server
    private IPharmacyServices server;

    private static final Logger logger = LogManager.getLogger();

    private final ObservableList<Medication> orderList = FXCollections.observableArrayList();

    public DoctorController() throws RemoteException {
    }

    /*
     *
     *  ORDER FUNCTIONS
     *
     */
    public void sendToOrderClick() {
        List<Long> medsIds = new ArrayList<>();
        Long doctorId = loggedDoctor.getId();
        tableMedsOrders.getItems().forEach(medication -> {
            medsIds.add(medication.getId());
        });
        Order order = new Order(medsIds, doctorId);
        server.addOrder(order);
        alertOrderSent();
        tableMedsOrders.getItems().clear();
    }

    private void alertOrderSent() {
        Alert orderSentSuccessfully = new Alert(Alert.AlertType.CONFIRMATION,
                "Comanda a fost trimisa cu succes");
        orderSentSuccessfully.setTitle("Comanda trimisa");
        orderSentSuccessfully.show();
    }

    public void addToOrdersClick() {
        if (tableMeds.getSelectionModel().getSelectedItem() != null) {
            Integer quantity = Integer.parseInt(quantityTextField.getText());
            if (quantity > 0) {
                Medication selectedMedication = tableMeds.getSelectionModel().getSelectedItem();
                logger.trace(selectedMedication);
                while (quantity > 0) {
                    orderList.add(selectedMedication);
                    tableMedsOrders.setItems(orderList);
                    quantity--;
                }
                alertMedicationAdded(selectedMedication);
            }
        }
    }

    private void alertMedicationAdded(Medication selectedMedication) {
        Alert addedAlert = new Alert(Alert.AlertType.CONFIRMATION,
                selectedMedication.getCommercialName() + " added to orders!");
        addedAlert.setTitle("Added to orders");
        addedAlert.show();
    }


    /*
     *
     *  MEDS PANE
     *
     */
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


    /*
     *
     *  HANDLE PANE CHANGE
     *
     */
    public void handleViewChange(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == orderButton) pnOrders.toFront();
        if (mouseEvent.getSource() == stocksButton) pnStock.toFront();
        if (mouseEvent.getSource() == medicationsButton) pnMedications.toFront();
    }

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
    private JFXTextField searchMedsTextField;
    @FXML
    private Pane pnOrders;
    @FXML
    private Pane pnMedications;
    @FXML
    private Pane pnStock;
    @FXML
    private Button orderButton;
    @FXML
    private Button stocksButton;
    @FXML
    private Button medicationsButton;
    @FXML
    private Label stocksLabel;
    @FXML
    private JFXButton removeFromOrdersButton;
    @FXML
    private JFXButton sendOrderButton;
    @FXML
    private JFXButton addToOrdersButton;
    @FXML
    private TableView<Medication> tableMedsOrders;
    @FXML
    private TableColumn<?, ?> idMedOrdersColumn;
    @FXML
    private TableColumn<?, ?> commercialOrdersColumn;
    @FXML
    private TableColumn<?, ?> concentrationOrdersColumn;
    @FXML
    private TableColumn<?, ?> therapeuticOrdersColumn;
    @FXML
    private TableColumn<?, ?> prescriptionOrdersColumn;
    @FXML
    private TableColumn<?, ?> shelfLifeOrdersColumn;
    @FXML
    private JFXTextField quantityTextField;
    @FXML
    private TableView<SpecialtyMedication> tableStocks;

    @FXML
    private TableColumn<?, ?> idStocksColumn;

    @FXML
    private TableColumn<?, ?> comNameStocksColumn;

    @FXML
    private TableColumn<?, ?> conStocksColumn;

    @FXML
    private TableColumn<?, ?> tEffectStocksColumn;

    @FXML
    private TableColumn<?, ?> prescriptionStocksColumn;

    @FXML
    private TableColumn<?, ?> shelfLStocksColumn;

    @FXML
    private TableColumn<?, ?> noStocksColumn;

    public void setServer(IPharmacyServices server) {
        this.server = server;
    }

    public void setDoctor(Doctor doctor) {
        this.loggedDoctor = doctor;
    }


    public void removeFromOrdersClick(MouseEvent mouseEvent) {
    }

    public void onTableOrdersClick(MouseEvent mouseEvent) {
    }

    /*
     *
     *  INITIALIZING METHODS
     *
     */
    public void init() {
        loggedDoctorLabel.setText(loggedDoctor.getDoctorFullName().toUpperCase());

        initializeMedsTableColumns();
        initializeMedsTableOrdersColumns();
        initializeStocksTableColumns();

        refreshStocksTable();

        ObservableList<Medication> medications = FXCollections.observableList(server.getAllMedications());

        FilteredList<Medication> filteredData = new FilteredList<>(FXCollections.observableList(medications));

        searchMedsTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        tableMeds.setItems(filteredData);
    }

    private void refreshStocksTable() {
        stocks = FXCollections.observableArrayList(server.getAllStocks()).filtered(stock -> stock.getDoctorId().equals(loggedDoctor.getId()));
        tableStocks.setItems(stocks);
    }

    private void initializeStocksTableColumns() {
        idStocksColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        comNameStocksColumn.setCellValueFactory(new PropertyValueFactory<>("commercialName"));
        conStocksColumn.setCellValueFactory(new PropertyValueFactory<>("concentration"));
        tEffectStocksColumn.setCellValueFactory(new PropertyValueFactory<>("therapeuticEffect"));
        prescriptionStocksColumn.setCellValueFactory(new PropertyValueFactory<>("prescription"));
        shelfLStocksColumn.setCellValueFactory(new PropertyValueFactory<>("shelfLife"));
        noStocksColumn.setCellValueFactory(new PropertyValueFactory<>("stocks"));
    }

    private void initializeMedsTableOrdersColumns() {
        initializeColumns(idMedOrdersColumn, commercialOrdersColumn, concentrationOrdersColumn, therapeuticOrdersColumn, prescriptionOrdersColumn, shelfLifeOrdersColumn);
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

    public void exitClicked(MouseEvent mouseEvent) {
        Platform.exit();
    }

    @Override
    public void orderFinished(Order order) throws PharmacyException, RemoteException {
        List<Long> medsIds = order.getMedsIds();
        for (Long medsId : medsIds) {
            Medication medication = server.findMedicationById(medsId);
            SpecialtyMedication specialtyMedication = new SpecialtyMedication(
                    medication.getId(),
                    medication.getCommercialName(),
                    medication.getConcentration(),
                    medication.getTherapeuticEffect(),
                    medication.getPrescription(),
                    medication.getShelfLife()
            );
            System.out.println(medication.toString());
            SpecialtyMedication specialtyMedicationById = server.getSpecialtyMedicationById(medication.getId());
            if (specialtyMedicationById != null) {
                Long newValue = specialtyMedicationById.getStocks() + 1L;
                specialtyMedicationById.setStocks(newValue);
                server.saveSpecialtyMedication(specialtyMedicationById);
            } else {
                specialtyMedication.setStocks(1L);
                specialtyMedication.setDoctorId(order.getDoctorId());
                System.out.println(specialtyMedication.toString());
                server.saveSpecialtyMedication(specialtyMedication);
            }
        }
        refreshStocksTable();
    }
}
