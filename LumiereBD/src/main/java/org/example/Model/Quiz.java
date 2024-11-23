package org.example.Model;

public class Quiz {

    private int id_quiz;
    private String nome;
    private String descricao;
    private int pontuacao_maxima;

    public Quiz(){}

    public Quiz(int id_quiz, String nome, String descricao, int pontuacao_maxima) {
        this.id_quiz = id_quiz;
        this.nome = nome;
        this.descricao = descricao;
        this.pontuacao_maxima = pontuacao_maxima;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPontuacao_maxima() {
        return pontuacao_maxima;
    }

    public void setPontuacao_maxima(int pontuacao_maxima) {
        this.pontuacao_maxima = pontuacao_maxima;
    }
}
