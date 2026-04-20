package POJOAK3;

/**
 * Epailea klaseak epaile baten informazioa gordetzen du.
 * Datu nagusiak honako hauek dira: NANa, izen-abizenak eta maila.
 */
public class Epailea {

    /** Epailearen NANa */
    private String nana;

    /** Epailearen izen-abizenak */
    private String izenAbizena;

    /** Epailearen maila */
    private String maila;

    /**
     * Eraikitzaile hutsa.
     */
    public Epailea() {}

    /**
     * Parametroak jasotzen dituen eraikitzailea.
     *
     * @param nana Epailearen NANa
     * @param izenAbizena Epailearen izen-abizenak
     * @param maila Epailearen maila
     */
    public Epailea(String nana, String izenAbizena, String maila) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.maila = maila;
    }

    /**
     * NANa lortzen du.
     *
     * @return Epailearen NANa
     */
    public String getNana() {
        return nana;
    }

    /**
     * NANa ezartzen du.
     *
     * @param nana Epailearen NAN berria
     */
    public void setNana(String nana) {
        this.nana = nana;
    }

    /**
     * Izen-abizenak lortzen ditu.
     *
     * @return Epailearen izen-abizenak
     */
    public String getIzenAbizena() {
        return izenAbizena;
    }

    /**
     * Izen-abizenak ezartzen ditu.
     *
     * @param izenAbizena Epailearen izen-abizen berriak
     */
    public void setIzenAbizena(String izenAbizena) {
        this.izenAbizena = izenAbizena;
    }

    /**
     * Maila lortzen du.
     *
     * @return Epailearen maila
     */
    public String getMaila() {
        return maila;
    }

    /**
     * Maila ezartzen du.
     *
     * @param maila Epailearen maila berria
     */
    public void setMaila(String maila) {
        this.maila = maila;
    }
}