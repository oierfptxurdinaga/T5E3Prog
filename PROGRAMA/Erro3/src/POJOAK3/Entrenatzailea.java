package POJOAK3;

public class Entrenatzailea {

    private String nana;
    private String izenAbizena;
    private String titulazioa;
    private String taldea;

    public Entrenatzailea() {}

    public Entrenatzailea(String nana, String izenAbizena, String titulazioa, String taldea) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.titulazioa = titulazioa;
        this.taldea = taldea;
    }

    public String getNana() { return nana; }
    public void setNana(String nana) { this.nana = nana; }

    public String getIzenAbizena() { return izenAbizena; }
    public void setIzenAbizena(String izenAbizena) { this.izenAbizena = izenAbizena; }

    public String getTitulazioa() { return titulazioa; }
    public void setTitulazioa(String titulazioa) { this.titulazioa = titulazioa; }

    public String getTaldea() { return taldea; }
    public void setTaldea(String taldea) { this.taldea = taldea; }
}