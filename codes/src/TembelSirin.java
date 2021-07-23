
public class TembelSirin extends Oyuncu {
	
	private OyuncuDavranis oyuncuDavranis;

	public TembelSirin() {
		super();
	}
	
	public TembelSirin(OyuncuDavranis oyuncuDavranis, int oyuncuId, String oyuncuAdi, int skor, Lokasyon lokasyon) {
		super(oyuncuDavranis,oyuncuId, oyuncuAdi, OyuncuTip.Oyuncu2, skor, lokasyon);
		this.oyuncuDavranis = oyuncuDavranis;
	}
}
