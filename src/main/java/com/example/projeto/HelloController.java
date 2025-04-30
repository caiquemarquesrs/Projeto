package com.example.projeto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

public class HelloController {

    @FXML
    private StackPane contentArea;

    @FXML
    private void showDoador() {
        loadView("Doador.fxml");
    }

    @FXML
    private void showEntidade() {
        loadView("Entidade.fxml");
    }

    @FXML
    private void showDoacao() {
        loadView("DoacaoController.fxml");
    }

    @FXML
    private void showDoacaoLista() {
        loadView("DoacaoLista.fxml");
    }

    private void loadView(String fxmlFile) {
        try {
            URL fxmlUrl = getClass().getResource("/com/doafacil/view/" + fxmlFile);

            if (fxmlUrl == null) {
                fxmlUrl = getClass().getResource("/" + fxmlFile);
            }

            if (fxmlUrl == null) {
                fxmlUrl = getClass().getResource(fxmlFile);
            }

            if (fxmlUrl == null) {
                System.err.println("Não foi possível encontrar o arquivo FXML: " + fxmlFile);
                System.err.println("Verifique se o arquivo existe e está no local correto.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();

            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            System.out.println("View carregada com sucesso: " + fxmlFile);

        } catch (IOException e) {
            System.err.println("Erro ao carregar a view: " + fxmlFile);
            e.printStackTrace();
        }
    }
}