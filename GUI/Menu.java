package GUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import DAO.clienteDAO;
import DAO.estadoDAO;
import DTO.clienteDTO;
import DTO.estadoDTO;

import java.sql.Date;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMenu() throws ParseException {
        while (true) {
            System.out.println("==== SQL VIA CLI ====");
            System.out.println("Select the query you would like to do:");
            System.out.println("1 - Clients:");
            System.out.println("2 - States:");
            System.out.println("0 - Exit:");
            System.out.println("Choose an option:");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    showClientMenu();
                    break;
                case 2:
                    showStateMenu();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid option! Try again.\n");
            }
        }
    }

    private static void showClientMenu() throws ParseException {
        while (true) {
            System.out.println("\n==== CLIENT MENU ====");
            System.out.println("1 - Register Client");
            System.out.println("2 - Clients List");
            System.out.println("3 - Edit Client");
            System.out.println("4 - Delete Client");
            System.out.println("0 - Back to Menu");
            System.out.print("Choose an Option: \n");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    clienteDTO novoCliente = new clienteDTO();

                    System.out.print("Enter the client cpf: ");
                    String cpf = scanner.next();
                    novoCliente.setCpf(cpf);

                    System.out.print("Enter the client name: ");
                    String nome = scanner.next();
                    novoCliente.setNome(nome);

                    System.out.print("Enter the client first address: ");
                    String endereco1 = scanner.next();
                    novoCliente.setEndereco1(endereco1);

                    System.out.print("Enter the client second address: ");
                    String endereco2 = scanner.next();
                    novoCliente.setEndereco2(endereco2);

                    System.out.print("Enter the client neighborhood: ");
                    String bairro = scanner.next();
                    novoCliente.setBairro(bairro);

                    System.out.print("Enter the client city: ");
                    String cidade = scanner.next();
                    novoCliente.setCidade(cidade);

                    System.out.print("Enter the client state: ");
                    String estado = scanner.next();
                    novoCliente.setEstado(estado);

                    System.out.print("Enter the client CEP: ");
                    String cep = scanner.next();
                    novoCliente.setCep(cep);

                    System.out.print("Enter the client's age: ");
                    Integer idade = Integer.valueOf(scanner.next());
                    novoCliente.setIdade(idade);

                    System.out.print("Is the client first shop? (type 1 for 'Yes' or 0 for 'No')");
                    Integer first_buy = Integer.valueOf(scanner.next());
                    novoCliente.setPrimeira_compra(first_buy);

                    System.out.print("Enter the client's birth date (DD/MM/YYYY): ");
                    String dataNascimentoStr = scanner.next();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                        java.util.Date utilDate = sdf.parse(dataNascimentoStr);  // Converte para java.util.Date
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  // Converte para java.sql.Date
                        novoCliente.setData_nascimento(sqlDate);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please use DD/MM/YYYY.");
                    }

                    clienteDAO.registerClient(novoCliente);
                    break;
                case 2:
                    clienteDAO.listClients().stream().map(cliente -> cliente.getNome() + " - " + cliente.getData_nascimento() + " - " + cliente.getCpf() + " - " + cliente.getIdade() + " - " + cliente.getEndereco1() + " - " + cliente.getEndereco2() + " - " + cliente.getBairro() + " - " + cliente.getCidade() + " - " + cliente.getEstado() + " - " + cliente.getPrimeira_compra() + " - " + cliente.getCep()).forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter the client CPF to edit: ");
                    String cpfParaEditar = scanner.next();

                    clienteDTO clienteParaEditar = clienteDAO.getClientByCpf(cpfParaEditar);

                    if (clienteParaEditar != null) {
                        System.out.print("Enter the new client name (current: " + clienteParaEditar.getNome() + "): ");
                        String novoNome = scanner.next();
                        clienteParaEditar.setNome(novoNome);

                        System.out.print("Enter the new client first address (current: " + clienteParaEditar.getEndereco1() + "): ");
                        String novoEndereco1 = scanner.next();
                        clienteParaEditar.setEndereco1(novoEndereco1);

                        System.out.print("Enter the new client second address (current: " + clienteParaEditar.getEndereco2() + "): ");
                        String novoEndereco2 = scanner.next();
                        clienteParaEditar.setEndereco2(novoEndereco2);

                        System.out.print("Enter the new client neighborhood (current: " + clienteParaEditar.getBairro() + "): ");
                        String novoBairro = scanner.next();
                        clienteParaEditar.setBairro(novoBairro);

                        System.out.print("Enter the new client city (current: " + clienteParaEditar.getCidade() + "): ");
                        String novaCidade = scanner.next();
                        clienteParaEditar.setCidade(novaCidade);

                        System.out.print("Enter the new client state (current: " + clienteParaEditar.getEstado() + "): ");
                        String novoEstado = scanner.next();
                        clienteParaEditar.setEstado(novoEstado);

                        System.out.print("Enter the new client CEP (current: " + clienteParaEditar.getCep() + "): ");
                        String novoCep = scanner.next();
                        clienteParaEditar.setCep(novoCep);

                        System.out.print("Enter the new client's age (current: " + clienteParaEditar.getIdade() + "): ");
                        Integer novaIdade = Integer.valueOf(scanner.next());
                        clienteParaEditar.setIdade(novaIdade);

                        System.out.print("Is the client first shop? (current: " + clienteParaEditar.getPrimeira_compra() + ") (type 1 for 'Yes' or 0 for 'No') ");
                        Integer novaPrimeiraCompra = Integer.valueOf(scanner.next());
                        clienteParaEditar.setPrimeira_compra(novaPrimeiraCompra);

                        System.out.print("Enter the client's birth date (current: " + new SimpleDateFormat("dd/MM/yyyy").format(clienteParaEditar.getData_nascimento()) + "): ");
                        String dataNascimentoStrr = scanner.next();
                        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
                        java.util.Date utilDate = sdff.parse(dataNascimentoStrr);
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        clienteParaEditar.setData_nascimento(sqlDate);

                        clienteDAO.updateClient(clienteParaEditar);
                    } else {
                        System.out.println("Client with the provided CPF does not exist.");
                    }
                    break;
                case 4:
                    clienteDAO clienteDAO = new clienteDAO();

                    System.out.print("Enter the client name to delete: ");
                    nome = scanner.next();

                    clienteDAO.deleteClient(nome);

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    private static void showStateMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n==== STATES MENU ====");
            System.out.println("1 - Register State");
            System.out.println("2 - States List");
            System.out.println("3 - Edit State");
            System.out.println("4 - Delete State");
            System.out.println("0 - Back to Menu");
            System.out.print("Choose an Option: \n");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    estadoDTO novoEstado = new estadoDTO(); // Cria um novo estado

                    System.out.print("Enter the state abbreviation (sigla): ");
                    String sigla = scanner.next();
                    novoEstado.setSigla(sigla);

                    System.out.print("Enter the state description: ");
                    String descricao = scanner.next();
                    novoEstado.setDescricao(descricao);

                    estadoDAO.registerState(novoEstado);

                    break;
                case 2:
                    for (estadoDTO estado : estadoDAO.listStates()) {
                        System.out.println(estado.getSigla() + " - " + estado.getDescricao());
                    }
                    break;
                case 3:
                    estadoDAO estadoDAO = new estadoDAO();
                    estadoDTO estado = new estadoDTO();

                    System.out.print("Enter the state abbreviation (sigla) to update: ");
                    sigla = scanner.next();
                    estado.setSigla(sigla);

                    System.out.print("Enter the new description for the state: ");
                    descricao = scanner.next();
                    estado.setDescricao(descricao);

                    estadoDAO.updateState(estado);

                    break;

                case 4:
                    estadoDAO = new estadoDAO();

                    System.out.print("Enter the state abbreviation (sigla) to delete: ");
                    sigla = scanner.next();

                    estadoDAO.deleteState(sigla);

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}
