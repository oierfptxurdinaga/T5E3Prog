package POJOAK3;

import java.sql.Date;

public class Jokalaria {

    private String nana;
    private String izenAbizena;
    private int dortsala;
    private String posizioa;
    private Date jaiotzeData;
    private String taldea;

    public Jokalaria() {}

    public Jokalaria(String nana, String izenAbizena, int dortsala, String posizioa, Date jaiotzeData, String taldea) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.dortsala = dortsala;
        this.posizioa = posizioa;
        this.jaiotzeData = jaiotzeData;
        this.taldea = taldea;
    }

	public String getNana() {
		return nana;
	}

	public void setNana(String nana) {
		this.nana = nana;
	}

	public String getIzenAbizena() {
		return izenAbizena;
	}

	public void setIzenAbizena(String izenAbizena) {
		this.izenAbizena = izenAbizena;
	}

	public int getDortsala() {
		return dortsala;
	}

	public void setDortsala(int dortsala) {
		this.dortsala = dortsala;
	}

	public String getPosizioa() {
		return posizioa;
	}

	public void setPosizioa(String posizioa) {
		this.posizioa = posizioa;
	}

	public Date getJaiotzeData() {
		return jaiotzeData;
	}

	public void setJaiotzeData(Date jaiotzeData) {
		this.jaiotzeData = jaiotzeData;
	}

	public String getTaldea() {
		return taldea;
	}

	public void setTaldea(String taldea) {
		this.taldea = taldea;
	}
}
    