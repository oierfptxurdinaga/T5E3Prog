package POJOAK3;

import java.sql.Date;

/**
 * Jokalaria klaseak jokalari baten informazioa gordetzen du.
 * Datu nagusiak honako hauek dira: NANa, izen-abizenak, dortsala,
 * posizioa, jaiotze-data eta taldea.
 */
public class Jokalaria {

    /** Jokalariaren NANa */
    private String nana;

    /** Jokalariaren izen-abizenak */
    private String izenAbizena;

    /** Jokalariaren dortsala (zenbakia) */
    private int dortsala;

    /** Jokalariaren posizioa zelaian */
    private String posizioa;

    /** Jokalariaren jaiotze-data */
    private Date jaiotzeData;

    /** Jokalaria dagoen taldea */
    private String taldea;

    /**
     * Eraikitzaile hutsa.
     */
    public Jokalaria() {}

    /**
     * Parametroak jasotzen dituen eraikitzailea.
     *
     * @param nana Jokalariaren NANa
     * @param izenAbizena Jokalariaren izen-abizenak
     * @param dortsala Jokalariaren dortsala
     * @param posizioa Jokalariaren posizioa
     * @param jaiotzeData Jokalariaren jaiotze-data
     * @param taldea Jokalaria dagoen taldea
     */
    public Jokalaria(String nana, String izenAbizena, int dortsala, String posizioa, Date jaiotzeData, String taldea) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.dortsala = dortsala;
        this.posizioa = posizioa;
        this.jaiotzeData = jaiotzeData;
        this.taldea = taldea;
    }

    /**
     * NANa lortzen du.
     *
     * @return Jokalariaren NANa
     */
    public String getNana() {
        return nana;
    }

    /**
     * NANa ezartzen du.
     *
     * @param nana Jokalariaren NAN berria
     */
    public void setNana(String nana) {
        this.nana = nana;
    }

    /**
     * Izen-abizenak lortzen ditu.
     *
     * @return Jokalariaren izen-abizenak
     */
    public String getIzenAbizena() {
        return izenAbizena;
    }

    /**
     * Izen-abizenak ezartzen ditu.
     *
     * @param izenAbizena Jokalariaren izen-abizen berriak
     */
    public void setIzenAbizena(String izenAbizena) {
        this.izenAbizena = izenAbizena;
    }

    /**
     * Dortsala lortzen du.
     *
     * @return Jokalariaren dortsala
     */
    public int getDortsala() {
        return dortsala;
    }

    /**
     * Dortsala ezartzen du.
     *
     * @param dortsala Jokalariaren dortsal berria
     */
    public void setDortsala(int dortsala) {
        this.dortsala = dortsala;
    }

    /**
     * Posizioa lortzen du.
     *
     * @return Jokalariaren posizioa
     */
    public String getPosizioa() {
        return posizioa;
    }

    /**
     * Posizioa ezartzen du.
     *
     * @param posizioa Jokalariaren posizio berria
     */
    public void setPosizioa(String posizioa) {
        this.posizioa = posizioa;
    }

    /**
     * Jaiotze-data lortzen du.
     *
     * @return Jokalariaren jaiotze-data
     */
    public Date getJaiotzeData() {
        return jaiotzeData;
    }

    /**
     * Jaiotze-data ezartzen du.
     *
     * @param jaiotzeData Jokalariaren jaiotze-data berria
     */
    public void setJaiotzeData(Date jaiotzeData) {
        this.jaiotzeData = jaiotzeData;
    }

    /**
     * Taldea lortzen du.
     *
     * @return Jokalaria dagoen taldea
     */
    public String getTaldea() {
        return taldea;
    }

    /**
     * Taldea ezartzen du.
     *
     * @param taldea Jokalaria esleituko zaion taldea
     */
    public void setTaldea(String taldea) {
        this.taldea = taldea;
    }
}