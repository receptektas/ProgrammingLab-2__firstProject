
public class GozlukluSirin extends Oyuncu {
	
	private OyuncuDavranis oyuncuDavranis;

	public GozlukluSirin() {
		super();
	}
	
	public GozlukluSirin(OyuncuDavranis oyuncuDavranis, int oyuncuId, String oyuncuAdi, int skor, Lokasyon lokasyon) {
		super(oyuncuDavranis,oyuncuId, oyuncuAdi, OyuncuTip.Oyuncu1, skor, lokasyon);
		this.oyuncuDavranis = oyuncuDavranis;
	}

}
