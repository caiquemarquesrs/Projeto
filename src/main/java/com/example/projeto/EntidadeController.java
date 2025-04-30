package com.example.projeto;

import com.example.projeto.EntidadeDAO;
import com.example.projeto.Entidade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EntidadeController implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    private TextField cnpjField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableView<Entidade> entidadeTable;

    @FXML
    private TableColumn<Entidade, Integer> idColumn;

    @FXML
    private TableColumn<Entidade, String> nameColumn;

    @FXML
    private TableColumn<Entidade, String> cnpjColumn;

    @FXML
    private TableColumn<Entidade, String> typeColumn;

    private EntidadeDAO entidadeDAO;
    private ObservableList<Entidade> entidadeList;
    private Entidade selectedEntidade;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        entidadeDAO = new EntidadeDAO();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cnpjColumn.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ObservableList<String> types = FXCollections.observableArrayList(
                "Abrigo", "ONG", "Escola", "Hospital", "Asilo", "Outro"
        );
        typeComboBox.setItems(types);
        loadEntidades();
        entidadeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectEntidades(newValue));
    }

    private void loadEntidades() {
        List<Entidade> entidades = entidadeDAO.findAll();
        entidadeList = FXCollections.observableArrayList(entidades);
        entidadeTable.setItems(entidadeList);
    }

    private void selectEntidades(Entidade entidade) {
        if (entidade != null) {
            selectedEntidade = entidade;
            nameField.setText(entidade.getName());
            cnpjField.setText(entidade.getCnpj());
            typeComboBox.setValue(entidade.getType());
        }
    }

    @FXML
    private void handleSave() {
            String name = nameField.getText();
            String cnpj = cnpjField.getText();
            String type = typeComboBox.getValue();
            if (selectedEntidade == null) {
                Entidade entidade = new Entidade(name, cnpj, type);
                entidadeDAO.insert(entidade);
            } else {
                selectedEntidade.setName(name);
                selectedEntidade.setCnpj(cnpj);
                selectedEntidade.setType(type);
                entidadeDAO.update(selectedEntidade);
            }
            loadEntidades();
        }
    }