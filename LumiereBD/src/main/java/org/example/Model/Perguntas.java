package org.example.Model;

public class Perguntas {
    private int id_pergunta;
    private String pergunta;
    private String alternativas;
    private String resposta_certa;
    private int id_quiz;

    public Perguntas(){}

    public Perguntas(int id_pergunta, String pergunta, String alternativas, String resposta_certa, int id_quiz) {
        this.pergunta = pergunta;
        this.id_pergunta = id_pergunta;
        this.alternativas = alternativas;
        this.resposta_certa = resposta_certa;
        this.id_quiz = id_quiz;
    }

    public int getId_pergunta() {
        return id_pergunta;
    }

    public void setId_pergunta(int id_pergunta) {
        this.id_pergunta = id_pergunta;
    }

    public String getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(String alternativas) {
        this.alternativas = alternativas;
    }

    public String getResposta_certa() {
        return resposta_certa;
    }

    public void setResposta_certa(String resposta_certa) {
        this.resposta_certa = resposta_certa;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}
