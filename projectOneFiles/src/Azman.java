import java.awt.Color;

public class Azman extends Dusman {

	private DusmanDavranis dusmanDavranis;

	public Azman(DusmanDavranis dusmanDavranis, int dusmanId, String dusmanAdi, Color renk, Lokasyon lokasyon) {
		super(dusmanDavranis,dusmanId,dusmanAdi,renk,DusmanTip.Dusman2,lokasyon);
		this.dusmanDavranis = dusmanDavranis;
	}
}
