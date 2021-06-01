
public class Puan extends Oyuncu {

	
	private Oyuncu oyuncu;
	
	private Dusman dusman;
	

	public Puan(Oyuncu oyuncu, Dusman dusman) {
		this.oyuncu = oyuncu;
		this.dusman = dusman;
	}


	@Override
	public int PuaniGoster() {
		if (dusman.getDusmanTur() == DusmanTip.Dusman1) {
			oyuncu.setPuan(oyuncu.getPuan() - oyuncu.getOyuncuDavranis().getPuanGargamel());

		} else if (dusman.getDusmanTur() == DusmanTip.Dusman2) {
			oyuncu.setPuan(oyuncu.getPuan() - oyuncu.getOyuncuDavranis().getPuanAzman());
		}
		return oyuncu.getPuan();
	}
	
}
