package com.example.projeto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import com.example.projeto.Conexao;

public class HelloApplication extends Application {
    private StackPane contentArea;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        contentArea = new StackPane();
        contentArea.getChildren().add(new Label("Bem-vindo ao Sistema de Doações Comunitárias"));
        VBox header = new VBox(10);
        header.setPadding(new Insets(20));

        Label title = new Label("DoaFácil - Sistema de Doações Comunitárias");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox buttonBar = new HBox(10);
        Button donorButton = new Button("Doadores");
        donorButton.setPrefWidth(150);
        donorButton.setOnAction(e -> loadView("Doador.fxml"));
        Button institutionButton = new Button("Instituições");
        institutionButton.setPrefWidth(150);
        institutionButton.setOnAction(e -> loadView("Entidade.fxml"));
        Button donationButton = new Button("Doações");
        donationButton.setPrefWidth(150);
        donationButton.setOnAction(e -> loadView("DoacaoController.fxml"));
        Button listButton = new Button("Listar Doações");
        listButton.setPrefWidth(150);
        listButton.setOnAction(e -> loadView("DoacaoLista.fxml"));
        buttonBar.getChildren().addAll(donorButton, institutionButton, donationButton, listButton);
        header.getChildren().addAll(title, buttonBar);
        root.setTop(header);
        root.setCenter(contentArea);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("DoaFácil - Sistema de Doações Comunitárias");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            System.out.println("View carregada com sucesso: " + fxmlFile);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a view: " + fxmlFile);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Conexao.closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}