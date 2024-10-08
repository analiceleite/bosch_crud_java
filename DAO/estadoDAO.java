package DAO;

import DB_CONNECTION.dbConnection;
import DTO.estadoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class estadoDAO {

    public boolean adicionarEstado(estadoDTO estado) {
        String sql = "INSERT INTO tbestado (siglar, descricao) VALUES (?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estado.getSigla());
            stmt.setString(2, estado.getDescricao());

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar estado no banco: " + e.getMessage());
            return false;
        }
    }

    public List<estadoDTO> listarEstados() {
        List<estadoDTO> estados = new ArrayList<>();

        String sql = "Select * from tbestado";

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                estadoDTO estado = new estadoDTO();
                estado.setSigla(rs.getString("siglar"));
                estado.setDescricao(rs.getString("descricao"));
                estados.add(estado);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar estado: " + e.getMessage());
        }
        return estados;
    }

    public boolean atualizaEstado(estadoDTO estado) {
        String sql = "UPDATE tbestado SET descricao = ? WHERE siglar = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, estado.getDescricao());
            stmt.setString(2, estado.getSigla());
            System.out.println("Estado atualizado!");

            connection.close();
            stmt.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o estado: " + e.getMessage());
        }
        return false;
    }

    public boolean deletarEstado(String sigla) {
        String sql = "DELETE FROM tbestado WHERE siglar = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, sigla);

            stmt.executeUpdate();
            connection.close();
            stmt.close();

            System.out.println("Estado deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }

        return false;
    }
}
