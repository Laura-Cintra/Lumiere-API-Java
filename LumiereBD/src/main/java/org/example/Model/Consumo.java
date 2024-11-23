package org.example.Model;

import java.time.LocalDate;

public class Consumo {

    private int idConsumo;
    private String dataConsumo;
    private int consumoKwh;
    private double custoConta;

    private int idUsuario;

    public Consumo(){}

    public Consumo(int idConsumo, String dataConsumo, int consumoKwh, double custoConta, int idUsuario) {
        this.idConsumo = idConsumo;
        this.dataConsumo = dataConsumo;
        this.consumoKwh = consumoKwh;
        this.custoConta = custoConta;
        this.idUsuario = idUsuario;
    }

    public int getIdConsumo() {
        return idConsumo;
    }

    public void setIdConsumo(int idConsumo) {
        this.idConsumo = idConsumo;
    }

    public String getDataConsumo() {
        return dataConsumo;
    }

    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }

    public int getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(int consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public double getCustoConta() {
        return custoConta;
    }

    public void setCustoConta(double custoConta) {
        this.custoConta = custoConta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
