package com.example.projeto;

import com.example.projeto.DoadorDAO;
import com.example.projeto.Doador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DoadorController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Doador> doadorTable;

    @FXML
    private TableColumn<Doador, Integer> idColumn;

    @FXML
    private TableColumn<Doador, String> nameColumn;

    @FXML
    private TableColumn<Doador, String> phoneColumn;

    @FXML
    private TableColumn<Doador, String> emailColumn;

    private DoadorDAO doadorDAO;

    private ObservableList<Doador> doadorList;

    private Doador selectedDoador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doadorDAO = new DoadorDAO();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        loadDoador();
        doadorTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectDoador(newValue));
    }

    private void loadDoador() {
        List<Doador> doadores = doadorDAO.findAll();
        doadorList = FXCollections.observableArrayList(doadores);
        doadorTable.setItems(doadorList);
    }

    private void selectDoador(Doador doador) {
        if (doador != null) {
            selectedDoador = doador;
            nameField.setText(doador.getName());
            phoneField.setText(doador.getPhone());
            emailField.setText(doador.getEmail());
        }
    }

    @FXML
    private void handleSave() {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (selectedDoador == null) {
                Doador doador = new Doador(name, phone, email);
                doadorDAO.insert(doador);
            } else {
                selectedDoador.setName(name);
                selectedDoador.setPhone(phone);
                selectedDoador.setEmail(email);
                doadorDAO.update(selectedDoador);
            }
            loadDoador();
        }
    }



