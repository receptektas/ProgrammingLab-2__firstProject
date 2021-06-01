import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

public class Dusman extends Karakter {

	private int dusmanId;

	private String dusmanAdi;

	private DusmanTip dusmanTur;

	private Color renk;

	private DusmanDavranis dusmanDavranis;

	private Lokasyon ilkYer;
	
	public Dusman() {
	}

	public Dusman(DusmanDavranis dusmanDavranis, int dusmanId, String dusmanAdi, Color renk, DusmanTip dusmanTur,
			Lokasyon lokasyon) {
		super(dusmanId, dusmanAdi, KarakterTip.Dusman, lokasyon);
		this.dusmanDavranis = dusmanDavranis;
		this.dusmanId = dusmanId;
		this.dusmanAdi = dusmanAdi;
		this.dusmanTur = dusmanTur;
		this.renk = renk;
	}

	public int getDusmanId() {
		return dusmanId;
	}

	public void setDusmanId(int dusmanId) {
		this.dusmanId = dusmanId;
	}

	public String getDusmanAdi() {
		return dusmanAdi;
	}

	public void setDusmanAdi(String dusmanAdi) {
		this.dusmanAdi = dusmanAdi;
	}

	public DusmanTip getDusmanTur() {
		return dusmanTur;
	}

	public void setDusmanTur(DusmanTip dusmanTur) {
		this.dusmanTur = dusmanTur;
	}

	public Color getRenk() {
		return renk;
	}

	public void setRenk(Color renk) {
		this.renk = renk;
	}

	public DusmanDavranis getDusmanDavranis() {
		return dusmanDavranis;
	}

	public void setDusmanDavranis(DusmanDavranis dusmanDavranis) {
		this.dusmanDavranis = dusmanDavranis;
	}

	public Lokasyon getIlkYer() {
		return ilkYer;
	}

	public void setIlkYer(Lokasyon ilkYer) {
		this.ilkYer= ilkYer;
	}

	@Override
	public ArrayList<Lokasyon> enKisaYol(int[][] harita, Lokasyon oyuncuLokasyon) {
		ArrayList<Lokasyon> lokasyonlar = new ArrayList<Lokasyon>();
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 13; j++) {
				if (this.getLokasyon().getX() == i && this.getLokasyon().getY() == j) {
					this.getLokasyon().setValue(0);
					lokasyonlar.add(this.getLokasyon());
				} else {
					Lokasyon lokasyon = new Lokasyon(i, j);
					lokasyon.setValue(Integer.MAX_VALUE);
					lokasyonlar.add(lokasyon);
				}

			}
		}

		ArrayList<Lokasyon> liste = new ArrayList<Lokasyon>();
		Hashtable<Lokasyon, Lokasyon> tablo = new Hashtable<Lokasyon, Lokasyon>();

		liste.add(this.getLokasyon());

		Lokasyon ilkLokasyon = this.getLokasyon();
		int ilkLokasyonDeger = 0;

		for (int i = 1; i < lokasyonlar.size(); i++) {
			ArrayList<Lokasyon> komsular = komsuBul(harita, oyuncuLokasyon, lokasyonlar, ilkLokasyon);
			for (Lokasyon komsu : komsular)
				if (!liste.contains(komsu)) {
					int yeniDeger = ilkLokasyonDeger + 1;
					if (yeniDeger < komsu.getValue()) {
						komsu.setValue(yeniDeger);
						tablo.put(komsu, ilkLokasyon);
					}
				}
			Lokasyon yeniLokasyon = null;
			int yeniLokasyonDeger = Integer.MAX_VALUE;
			for (Lokasyon l : lokasyonlar)
				if (!liste.contains(l)) {
					int piV = l.getValue();
					if (piV < yeniLokasyonDeger) {
						yeniLokasyon = l;
						yeniLokasyonDeger = piV;
					}
				}
			if (yeniLokasyon == null)
				break;
			ilkLokasyon = yeniLokasyon;
			ilkLokasyonDeger = yeniLokasyonDeger;
			liste.add(ilkLokasyon);

		}

		ArrayList<Lokasyon> yol = new ArrayList<Lokasyon>();
		Lokasyon lokasyon = null;

		for (Lokasyon l : lokasyonlar) {
			if (l.getX() == oyuncuLokasyon.getX() && l.getY() == oyuncuLokasyon.getY()) {
				lokasyon = l;
				break;
			}
		}

		while (lokasyon != null) {
			yol.add(lokasyon);
			lokasyon = tablo.get(lokasyon);
		}
		return yol;
	}

	private ArrayList<Lokasyon> komsuBul(int[][] harita, Lokasyon oyuncuLokasyon, ArrayList<Lokasyon> lokasyonlar,
			Lokasyon current) {

		ArrayList<Lokasyon> komsular = new ArrayList<Lokasyon>();
		
		if (current.getX() - 1 > 0) {
			for (Lokasyon lokasyon : lokasyonlar) {
				if (harita[current.getX() - 1][current.getY()] == 1 && lokasyon.getX() == current.getX() - 1
						&& lokasyon.getY() == current.getY()) {
					komsular.add(lokasyon);
					break;
				}
			}
		}

		if (current.getX() + 1 < 11) {
			for (Lokasyon lokasyon : lokasyonlar) {
				if (harita[current.getX() + 1][current.getY()] == 1 && lokasyon.getX() == current.getX() + 1
						&& lokasyon.getY() == current.getY()) {
					komsular.add(lokasyon);
					break;
				}
			}
		}

		if (current.getY() - 1 > 0) {
			for (Lokasyon lokasyon : lokasyonlar) {
				if (harita[current.getX()][current.getY() - 1] == 1 && lokasyon.getX() == current.getX()
						&& lokasyon.getY() == current.getY() - 1) {
					komsular.add(lokasyon);
					break;
				}
			}
		}

		if (current.getY() + 1 < 13) {
			for (Lokasyon lokasyon : lokasyonlar) {
				if (harita[current.getX()][current.getY() + 1] == 1 && lokasyon.getX() == current.getX()
						&& lokasyon.getY() == current.getY() + 1) {
					komsular.add(lokasyon);
					break;
				}
			}
		}

		return komsular;
	}

}
