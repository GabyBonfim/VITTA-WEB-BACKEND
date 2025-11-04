package br.com.fiap;

import br.com.fiap.api.Endereco;
import br.com.fiap.beans.Paciente;
import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.*;
import java.util.ArrayList;

public class PacienteDAO {

    // =============================
    // INSERT
    // =============================
    public String inserir(Paciente paciente) throws SQLException, ClassNotFoundException {
        Endereco e = paciente.getEndereco();
        int enderecoId = 0;

        try (Connection conn = new ConexaoFactory().conexao()) {

            // Inserir endereço primeiro (se existir)
            if (e != null) {
                String sqlEndereco = """
                    INSERT INTO ENDERECO (CEP, LOGRADOURO, COMPLEMENTO, BAIRRO, LOCALIDADE, ESTADO, REGIAO)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
                try (PreparedStatement stmtEnd = conn.prepareStatement(sqlEndereco, new String[]{"ID"})) {
                    stmtEnd.setString(1, e.getCep());
                    stmtEnd.setString(2, e.getLogradouro());
                    stmtEnd.setString(3, e.getComplemento());
                    stmtEnd.setString(4, e.getBairro());
                    stmtEnd.setString(5, e.getLocalidade());
                    stmtEnd.setString(6, e.getEstado());
                    stmtEnd.setString(7, e.getRegiao());
                    stmtEnd.executeUpdate();

                    try (ResultSet rsEnd = stmtEnd.getGeneratedKeys()) {
                        if (rsEnd.next()) {
                            enderecoId = rsEnd.getInt(1);
                        }
                    }
                }
            }

            // Inserir paciente com o endereço vinculado
            String sqlPaciente = """
                INSERT INTO PACIENTE (NOME, DATANASCIMENTO, CPF, NUMERO, FEEDBACK, ENDERECO_ID)
                VALUES (?, ?, ?, ?, ?, ?)
            """;
            try (PreparedStatement stmtPac = conn.prepareStatement(sqlPaciente)) {
                stmtPac.setString(1, paciente.getNome());
                stmtPac.setString(2, paciente.getDataNascimento());
                stmtPac.setString(3, paciente.getCpf());
                stmtPac.setInt(4, paciente.getNumero());
                stmtPac.setString(5, paciente.getFeedback());
                stmtPac.setInt(6, enderecoId);
                stmtPac.executeUpdate();
            }
        }

        return "✅ Paciente cadastrado com sucesso!";
    }

    // =============================
    // DELETE
    // =============================
    public String deletar(int id) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao()) {
            // Primeiro, descobrir o ID do endereço vinculado
            int enderecoId = 0;
            String sqlBuscarEndereco = "SELECT ENDERECO_ID FROM PACIENTE WHERE ID = ?";
            try (PreparedStatement stmtBusca = conn.prepareStatement(sqlBuscarEndereco)) {
                stmtBusca.setInt(1, id);
                ResultSet rs = stmtBusca.executeQuery();
                if (rs.next()) {
                    enderecoId = rs.getInt("ENDERECO_ID");
                }
                rs.close();
            }

            // Depois, deletar o paciente
            String sqlPaciente = "DELETE FROM PACIENTE WHERE ID = ?";
            try (PreparedStatement stmtPac = conn.prepareStatement(sqlPaciente)) {
                stmtPac.setInt(1, id);
                stmtPac.executeUpdate();
            }

            // Por fim, deletar o endereço (se existir)
            if (enderecoId > 0) {
                String sqlEndereco = "DELETE FROM ENDERECO WHERE ID = ?";
                try (PreparedStatement stmtEnd = conn.prepareStatement(sqlEndereco)) {
                    stmtEnd.setInt(1, enderecoId);
                    stmtEnd.executeUpdate();
                }
            }
        }

        return "🗑️ Paciente e endereço deletados com sucesso!";
    }

    // =============================
    // UPDATE
    // =============================
    public String atualizar(Paciente paciente) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao()) {
            // Atualizar paciente
            String sqlPaciente = """
                UPDATE PACIENTE
                SET NOME = ?, DATANASCIMENTO = ?, CPF = ?, NUMERO = ?, FEEDBACK = ?
                WHERE ID = ?
            """;
            try (PreparedStatement stmtPac = conn.prepareStatement(sqlPaciente)) {
                stmtPac.setString(1, paciente.getNome());
                stmtPac.setString(2, paciente.getDataNascimento());
                stmtPac.setString(3, paciente.getCpf());
                stmtPac.setInt(4, paciente.getNumero());
                stmtPac.setString(5, paciente.getFeedback());
                stmtPac.setInt(6, paciente.getId());
                stmtPac.executeUpdate();
            }

            // Atualizar endereço (se existir)
            if (paciente.getEndereco() != null) {
                Endereco e = paciente.getEndereco();
                String sqlEndereco = """
                    UPDATE ENDERECO 
                    SET CEP = ?, LOGRADOURO = ?, COMPLEMENTO = ?, BAIRRO = ?, 
                        LOCALIDADE = ?, ESTADO = ?, REGIAO = ?
                    WHERE ID = (
                        SELECT ENDERECO_ID FROM PACIENTE WHERE ID = ?
                    )
                """;
                try (PreparedStatement stmtEnd = conn.prepareStatement(sqlEndereco)) {
                    stmtEnd.setString(1, e.getCep());
                    stmtEnd.setString(2, e.getLogradouro());
                    stmtEnd.setString(3, e.getComplemento());
                    stmtEnd.setString(4, e.getBairro());
                    stmtEnd.setString(5, e.getLocalidade());
                    stmtEnd.setString(6, e.getEstado());
                    stmtEnd.setString(7, e.getRegiao());
                    stmtEnd.setInt(8, paciente.getId());
                    stmtEnd.executeUpdate();
                }
            }
        }

        return "♻️ Paciente atualizado com sucesso!";
    }

    // =============================
    // SELECT
    // =============================
    public ArrayList<Paciente> selecionar() throws SQLException, ClassNotFoundException {
        ArrayList<Paciente> listaPacientes = new ArrayList<>();

        String sql = """
            SELECT p.ID, p.NOME, p.DATANASCIMENTO, p.CPF, p.NUMERO, p.FEEDBACK,
                   e.ID AS ENDERECO_ID, e.CEP, e.LOGRADOURO, e.COMPLEMENTO, 
                   e.BAIRRO, e.LOCALIDADE, e.ESTADO, e.REGIAO
            FROM PACIENTE p
            LEFT JOIN ENDERECO e ON p.ENDERECO_ID = e.ID
        """;

        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("ID"));
                paciente.setNome(rs.getString("NOME"));
                paciente.setDataNascimento(rs.getString("DATANASCIMENTO"));
                paciente.setCpf(rs.getString("CPF"));
                paciente.setNumero(rs.getInt("NUMERO"));
                paciente.setFeedback(rs.getString("FEEDBACK"));

                Endereco endereco = new Endereco();
                endereco.setCep(rs.getString("CEP"));
                endereco.setLogradouro(rs.getString("LOGRADOURO"));
                endereco.setComplemento(rs.getString("COMPLEMENTO"));
                endereco.setBairro(rs.getString("BAIRRO"));
                endereco.setLocalidade(rs.getString("LOCALIDADE"));
                endereco.setEstado(rs.getString("ESTADO"));
                endereco.setRegiao(rs.getString("REGIAO"));

                paciente.setEndereco(endereco);
                listaPacientes.add(paciente);
            }
        }

        return listaPacientes;
    }
}
