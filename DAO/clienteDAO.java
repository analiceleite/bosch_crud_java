package DAO;

import DTO.clienteDTO;
import DB_CONNECTION.dbConnection;
import DTO.estadoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class clienteDAO {

    public boolean adicionarCliente(clienteDTO cliente) {
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

            //* Converter java.util.Date para java.sql.Date
            Date sqlDate = new Date(cliente.getData_nascimento().getTime());
            stmt.setDate(11, sqlDate); //? Define a data no PreparedStatement

            stmt.executeUpdate();
            stmt.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente no banco: " + e.getMessage());
            return false;
        }
    }

    public List<clienteDTO> listarClientes() {
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

    public boolean atualizaCliente(clienteDTO cliente) {
        String sql = "UPDATE tbcliente SET nome = ?, endereco1 = ?, endereco2 = ?, bairro = ?, cidade = ?, estado = ?, cep = ?, idade = ?, primeira_compra = ?, data_nascimento = ? WHERE cpf = ?";

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

            System.out.println("Cliente atualizado!");

            connection.close();
            stmt.close();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar os dados do cliente: " + e.getMessage());
        }
        return false;
    }

    public boolean deletarCliente(String cpf) {
        String sql = "DELETE FROM tbcliente WHERE cpf = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, cpf);

            stmt.executeUpdate();
            connection.close();
            stmt.close();

            System.out.println("Cliente deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar o cliente: " + e.getMessage());
        }

        return false;
    }
}
