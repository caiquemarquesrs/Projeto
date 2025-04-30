package com.example.projeto;

import com.example.projeto.Doacao;
import com.example.projeto.Doador;
import com.example.projeto.Entidade;
import com.example.projeto.Conexao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DoacaoDAO {

    private Connection connection;
    private DoadorDAO doadorDAO;
    private EntidadeDAO entidadeDAO;

    public DoacaoDAO() {
        this.connection = Conexao.getConnection();
        this.doadorDAO = new DoadorDAO();
        this.entidadeDAO = new EntidadeDAO();
    }

    public void insert(Doacao doacao) {
        String sql = "INSERT INTO doacoes (doador_id, entidade_id, item, quantity, donation_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, doacao.getDoador().getId());
            stmt.setInt(2, doacao.getEntidade().getId());
            stmt.setString(3, doacao.getItem());
            stmt.setInt(4, doacao.getQuantity());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                doacao.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error ao inseriar doacao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Doacao doacao) {
        String sql = "UPDATE doacoes SET doador_id = ?, entidade_id = ?, item = ?, quantity = ?, donation_date = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doacao.getDoador().getId());
            stmt.setInt(2, doacao.getEntidade().getId());
            stmt.setString(3, doacao.getItem());
            stmt.setInt(4, doacao.getQuantity());
            stmt.setInt(6, doacao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error ao atualizar doacoes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(Doacao doacao) {
        String sql = "DELETE FROM docoes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doacao.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting doacao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Doacao> findAll() {
        List<Doacao> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM doacoes ORDER BY doacao_date DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setId(rs.getInt("id"));

                Doador donor = doadorDAO.findById(rs.getInt("doador_id"));
                doacao.setDoador(donor);

                Entidade entidade = entidadeDAO.findById(rs.getInt("entidade_id"));
                doacao.setEntidade(entidade);

                doacao.setItem(rs.getString("item"));
                doacao.setQuantity(rs.getInt("quantity"));
                doacoes.add(doacao);
            }
        } catch (SQLException e) {
            System.err.println("Error finding all doacoes: " + e.getMessage());
            e.printStackTrace();
        }

        return doacoes;
    }

    public List<Doacao> findByDoador(Doador doador) {
        List<Doacao> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM doacoes WHERE doador_id = ? ORDER BY doacao_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doador.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setId(rs.getInt("id"));
                doacao.setDoador(doador);
                Entidade entidade = entidadeDAO.findById(rs.getInt("entidade_id"));
                doacao.setEntidade(entidade);
                doacao.setItem(rs.getString("item"));
                doacao.setQuantity(rs.getInt("quantity"));
                doacoes.add(doacao);
            }
        } catch (SQLException e) {
            System.err.println("Error finding doacoes by doador: " + e.getMessage());
            e.printStackTrace();
        }
        return doacoes;
    }

    public List<Doacao> findByEntidade(Entidade entidade) {
        List<Doacao> doacoes = new ArrayList<>();
        String sql = "SELECT * FROM doacoes WHERE entidades_id = ? ORDER BY doacoes_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, entidade.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setId(rs.getInt("id"));

                Doador doador = doadorDAO.findById(rs.getInt("doador_id"));
                doacao.setDoador(doador);

                doacao.setEntidade(entidade);
                doacao.setItem(rs.getString("item"));
                doacao.setQuantity(rs.getInt("quantity"));
                doacoes.add(doacao);
            }
        } catch (SQLException e) {
            System.err.println("Error finding doacoes by institution: " + e.getMessage());
            e.printStackTrace();
        }

        return doacoes;
    }
}