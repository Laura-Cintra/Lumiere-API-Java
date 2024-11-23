package org.example.Model;

public class UsuarioUpdate {
    private String nome;
    private String cep;
    private String senha;
    private String data_nascimento;
    private String nick_name;

    public UsuarioUpdate(){}

    public UsuarioUpdate(String nome, String cep, String senha, String data_nascimento, String nick_name) {
        this.nome = nome;
        this.cep = cep;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.nick_name = nick_name;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
