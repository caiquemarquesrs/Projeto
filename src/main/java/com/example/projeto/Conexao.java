package com.example.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:doafacil.db");
                initializeDatabase();
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS donors (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    phone TEXT,\n" +
                    "    email TEXT\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS institutions (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    cnpj TEXT NOT NULL,\n" +
                    "    type TEXT NOT NULL\n" +
                    ");");
            statement.execute("CREATE TABLE IF NOT EXISTS donations (\n" +
                    "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    donor_id INTEGER NOT NULL,\n" +
                    "    institution_id INTEGER NOT NULL,\n" +
                    "    item TEXT NOT NULL,\n" +
                    "    quantity INTEGER NOT NULL,\n" +
                    "    donation_date TEXT NOT NULL,\n" +
                    "    FOREIGN KEY (donor_id) REFERENCES donors(id),\n" +
                    "    FOREIGN KEY (institution_id) REFERENCES institutions(id)\n" +
                    ");");
            System.out.println("Esquema do banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o esquema do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexão com o banco de dados fechada com sucesso.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}