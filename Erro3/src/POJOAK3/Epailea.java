package POJOAK3;

public class Epailea {

    private String nana;
    private String izenAbizena;
    private String maila;

    public Epailea() {}

    public Epailea(String nana, String izenAbizena, String maila) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.maila = maila;
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

    public String getMaila() {
        return maila;
    }

    public void setMaila(String maila) {
        this.maila = maila;
    }
}