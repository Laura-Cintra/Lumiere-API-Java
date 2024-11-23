package org.example.Model;

public class RankingUser {

    private String nick_name;
    private int posicao;
    private int pontuacao;
    private int porc_consumo;

    public RankingUser(){}

    public RankingUser(String nick_name, int posicao, int pontuacao, int porc_consumo) {
        this.nick_name = nick_name;
        this.posicao = posicao;
        this.pontuacao = pontuacao;
        this.porc_consumo = porc_consumo;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getPorc_consumo() {
        return porc_consumo;
    }

    public void setPorc_consumo(int porc_consumo) {
        this.porc_consumo = porc_consumo;
    }
}
