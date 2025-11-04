package br.com.fiap.main;

import br.com.fiap.PacienteDAO;
import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Paciente;
import java.time.LocalDate;
import javax.swing.*;
import java.sql.SQLException;

public class TesteAtualizar {

    // String
    static String texto(String j) {
        return JOptionPane.showInputDialog(j);
    }

    // int
    static int inteiro(String j) {
        return Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    // double
    static double real(String j) {
        return Double.parseDouble(JOptionPane.showInputDialog(j));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Paciente objPaciente = new Paciente();

        PacienteDAO pacienteDao = new PacienteDAO();

        objPaciente.setId(inteiro("Informe o ID do paciente a ser atualizado"));
        objPaciente.setNome(texto("Atualize o Nome"));
        objPaciente.setDataNascimento(texto("Data de nascimento (MM-DD-AAAA): "));
        objPaciente.setCpf(texto("Atualize a Nota"));
        objPaciente.setNumero(inteiro("Número para contato: "));
        objPaciente.setFeedback(texto("Atualizações/Feedbacks: "));

        Endereco endereco = new Endereco();
        endereco.setCep(texto("CEP: "));
        endereco.setLogradouro(texto("Logradouro: "));
        endereco.setComplemento(texto("Complemento: "));
        endereco.setBairro(texto("Bairro: "));
        endereco.setLocalidade(texto("Cidade: "));
        endereco.setEstado(texto("Estado: "));
        endereco.setRegiao(texto("Região: "));

        objPaciente.setEndereco(endereco);

        System.out.println(pacienteDao.atualizar(objPaciente));

    }



}
