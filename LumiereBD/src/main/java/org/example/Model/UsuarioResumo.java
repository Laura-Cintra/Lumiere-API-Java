package org.example.Model;

public class UsuarioResumo {
    private String nome;
    private String email;
    private int quant_pontos;
    private String status;
    private int posicao;

    public UsuarioResumo(){}

    public UsuarioResumo(String nome, String email, int quant_pontos, String status, int posicao) {
        this.nome = nome;
        this.email = email;
        this.quant_pontos = quant_pontos;
        this.status = status;
        this.posicao = posicao;
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

    public int getQuant_pontos() {
        return quant_pontos;
    }

    public void setQuant_pontos(int quant_pontos) {
        this.quant_pontos = quant_pontos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }
}
