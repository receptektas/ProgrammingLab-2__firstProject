import java.util.ArrayList;

public class Oyuncu extends Karakter {
	
	private int oyuncuId;
	
	private String oyuncuAdi;
	
	private OyuncuTip oyuncuTur;
	
	private int puan;
	
	private OyuncuDavranis oyuncuDavranis;
	
	public Oyuncu() {
		super();
	}
	
	
	public Oyuncu(OyuncuDavranis oyuncuDavranis,int oyuncuId, String oyuncuAdi, OyuncuTip oyuncuTur, int puan, Lokasyon lokasyon) {
		super(oyuncuId,oyuncuAdi,KarakterTip.Oyuncu,lokasyon);
		this.oyuncuDavranis= oyuncuDavranis; 
		this.oyuncuId = oyuncuId;
		this.oyuncuAdi = oyuncuAdi;
		this.oyuncuTur = oyuncuTur;
		this.puan = puan;
	}


	public int getOyuncuId() {
		return oyuncuId;
	}


	public void setOyuncuId(int oyuncuId) {
		this.oyuncuId = oyuncuId;
	}


	public String getOyuncuAdi() {
		return oyuncuAdi;
	}


	public void setOyuncuAdi(String oyuncuAdi) {
		this.oyuncuAdi = oyuncuAdi;
	}


	public OyuncuTip getOyuncuTur() {
		return oyuncuTur;
	}


	public void setOyuncuTur(OyuncuTip oyuncuTur) {
		this.oyuncuTur = oyuncuTur;
	}


	public int getPuan() {
		return puan;
	}


	public void setPuan(int puan) {
		this.puan = puan;
	}


	public OyuncuDavranis getOyuncuDavranis() {
		return oyuncuDavranis;
	}


	public void setOyuncuDavranis(OyuncuDavranis oyuncuDavranis) {
		this.oyuncuDavranis = oyuncuDavranis;
	}


	public int PuaniGoster() {
		return puan;
	}

	@Override
	public ArrayList<Lokasyon> enKisaYol(int[][] harita, Lokasyon lokasyon) {return null;}
}
