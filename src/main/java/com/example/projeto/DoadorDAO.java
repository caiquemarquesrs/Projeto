package com.example.projeto;

import com.example.projeto.Doador;
import com.example.projeto.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoadorDAO {
    private Connection connection;

    public DoadorDAO() {
        this.connection = Conexao.getConnection();
    }

    public void insert(Doador doador) {
        String sql = "INSERT INTO doadores (name, phone, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, doador.getName());
            stmt.setString(2, doador.getPhone());
            stmt.setString(3, doador.getEmail());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                doador.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir o doador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Doador doador) {
        String sql = "UPDATE doadores SET name = ?, phone = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, doador.getName());
            stmt.setString(2, doador.getPhone());
            stmt.setString(3, doador.getEmail());
            stmt.setInt(4, doador.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar doador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Doador doador) {
        String sql = "DELETE FROM donors WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doador.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar doador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Doador> findAll() {
        List<Doador> doadores = new ArrayList<>();
        String sql = "SELECT * FROM doadores ORDER BY name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doador doador = new Doador();
                doador.setId(rs.getInt("id"));
                doador.setName(rs.getString("name"));
                doador.setPhone(rs.getString("phone"));
                doador.setEmail(rs.getString("email"));

                doadores.add(doador);
            }
        } catch (SQLException e) {
            System.err.println("Error finding all doadores: " + e.getMessage());
            e.printStackTrace();
        }

        return doadores;
    }

    public Doador findById(int id) {
        String sql = "SELECT * FROM doadores WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Doador doador = new Doador();
                doador.setId(rs.getInt("id"));
                doador.setName(rs.getString("name"));
                doador.setPhone(rs.getString("phone"));
                doador.setEmail(rs.getString("email"));

                return doador;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao procurar doador by id: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}