
public class Obje {
	
	private String objeAdi;
	
	private int puan;
	
    private int maksSure;
	
	private int beklemeSure;
	
	private boolean aktif;
	
	private Lokasyon lokasyon;

	public Obje() {
		
	}

	public Obje(String objeAdi, int puan, int maksSure, int beklemeSure, boolean aktif, Lokasyon lokasyon) {
		this.objeAdi = objeAdi;
		this.puan = puan;
		this.maksSure = maksSure;
		this.beklemeSure = beklemeSure;
		this.aktif = aktif;
		this.lokasyon = lokasyon;
	}

	public String getObjeAdi() {
		return objeAdi;
	}

	public void setObjeAdi(String objeAdi) {
		this.objeAdi = objeAdi;
	}

	public int getPuan() {
		return puan;
	}

	public void setPuan(int puan) {
		this.puan = puan;
	}

	public int getMaksSure() {
		return maksSure;
	}

	public void setMaksSure(int maksSure) {
		this.maksSure = maksSure;
	}

	public int getBeklemeSure() {
		return beklemeSure;
	}

	public void setBeklemeSure(int beklemeSure) {
		this.beklemeSure = beklemeSure;
	}

	public boolean isAktif() {
		return aktif;
	}

	public void setAktif(boolean aktif) {
		this.aktif = aktif;
	}

	public Lokasyon getLokasyon() {
		return lokasyon;
	}

	public void setLokasyon(Lokasyon lokasyon) {
		this.lokasyon = lokasyon;
	}

	
	
	
}
