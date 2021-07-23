import java.awt.Color;

public class Gargamel extends Dusman {
	
	private DusmanDavranis dusmanDavranis;

	public Gargamel(DusmanDavranis dusmanDavranis, int dusmanId, String dusmanAdi, Color renk, Lokasyon lokasyon) {
		super(dusmanDavranis,dusmanId,dusmanAdi,renk,DusmanTip.Dusman1,lokasyon);
		this.dusmanDavranis = dusmanDavranis;
	}


}
