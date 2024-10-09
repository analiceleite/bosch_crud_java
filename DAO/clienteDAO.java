package DAO;

import DTO.clienteDTO;
import DB_CONNECTION.dbConnection;
import DTO.estadoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDAO {

    public static boolean existsByCpf(String cpf) {
        String sql = "SELECT COUNT(*) FROM tbcliente WHERE cpf = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking if the client exists: " + e.getMessage());
        }
        return true;
    }

    public static clienteDTO getClientByCpf(String cpf) {
        String sql = "SELECT * FROM tbcliente WHERE cpf = ?";
        clienteDTO cliente = null;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { //? Se um cliente for encontrado
                cliente = new clienteDTO();
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEndereco1(rs.getString("endereco1"));
                cliente.setEndereco2(rs.getString("endereco2"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setCep(rs.getString("cep"));
                cliente.setIdade(rs.getInt("idade"));
                cliente.setPrimeira_compra(rs.getInt("primeira_compra"));
                cliente.setData_nascimento(rs.getDate("data_nascimento"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving client: " + e.getMessage());
        }

        return cliente;
    }

    public boolean saveNewClient(clienteDTO cliente) {
        String sql = "INSERT INTO tbcliente (cpf, nome, endereco1, endereco2, bairro, cidade, estado, cep, idade, primeira_compra, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEndereco1());
            stmt.setString(4, cliente.getEndereco2());
            stmt.setString(5, cliente.getBairro());
            stmt.setString(6, cliente.getCidade());
            stmt.setString(7, cliente.getEstado());
            stmt.setString(8, cliente.getCep());
            stmt.setInt(9, cliente.getIdade());
            stmt.setInt(10, cliente.getPrimeira_compra());

            if (cliente.getData_nascimento() != null) {
                Date sqlDate = new Date(cliente.getData_nascimento().getTime());
                stmt.setDate(11, sqlDate); // Define a data no PreparedStatement
            } else {
                stmt.setNull(11, java.sql.Types.DATE); // Se data_nascimento for nulo, define como NULL no SQL
            }

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error adding client to the database: " + e.getMessage());
            return false;
        }
    }

    public static void registerClient(clienteDTO cliente) {
        clienteDAO clienteDAO = new clienteDAO();

        if (existsByCpf(cliente.getNome())) {
            System.out.println("Error: Client with this name already exists.");
            return;
        }

        boolean success = clienteDAO.saveNewClient(cliente);

        if (success) {
            System.out.println("Client added successfully!");
        } else {
            System.out.println("Error adding client.");
        }
    }

    public static List<clienteDTO> listClients() {
        List<clienteDTO> clientes = new ArrayList<>();

        String sql = "Select * from tbcliente";

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                clienteDTO cliente = new clienteDTO();
                cliente.setCpf(rs.getString("cpf"));
                cliente.setNome(rs.getString("nome"));
                cliente.setIdade(rs.getInt("idade"));
                cliente.setData_nascimento(rs.getDate("data_nascimento"));
                cliente.setCep(rs.getString("cep"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setEndereco1(rs.getString("endereco1"));
                cliente.setEndereco2(rs.getString("endereco1"));
                cliente.setPrimeira_compra(rs.getInt("primeira_compra"));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        }
        return clientes;
    }

    public static boolean updateClient(clienteDTO cliente) {
        String sql = "UPDATE tbcliente SET cpf = ?, nome = ?, endereco1 = ?, endereco2 = ?, bairro = ?, cidade = ?, estado = ?, cep = ?, idade = ?, primeira_compra = ?, data_nascimento = ? WHERE cpf = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEndereco1());
            stmt.setString(4, cliente.getEndereco2());
            stmt.setString(5, cliente.getBairro());
            stmt.setString(6, cliente.getCidade());
            stmt.setString(7, cliente.getEstado());
            stmt.setString(8, cliente.getCep());
            stmt.setInt(9, cliente.getIdade());
            stmt.setInt(10, cliente.getPrimeira_compra());
            stmt.setDate(11, new java.sql.Date(cliente.getData_nascimento().getTime()));

            stmt.setString(12, cliente.getCpf());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Client updated successfully!");
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error updating client data: " + e.getMessage());
        }
        return false;
    }

    public void deleteClient(String nome) {
        String sql = "DELETE FROM tbcliente WHERE nome = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, nome);

            stmt.executeUpdate();
            connection.close();
            stmt.close();

            System.out.println("Client deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error deleting the client: " + e.getMessage());
        }
    }
}
