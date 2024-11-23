package org.example.Model;

public class ConsumoCusto {

    private String mesAno;
    private double custo_conta;

    public ConsumoCusto(){}

    public ConsumoCusto(String mesAno, double custo_conta) {
        this.mesAno = mesAno;
        this.custo_conta = custo_conta;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public double getCusto_conta() {
        return custo_conta;
    }

    public void setCusto_conta(double custo_conta) {
        this.custo_conta = custo_conta;
    }
}
