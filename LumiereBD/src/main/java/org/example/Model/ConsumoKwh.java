package org.example.Model;

public class ConsumoKwh {

    private String mesAno;
    private int consumoKwh;

    public ConsumoKwh(){}

    public ConsumoKwh(String mesAno, int consumoKwh) {
        this.mesAno = mesAno;
        this.consumoKwh = consumoKwh;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public int getConsumoKwh() {
        return consumoKwh;
    }

    public void setConsumoKwh(int consumoKwh) {
        this.consumoKwh = consumoKwh;
    }
}
