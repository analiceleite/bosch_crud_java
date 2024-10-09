package DAO;

import DB_CONNECTION.dbConnection;
import DTO.estadoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class estadoDAO {
    static {
        new Scanner(System.in);
    }

    public boolean existsBySigla(String sigla) {
        String sql = "SELECT COUNT(*) FROM tbestado WHERE siglar = ?";

        try (Connection connection = dbConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, sigla);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking if state exists: " + e.getMessage());
        }
        return false;
    }

    public boolean saveNewState(estadoDTO novoEstado) {
        String sql = "INSERT INTO tbestado (siglar, descricao) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, novoEstado.getSigla());
                stmt.setString(2, novoEstado.getDescricao());

                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;

            }
        } catch (SQLException e) {
            System.out.println("Error, state could not be saved: " + e.getMessage());
            return false;
        }
    }

    public static void registerState(estadoDTO novoEstado) {
        estadoDAO estadoDAO = new estadoDAO();

        if (estadoDAO.existsBySigla(novoEstado.getSigla())) {
            System.out.println("Error: State with this abbreviation already exists.");
            return;
        }

        boolean success = estadoDAO.saveNewState(novoEstado);

        if (success) {
            System.out.println("State added successfully!");
        } else {
            System.out.println("Error adding state.");
        }
    }

    public static List<estadoDTO> listStates() {
        List<estadoDTO> estados = new ArrayList<>();

        String sql = "Select * from tbestado";

        try {
            Connection connection = dbConnection.getConnection();
            assert connection != null;
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

    public boolean updateState(estadoDTO estado) {
        estadoDAO estadoDAO = new estadoDAO();
        String sql = "UPDATE tbestado SET descricao = ? WHERE siglar = ?";

        try (Connection connection = dbConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, estado.getDescricao());
                stmt.setString(2, estado.getSigla());

                stmt.executeUpdate();

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("State updated successfully!");
                    return true;
                } else {
                    System.out.println("Error: No state was updated.");
                    return false;
                }

            }
        } catch (
                SQLException e) {
            System.out.println("Error updating the state: " + e.getMessage());
            return false;
        }
    }

    public void deleteState(String sigla) {
        String sql = "DELETE FROM tbestado WHERE siglar = ?";

        try (Connection connection = dbConnection.getConnection()
        ) {
            assert connection != null;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, sigla);

                stmt.executeUpdate();
                connection.close();
                stmt.close();

                System.out.println("State deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting: " + e.getMessage());
        }

    }
}
