package POJOAK3;

/**
 * Entrenatzailea klaseak entrenatzaile baten informazioa gordetzen du.
 * Datu nagusiak honako hauek dira: NANa, izen-abizenak, titulazioa eta taldea.
 */
public class Entrenatzailea {

    /** Entrenatzailearen NANa */
    private String nana;

    /** Entrenatzailearen izen-abizenak */
    private String izenAbizena;

    /** Entrenatzailearen titulazioa */
    private String titulazioa;

    /** Entrenatzailea dagoen taldea */
    private String taldea;

    /**
     * Eraikitzaile hutsa.
     */
    public Entrenatzailea() {}

    /**
     * Parametroak jasotzen dituen eraikitzailea.
     *
     * @param nana Entrenatzailearen NANa
     * @param izenAbizena Entrenatzailearen izen-abizenak
     * @param titulazioa Entrenatzailearen titulazioa
     * @param taldea Entrenatzailea dagoen taldea
     */
    public Entrenatzailea(String nana, String izenAbizena, String titulazioa, String taldea) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.titulazioa = titulazioa;
        this.taldea = taldea;
    }

    /**
     * NANa lortzen du.
     *
     * @return Entrenatzailearen NANa
     */
    public String getNana() { return nana; }

    /**
     * NANa ezartzen du.
     *
     * @param nana Entrenatzailearen NAN berria
     */
    public void setNana(String nana) { this.nana = nana; }

    /**
     * Izen-abizenak lortzen ditu.
     *
     * @return Entrenatzailearen izen-abizenak
     */
    public String getIzenAbizena() { return izenAbizena; }

    /**
     * Izen-abizenak ezartzen ditu.
     *
     * @param izenAbizena Entrenatzailearen izen-abizen berriak
     */
    public void setIzenAbizena(String izenAbizena) { this.izenAbizena = izenAbizena; }

    /**
     * Titulazioa lortzen du.
     *
     * @return Entrenatzailearen titulazioa
     */
    public String getTitulazioa() { return titulazioa; }

    /**
     * Titulazioa ezartzen du.
     *
     * @param titulazioa Entrenatzailearen titulazio berria
     */
    public void setTitulazioa(String titulazioa) { this.titulazioa = titulazioa; }

    /**
     * Taldea lortzen du.
     *
     * @return Entrenatzailea dagoen taldea
     */
    public String getTaldea() { return taldea; }

    /**
     * Taldea ezartzen du.
     *
     * @param taldea Entrenatzailea esleituko zaion taldea
     */
    public void setTaldea(String taldea) { this.taldea = taldea; }
}