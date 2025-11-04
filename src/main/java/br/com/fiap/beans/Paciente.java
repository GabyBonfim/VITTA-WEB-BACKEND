package br.com.fiap.beans;

import br.com.fiap.api.Endereco;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Paciente {
    private int id;
    private String nome;
  //  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private String dataNascimento;
    private String cpf;
    private int numero;
    private Endereco endereco;
    private String feedback;

    public Paciente() {
    }

    public Paciente(int id, String nome, String dataNascimento, String cpf, int numero, String endereco, String feedback) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.numero = numero;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "--- Paciente: --- " +
                "ID: " + id +
                "\n Nome: " + nome + '\'' +
                "\n Data de nascimento: " + dataNascimento +
                "\n CPF: " + cpf + '\'' +
                "\n Número: " + numero +
                "\n Endereco: " + endereco + '\'' +
                "\n Feedback: " + feedback + '\'' +
                ' ';
    }
}
