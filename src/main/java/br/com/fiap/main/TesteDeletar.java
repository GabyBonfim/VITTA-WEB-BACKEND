package br.com.fiap.main;

import br.com.fiap.PacienteDAO;
import br.com.fiap.beans.Paciente;

import javax.swing.*;
import java.sql.SQLException;

public class TesteDeletar {

    // int
    static int inteiro(String j){
        return  Integer.parseInt(JOptionPane.showInputDialog(j));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        PacienteDAO pacienteDAO = new PacienteDAO();

        Paciente objPaciente = new Paciente();

        objPaciente.setId(inteiro("Informe o ID do paciente para ser deletado"));

        System.out.println(pacienteDAO.deletar(objPaciente.getId()));



    }
}
