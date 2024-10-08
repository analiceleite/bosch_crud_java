import DAO.estadoDAO;
import DAO.clienteDAO;
import DTO.estadoDTO;
import DTO.clienteDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {

        estadoDAO daoEstado = new estadoDAO();
        clienteDAO daoCliente = new clienteDAO();

        //* Add State
        estadoDTO novoEstado = new estadoDTO();
        novoEstado.setSigla("SP");
        novoEstado.setDescricao("São Paulo");
        daoEstado.adicionarEstado(novoEstado);

        //* List States
        for (estadoDTO estado : daoEstado.listarEstados()) {
            System.out.println(estado.getSigla() + " - "+estado.getDescricao());
        }

        //* Update State
        novoEstado.setDescricao("São Paulo - Atualizado");
        daoEstado.atualizaEstado(novoEstado);

        //* Delete State
        daoEstado.deletarEstado(novoEstado.getSigla());

        //* Add Client
        clienteDTO novoCliente = new clienteDTO();
        novoCliente.setCpf("14011949906");
        novoCliente.setNome("Analice Nova Cliente");
        novoCliente.setIdade(20);
        novoCliente.setCep("89450000");
        novoCliente.setBairro("Itinga");
        novoCliente.setCidade("Araquari");
        novoCliente.setEstado("SC");
        novoCliente.setEndereco1("Adicionando Endereco 01");
        novoCliente.setEndereco2("Adicionando Endereco 02");
        novoCliente.setPrimeira_compra(1);

        // Data de nascimento como String
        String dataNascimentoStr = "20040826";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");


        try {
            Date dataNascimento = formatter.parse(dataNascimentoStr);
            novoCliente.setData_nascimento(dataNascimento);
        } catch (ParseException e) {
            System.out.println("Erro ao converter a data: " + e.getMessage());
            return; // Sai se houver erro na conversão da data
        }

        // Adicionar cliente ao banco
        if (daoCliente.adicionarCliente(novoCliente)) {
            System.out.println("Cliente adicionado com sucesso!");
        } else {
            System.out.println("Erro ao adicionar cliente.");
        }

        //* List Clients
        for (clienteDTO cliente : daoCliente.listarClientes()) {
            System.out.println(cliente.getNome() + " - " + cliente.getIdade() + " - " + cliente.getCpf() + " - " + cliente.getCep() + " - " + cliente.getEstado() + " - " + cliente.getCidade() + " - " + cliente.getBairro() + " - " + cliente.getEndereco1() + " - " + cliente.getEndereco2() + " - " + cliente.getData_nascimento() + " - " + cliente.getPrimeira_compra());
        }

        //* Update Client
        novoCliente.setIdade(25);
        daoCliente.atualizaCliente(novoCliente);

        //* Delete Client
        daoCliente.deletarCliente(novoCliente.getCpf());
    }
}
