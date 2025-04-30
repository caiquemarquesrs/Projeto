package com.example.projeto;

import com.example.projeto.DoacaoDAO;
import com.example.projeto.DoadorDAO;
import com.example.projeto.EntidadeDAO;
import com.example.projeto.Doacao;
import com.example.projeto.Doador;
import com.example.projeto.Entidade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class DoacaoController implements Initializable {
    @FXML
    private ComboBox<Doador> doadoComboBox;

    @FXML
    private ComboBox<Entidade> entidadeComboBox;

    @FXML
    private TextField itemField;

    @FXML
    private TextField quantityField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Doacao> doacaoTable;

    @FXML
    private TableColumn<Doacao, Integer> idColumn;

    @FXML
    private TableColumn<Doacao, Doador> doadorColumn;

    @FXML
    private TableColumn<Doacao, Entidade> entidadeColumn;

    @FXML
    private TableColumn<Doacao, String> itemColumn;

    @FXML
    private TableColumn<Doacao, Integer> quantityColumn;

    @FXML
    private TableColumn<Doacao, LocalDate> dateColumn;

    @FXML
    private ComboBox<String> filterTypeComboBox;

    @FXML
    private ComboBox<Object> filterValueComboBox;

    @FXML
    private TableView<Doacao> doacaoListTable;

    @FXML
    private TableColumn<Doacao, Integer> idListColumn;

    @FXML
    private TableColumn<Doacao, Doador> doadorListColumn;

    @FXML
    private TableColumn<Doacao, Entidade> entidadeListColumn;

    @FXML
    private TableColumn<Doacao, String> itemListColumn;

    @FXML
    private TableColumn<Doacao, Integer> quantityListColumn;

    @FXML
    private TableColumn<Doacao, LocalDate> dateListColumn;

    private DoacaoDAO doacaoDAO;
    private DoadorDAO doadorDAO;
    private EntidadeDAO entidadeDAO;
    private ObservableList<Doacao> doacaoList;
    private Doacao selectedDoacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doacaoDAO = new DoacaoDAO();
        doadorDAO = new DoadorDAO();
        entidadeDAO = new EntidadeDAO();
        if (datePicker != null) {
            datePicker.setValue(LocalDate.now());
        }
        loadDoadores();
        loadEntidades();
        if (doacaoTable != null) {
            configureMainTable();
        }
        if (doacaoListTable != null) {
            configureListTable();
            filterTypeComboBox.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            if (newValue.equals("Doador")) {
                                loadDonorsForFilter();
                                filterValueComboBox.setDisable(false);
                            } else if (newValue.equals("Instituição")) {
                                loadInstitutionsForFilter();
                                filterValueComboBox.setDisable(false);
                            } else {
                                filterValueComboBox.setDisable(true);
                                filterValueComboBox.getItems().clear();
                            }
                        }
                    });
        }
        loadDoadores();
    }

    private void configureMainTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        doadorColumn.setCellValueFactory(new PropertyValueFactory<>("doador"));
        entidadeColumn.setCellValueFactory(new PropertyValueFactory<>("entidade"));
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("doacaoDate"));
        doacaoTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectDoacao(newValue));
    }

    private void configureListTable() {
        idListColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        doadorListColumn.setCellValueFactory(new PropertyValueFactory<>("doador"));
        entidadeListColumn.setCellValueFactory(new PropertyValueFactory<>("entidade"));
        itemListColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        quantityListColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        dateListColumn.setCellValueFactory(new PropertyValueFactory<>("doacaoDate"));
    }

    private void loadDoadores() {
        if (doadoComboBox != null) {
            List<Doador> donors = doadorDAO.findAll();
            ObservableList<Doador> donorList = FXCollections.observableArrayList(donors);
            doadoComboBox.setItems(donorList);
        }
    }

    private void loadEntidades() {
        if (entidadeComboBox != null) {
            List<Entidade> institutions = entidadeDAO.findAll();
            ObservableList<Entidade> institutionList = FXCollections.observableArrayList(institutions);
            entidadeComboBox.setItems(institutionList);
        }
    }

    private void loadDonorsForFilter() {
        List<Doador> donors = doadorDAO.findAll();
        ObservableList<Object> donorList = FXCollections.observableArrayList(donors);
        filterValueComboBox.setItems(donorList);
    }

    private void loadInstitutionsForFilter() {
        List<Entidade> institutions = entidadeDAO.findAll();
        ObservableList<Object> institutionList = FXCollections.observableArrayList(institutions);
        filterValueComboBox.setItems(institutionList);
    }

    private void loadDoacao() {
        List<Doacao> donations = doacaoDAO.findAll();
        doacaoList = FXCollections.observableArrayList(donations);

        if (doacaoTable != null) {
            doacaoTable.setItems(doacaoList);
        }

        if (doacaoListTable != null) {
            doacaoListTable.setItems(doacaoList);
        }
    }

    private void selectDoacao(Doacao donation) {
        if (donation != null) {
            selectedDoacao = donation;
            doadoComboBox.setValue(donation.getDoador());
            entidadeComboBox.setValue(donation.getEntidade());
            itemField.setText(donation.getItem());
            quantityField.setText(String.valueOf(donation.getQuantity()));
            datePicker.setValue(donation.getDonationDate());
        }
    }

    @FXML
    private void handleSave() {
            Doador donor = doadoComboBox.getValue();
            Entidade institution = entidadeComboBox.getValue();
            String item = itemField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            LocalDate donationDate = datePicker.getValue();
            if (selectedDoacao == null) {
                Doacao donation = new Doacao(donor, institution, item, quantity, donationDate);
                doacaoDAO.insert(donation);
            } else {
                selectedDoacao.setDoador(donor);
                selectedDoacao.setEntidade(institution);
                selectedDoacao.setItem(item);
                selectedDoacao.setQuantity(quantity);
                selectedDoacao.setDonationDate(donationDate);
                doacaoDAO.update(selectedDoacao);
            }

            loadDoacao();
        }
    }
