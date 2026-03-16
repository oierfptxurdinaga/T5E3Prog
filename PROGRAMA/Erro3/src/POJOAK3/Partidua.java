package POJOAK3;

import java.sql.Date;
import java.sql.Time;

public class Partidua {

    private int idAuto;
    private String kodPartidua;
    private String denboraldia;
    private Date data;
    private Time ordua;
    private Integer golakLokala; // Integer erabilita nuluak onartzeko (adib. oraindik jokatu ez bada)
    private Integer golakKanpokoak;
    private String zelaia;
    private String taldeLokala;
    private String kampokoTaldea;
    private String epailea1;
    private String epailea2;

    public Partidua() {}

    // Getters eta Setters
    public int getIdAuto() { return idAuto; }
    public void setIdAuto(int idAuto) { this.idAuto = idAuto; }

    public String getKodPartidua() { return kodPartidua; }
    public void setKodPartidua(String kodPartidua) { this.kodPartidua = kodPartidua; }

    public String getDenboraldia() { return denboraldia; }
    public void setDenboraldia(String denboraldia) { this.denboraldia = denboraldia; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Time getOrdua() { return ordua; }
    public void setOrdua(Time ordua) { this.ordua = ordua; }

    public Integer getGolakLokala() { return golakLokala; }
    public void setGolakLokala(Integer golakLokala) { this.golakLokala = golakLokala; }

    public Integer getGolakKanpokoak() { return golakKanpokoak; }
    public void setGolakKanpokoak(Integer golakKanpokoak) { this.golakKanpokoak = golakKanpokoak; }

    public String getZelaia() { return zelaia; }
    public void setZelaia(String zelaia) { this.zelaia = zelaia; }

    public String getTaldeLokala() { return taldeLokala; }
    public void setTaldeLokala(String taldeLokala) { this.taldeLokala = taldeLokala; }

    public String getKampokoTaldea() { return kampokoTaldea; }
    public void setKampokoTaldea(String kampokoTaldea) { this.kampokoTaldea = kampokoTaldea; }

    public String getEpailea1() { return epailea1; }
    public void setEpailea1(String epailea1) { this.epailea1 = epailea1; }

    public String getEpailea2() { return epailea2; }
    public void setEpailea2(String epailea2) { this.epailea2 = epailea2; }
}