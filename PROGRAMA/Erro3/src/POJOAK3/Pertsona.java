package POJOAK3;

/**
 * Pertsona klaseak pertsona baten informazio orokorra gordetzen du.
 * Datu nagusiak honako hauek dira: NANa, izen-abizenak, adina,
 * helbidea, telefonoa, taldea eta rola.
 */
public class Pertsona {

    /** Pertsonaren NANa */
    private String nana;

    /** Pertsonaren izen-abizenak */
    private String izenAbizena;

    /**
     * Pertsonaren adina.
     * Integer erabiltzen da balio nulua onartzeko.
     */
    private Integer adina;

    /** Pertsonaren helbidea */
    private String helbidea;

    /** Pertsonaren telefono zenbakia */
    private String tlfn;

    /** Pertsona dagokion taldea */
    private String taldea;

    /** Pertsonaren rola (adibidez: jokalaria, entrenatzailea, epailea, etab.) */
    private String rola;

    /**
     * Eraikitzaile hutsa.
     */
    public Pertsona() {}

    /**
     * Parametroak jasotzen dituen eraikitzailea.
     *
     * @param nana Pertsonaren NANa
     * @param izenAbizena Pertsonaren izen-abizenak
     * @param adina Pertsonaren adina
     * @param helbidea Pertsonaren helbidea
     * @param tlfn Pertsonaren telefonoa
     * @param taldea Pertsona dagokion taldea
     * @param rola Pertsonaren rola
     */
    public Pertsona(String nana, String izenAbizena, Integer adina, String helbidea, String tlfn, String taldea, String rola) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.adina = adina;
        this.helbidea = helbidea;
        this.tlfn = tlfn;
        this.taldea = taldea;
        this.rola = rola;
    }

    /**
     * NANa lortzen du.
     *
     * @return Pertsonaren NANa
     */
    public String getNana() { return nana; }

    /**
     * NANa ezartzen du.
     *
     * @param nana Pertsonaren NAN berria
     */
    public void setNana(String nana) { this.nana = nana; }

    /**
     * Izen-abizenak lortzen ditu.
     *
     * @return Pertsonaren izen-abizenak
     */
    public String getIzenAbizena() { return izenAbizena; }

    /**
     * Izen-abizenak ezartzen ditu.
     *
     * @param izenAbizena Pertsonaren izen-abizen berriak
     */
    public void setIzenAbizena(String izenAbizena) { this.izenAbizena = izenAbizena; }

    /**
     * Adina lortzen du.
     *
     * @return Pertsonaren adina (balio nulua izan daiteke)
     */
    public Integer getAdina() { return adina; }

    /**
     * Adina ezartzen du.
     *
     * @param adina Pertsonaren adin berria
     */
    public void setAdina(Integer adina) { this.adina = adina; }

    /**
     * Helbidea lortzen du.
     *
     * @return Pertsonaren helbidea
     */
    public String getHelbidea() { return helbidea; }

    /**
     * Helbidea ezartzen du.
     *
     * @param helbidea Helbide berria
     */
    public void setHelbidea(String helbidea) { this.helbidea = helbidea; }

    /**
     * Telefono zenbakia lortzen du.
     *
     * @return Pertsonaren telefonoa
     */
    public String getTlfn() { return tlfn; }

    /**
     * Telefono zenbakia ezartzen du.
     *
     * @param tlfn Telefono berria
     */
    public void setTlfn(String tlfn) { this.tlfn = tlfn; }

    /**
     * Taldea lortzen du.
     *
     * @return Pertsona dagokion taldea
     */
    public String getTaldea() { return taldea; }

    /**
     * Taldea ezartzen du.
     *
     * @param taldea Talde berria
     */
    public void setTaldea(String taldea) { this.taldea = taldea; }

    /**
     * Rola lortzen du.
     *
     * @return Pertsonaren rola
     */
    public String getRola() { return rola; }

    /**
     * Rola ezartzen du.
     *
     * @param rola Rol berria
     */
    public void setRola(String rola) { this.rola = rola; }
}