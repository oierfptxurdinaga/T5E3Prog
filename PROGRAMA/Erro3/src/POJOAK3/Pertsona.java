package POJOAK3;

public class Pertsona {

    private String nana;
    private String izenAbizena;
    private Integer adina; // Usamos Integer para permitir nulos si fuera necesario
    private String helbidea;
    private String tlfn;
    private String taldea;
    private String rola;

    public Pertsona() {}

    public Pertsona(String nana, String izenAbizena, Integer adina, String helbidea, String tlfn, String taldea, String rola) {
        this.nana = nana;
        this.izenAbizena = izenAbizena;
        this.adina = adina;
        this.helbidea = helbidea;
        this.tlfn = tlfn;
        this.taldea = taldea;
        this.rola = rola;
    }

    public String getNana() { return nana; }
    public void setNana(String nana) { this.nana = nana; }

    public String getIzenAbizena() { return izenAbizena; }
    public void setIzenAbizena(String izenAbizena) { this.izenAbizena = izenAbizena; }

    public Integer getAdina() { return adina; }
    public void setAdina(Integer adina) { this.adina = adina; }

    public String getHelbidea() { return helbidea; }
    public void setHelbidea(String helbidea) { this.helbidea = helbidea; }

    public String getTlfn() { return tlfn; }
    public void setTlfn(String tlfn) { this.tlfn = tlfn; }

    public String getTaldea() { return taldea; }
    public void setTaldea(String taldea) { this.taldea = taldea; }

    public String getRola() { return rola; }
    public void setRola(String rola) { this.rola = rola; }
}