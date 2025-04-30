package com.example.projeto;

import com.example.projeto.Entidade;
import com.example.projeto.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntidadeDAO {

    private Connection connection;

    public EntidadeDAO() {
        this.connection = Conexao.getConnection();
    }

    public void insert(Entidade entidade) {
        String sql = "INSERT INTO entidades (name, cnpj, type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entidade.getName());
            stmt.setString(2, entidade.getCnpj());
            stmt.setString(3, entidade.getType());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                entidade.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir entidade: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Entidade entidade) {
        String sql = "UPDATE entidades SET name = ?, cnpj = ?, type = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entidade.getName());
            stmt.setString(2, entidade.getCnpj());
            stmt.setString(3, entidade.getType());
            stmt.setInt(4, entidade.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar entidades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Entidade entidade) {
        String sql = "DELETE FROM entidades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entidade.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar entidades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Entidade> findAll() {
        List<Entidade> entidades = new ArrayList<>();
        String sql = "SELECT * FROM entidades ORDER BY name";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Entidade entidade = new Entidade();
                entidade.setId(rs.getInt("id"));
                entidade.setName(rs.getString("name"));
                entidade.setCnpj(rs.getString("cnpj"));
                entidade.setType(rs.getString("type"));
                entidades.add(entidade);
            }
        } catch (SQLException e) {
            System.err.println("Error finding all entidades: " + e.getMessage());
            e.printStackTrace();
        }
        return entidades;
    }

    public Entidade findById(int id) {
        String sql = "SELECT * FROM entidades WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Entidade entidade = new Entidade();
                entidade.setId(rs.getInt("id"));
                entidade.setName(rs.getString("name"));
                entidade.setCnpj(rs.getString("cnpj"));
                entidade.setType(rs.getString("type"));
                return entidade;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao procurar entiades por id: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}