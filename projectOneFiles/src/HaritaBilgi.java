import java.awt.*;
import java.util.ArrayList;

public class HaritaBilgi {
	
	private int[][] harita;
	
	private Oyuncu oyuncu;
		
	private ArrayList<Dusman> dusmanlar;
	
	public HaritaBilgi(int[][] harita, Oyuncu oyuncu, ArrayList<Dusman> dusmanlar) {
		super();
		this.harita = harita;
		this.oyuncu = oyuncu;
		this.dusmanlar = dusmanlar;	
	}

	public int[][] getHarita() {
		return harita;
	}
	
	public Oyuncu getOyuncu() {
		return oyuncu;
	}

	public ArrayList<Dusman> getDusmanlar() {
		return dusmanlar;
	}

}
