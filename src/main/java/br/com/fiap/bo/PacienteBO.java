package br.com.fiap.bo;

import br.com.fiap.PacienteDAO;
import br.com.fiap.beans.Paciente;

import java.sql.SQLException;
import java.util.ArrayList;

public class PacienteBO {

    PacienteDAO pacienteDAO;

    public ArrayList<Paciente> selecionarBo() throws SQLException, ClassNotFoundException {
        pacienteDAO = new PacienteDAO();

        return (ArrayList<Paciente>) pacienteDAO.selecionar();

    }

    public void inserirBO(Paciente paciente) throws SQLException, ClassNotFoundException {
        PacienteDAO pacienteDAO = new PacienteDAO();

        //regra de negocio
        pacienteDAO.inserir(paciente);

    }
    public void attBo(Paciente paciente) throws SQLException, ClassNotFoundException {
        PacienteDAO pacienteDAO = new PacienteDAO();

        //regra de negocio

        pacienteDAO.atualizar(paciente);
    }
    public void deletar(int id) throws SQLException, ClassNotFoundException {
        PacienteDAO pacienteDAO = new PacienteDAO();

        pacienteDAO.deletar(id);
    }
}
