package br.com.fiap.main;

import br.com.fiap.PacienteDAO;
import br.com.fiap.beans.Paciente;
import br.com.fiap.api.Endereco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesteSelecionar {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Instanciar objeto DAO
        PacienteDAO pacienteDAO = new PacienteDAO();

        // Buscar lista de pacientes
        List<Paciente> listaPacientes = (ArrayList<Paciente>) pacienteDAO.selecionar();

        if (listaPacientes != null && !listaPacientes.isEmpty()) {
            // foreach
            for (Paciente paciente : listaPacientes) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + paciente.getId());
                System.out.println("Nome: " + paciente.getNome());
                System.out.println("Data de Nascimento: " + paciente.getDataNascimento());
                System.out.println("CPF: " + paciente.getCpf());
                System.out.println("Número para Contato: " + paciente.getNumero());
                System.out.println("Feedback: " + paciente.getFeedback());

                Endereco end = paciente.getEndereco();
                if (end != null) {
                    System.out.println("---- Endereço ----");
                    System.out.println("CEP: " + end.getCep());
                    System.out.println("Logradouro: " + end.getLogradouro());
                    System.out.println("Complemento: " + end.getComplemento());
                    System.out.println("Bairro: " + end.getBairro());
                    System.out.println("Cidade: " + end.getLocalidade());
                    System.out.println("Estado: " + end.getEstado());
                    System.out.println("Região: " + end.getRegiao());
                }

                System.out.println("----------------------------------------\n");
            }
        } else {
            System.out.println("Nenhum paciente encontrado no banco de dados.");
        }
    }
}
