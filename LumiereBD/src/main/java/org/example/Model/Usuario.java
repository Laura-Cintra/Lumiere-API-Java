package org.example.Model;

public class Usuario {
    private int id_usuario;
    private String nome;
    private String email;
    private String cep;
    private String senha;
    private String data_nascimento;
    private String nick_name;
    private int quant_pontos;
    private int porc_atual;
    private String data_registro;

    public Usuario(){}

    public Usuario(String nome, String email, String cep, String senha, String data_nascimento, String nick_name, int quant_pontos, int porc_atual, String data_registro, int id_usuario) {
        this.nome = nome;
        this.email = email;
        this.cep = cep;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.nick_name = nick_name;
        this.quant_pontos = quant_pontos;
        this.porc_atual = porc_atual;
        this.data_registro = data_registro;
        this.id_usuario = id_usuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getQuant_pontos() {
        return quant_pontos;
    }

    public void setQuant_pontos(int quant_pontos) {
        this.quant_pontos = quant_pontos;
    }

    public int getPorc_atual() {
        return porc_atual;
    }

    public void setPorc_atual(int porc_atual) {
        this.porc_atual = porc_atual;
    }

    public String getData_registro() {
        return data_registro;
    }

    public void setData_registro(String data_registro) {
        this.data_registro = data_registro;
    }
}
