import java.util.ArrayList;

public abstract class Karakter {
	
	private int id;
	
	private String ad;
	
	private KarakterTip tur;
	
	private Lokasyon lokasyon;
	
	public Karakter() {}
	
	public Karakter(int id, String ad, KarakterTip tur, Lokasyon lokasyon) {
		super();
		this.id = id;
		this.ad = ad;
		this.tur = tur;
		this.lokasyon = lokasyon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public KarakterTip getTur() {
		return tur;
	}

	public void setTur(KarakterTip tur) {
		this.tur = tur;
	}

	public Lokasyon getLokasyon() {
		return lokasyon;
	}

	public void setLokasyon(Lokasyon lokasyon) {
		this.lokasyon = lokasyon;
	}

	public abstract ArrayList<Lokasyon> enKisaYol(int [][] harita, Lokasyon lokasyon);
}
