package org.example.Model;

public class Resposta {
    private int id_resposta;
    private int pontuacao;
    private int quant_acertos;
    private int id_usuario;
    private int id_quiz;

    public Resposta(){}

    public Resposta(int id_resposta, int pontuacao, int quant_acertos, int id_usuario, int id_quiz) {
        this.id_resposta = id_resposta;
        this.pontuacao = pontuacao;
        this.quant_acertos = quant_acertos;
        this.id_usuario = id_usuario;
        this.id_quiz = id_quiz;
    }

    public int getId_resposta() {
        return id_resposta;
    }

    public void setId_resposta(int id_resposta) {
        this.id_resposta = id_resposta;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getQuant_acertos() {
        return quant_acertos;
    }

    public void setQuant_acertos(int quant_acertos) {
        this.quant_acertos = quant_acertos;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }
}
