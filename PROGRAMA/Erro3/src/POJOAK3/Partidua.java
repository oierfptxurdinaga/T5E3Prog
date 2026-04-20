package POJOAK3;

import java.sql.Date;
import java.sql.Time;

/**
 * Partidua klaseak partida baten informazioa gordetzen du.
 * Datu nagusiak honako hauek dira: identifikatzailea, kodea,
 * denboraldia, data eta ordua, golak, zelaia, taldeak eta epaileak.
 */
public class Partidua {

    /** Partiduaren identifikatzaile automatikoa */
    private int idAuto;

    /** Partiduaren kodea */
    private String kodPartidua;

    /** Partiduaren denboraldia */
    private String denboraldia;

    /** Partiduaren data */
    private Date data;

    /** Partiduaren ordua */
    private Time ordua;

    /**
     * Talde lokalak sartutako golak.
     * Integer erabiltzen da balio nulua onartzeko (partidua jokatu gabe badago).
     */
    private Integer golakLokala;

    /**
     * Kanpoko taldeak sartutako golak.
     * Integer erabiltzen da balio nulua onartzeko.
     */
    private Integer golakKanpokoak;

    /** Partidua jokatzen den zelaia */
    private String zelaia;

    /** Talde lokala */
    private String taldeLokala;

    /** Kanpoko taldea */
    private String kampokoTaldea;

    /** Lehen epailea */
    private String epailea1;

    /** Bigarren epailea */
    private String epailea2;

    /**
     * Eraikitzaile hutsa.
     */
    public Partidua() {}

    /**
     * Id automatikoa lortzen du.
     *
     * @return Partiduaren identifikatzailea
     */
    public int getIdAuto() { return idAuto; }

    /**
     * Id automatikoa ezartzen du.
     *
     * @param idAuto Identifikatzaile berria
     */
    public void setIdAuto(int idAuto) { this.idAuto = idAuto; }

    /**
     * Partiduaren kodea lortzen du.
     *
     * @return Partiduaren kodea
     */
    public String getKodPartidua() { return kodPartidua; }

    /**
     * Partiduaren kodea ezartzen du.
     *
     * @param kodPartidua Kode berria
     */
    public void setKodPartidua(String kodPartidua) { this.kodPartidua = kodPartidua; }

    /**
     * Denboraldia lortzen du.
     *
     * @return Denboraldia
     */
    public String getDenboraldia() { return denboraldia; }

    /**
     * Denboraldia ezartzen du.
     *
     * @param denboraldia Denboraldi berria
     */
    public void setDenboraldia(String denboraldia) { this.denboraldia = denboraldia; }

    /**
     * Data lortzen du.
     *
     * @return Partiduaren data
     */
    public Date getData() { return data; }

    /**
     * Data ezartzen du.
     *
     * @param data Data berria
     */
    public void setData(Date data) { this.data = data; }

    /**
     * Ordua lortzen du.
     *
     * @return Partiduaren ordua
     */
    public Time getOrdua() { return ordua; }

    /**
     * Ordua ezartzen du.
     *
     * @param ordua Ordu berria
     */
    public void setOrdua(Time ordua) { this.ordua = ordua; }

    /**
     * Talde lokalaren golak lortzen ditu.
     *
     * @return Gol kopurua (balio nulua izan daiteke)
     */
    public Integer getGolakLokala() { return golakLokala; }

    /**
     * Talde lokalaren golak ezartzen ditu.
     *
     * @param golakLokala Gol kopuru berria
     */
    public void setGolakLokala(Integer golakLokala) { this.golakLokala = golakLokala; }

    /**
     * Kanpoko taldearen golak lortzen ditu.
     *
     * @return Gol kopurua (balio nulua izan daiteke)
     */
    public Integer getGolakKanpokoak() { return golakKanpokoak; }

    /**
     * Kanpoko taldearen golak ezartzen ditu.
     *
     * @param golakKanpokoak Gol kopuru berria
     */
    public void setGolakKanpokoak(Integer golakKanpokoak) { this.golakKanpokoak = golakKanpokoak; }

    /**
     * Zelaia lortzen du.
     *
     * @return Zelaia
     */
    public String getZelaia() { return zelaia; }

    /**
     * Zelaia ezartzen du.
     *
     * @param zelaia Zelaia berria
     */
    public void setZelaia(String zelaia) { this.zelaia = zelaia; }

    /**
     * Talde lokala lortzen du.
     *
     * @return Talde lokala
     */
    public String getTaldeLokala() { return taldeLokala; }

    /**
     * Talde lokala ezartzen du.
     *
     * @param taldeLokala Talde lokal berria
     */
    public void setTaldeLokala(String taldeLokala) { this.taldeLokala = taldeLokala; }

    /**
     * Kanpoko taldea lortzen du.
     *
     * @return Kanpoko taldea
     */
    public String getKampokoTaldea() { return kampokoTaldea; }

    /**
     * Kanpoko taldea ezartzen du.
     *
     * @param kampokoTaldea Kanpoko talde berria
     */
    public void setKampokoTaldea(String kampokoTaldea) { this.kampokoTaldea = kampokoTaldea; }

    /**
     * Lehen epailea lortzen du.
     *
     * @return Lehen epailea
     */
    public String getEpailea1() { return epailea1; }

    /**
     * Lehen epailea ezartzen du.
     *
     * @param epailea1 Epaile berria
     */
    public void setEpailea1(String epailea1) { this.epailea1 = epailea1; }

    /**
     * Bigarren epailea lortzen du.
     *
     * @return Bigarren epailea
     */
    public String getEpailea2() { return epailea2; }

    /**
     * Bigarren epailea ezartzen du.
     *
     * @param epailea2 Epaile berria
     */
    public void setEpailea2(String epailea2) { this.epailea2 = epailea2; }
}