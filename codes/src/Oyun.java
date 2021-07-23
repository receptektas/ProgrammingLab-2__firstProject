import javax.imageio.ImageIO;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static javax.swing.JOptionPane.showMessageDialog;

public class Oyun extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel puan;
	private int[][] harita;
	private Oyuncu oyuncu;
	private ArrayList<Dusman> dusmanlar;
	private JButton[][] buttons;
	private Random rand = new Random();
	private ArrayList<Karakter> karakterler = new ArrayList<Karakter>();
	private ArrayList<Obje> altinlar = new ArrayList<Obje>();
	private ArrayList<Lokasyon> eskiYollar = new ArrayList<Lokasyon>();
	private Obje mantar;
	private int sayiYukseklik = 11;
	private int sayiGenislik = 13;
	private int altinSayi = 5;

	public Oyun(Oyuncu oyuncu) {
		setTitle("The Smurfs");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		getContentPane().setBackground(Color.white);

		setSize(590, 460);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		ImageIcon sirineIcon = getIconImage("sirine", 40, 40);
		JLabel sirine = new JLabel(sirineIcon);
		sirine.setBounds(540, 260, 40, 40);

		puan = new JLabel("Puan : " + oyuncu.PuaniGoster());
		puan.setForeground(Color.BLUE);
		puan.setBounds(20, 5, 200, 10);

		panel.add(puan);
		panel.add(sirine);
		add(panel);

		this.oyuncu = oyuncu;
		readFile("/resources/harita.txt");

		haritaOlustur(panel);
		haritaGuncelle();
		dusmanYol(null,false);

		Thread altin = new Thread(() -> {
			altinGoster();
		});
		altin.start();
		Thread mantar = new Thread(() -> {
			mantarGoster();
		});
		mantar.start();

		setVisible(true);

	}

	private synchronized void dusmanYol(Dusman item, boolean ilerlet) {
		ArrayList<Lokasyon> yol;
		if (eskiYollar != null) {
			for (Lokasyon lokasyon : eskiYollar) {
				buttons[lokasyon.getX()][lokasyon.getY()].setBackground(Color.WHITE);
			}
			eskiYollar.clear();
		}
		for (Dusman dusman : dusmanlar) {
			if(!dusman.equals(item)) {
				yol = dusman.enKisaYol(harita, oyuncu.getLokasyon());
				eskiYollar.addAll(yol);
				for (Lokasyon lokasyon : yol) {
					buttons[lokasyon.getX()][lokasyon.getY()].setBackground(dusman.getRenk());
				}
				if (ilerlet)
					dusmanIlerle(dusman, yol);
			}
		}
	}
	


	private synchronized void dusmanIlerle(Dusman dusman, ArrayList<Lokasyon> yol) {
		Lokasyon yeniLokasyon;
		if (yol.size() > dusman.getDusmanDavranis().getBirim()) {
			yeniLokasyon = yol.get(yol.size() - dusman.getDusmanDavranis().getBirim() - 1);
			for (int i = 0; i < dusman.getDusmanDavranis().getBirim(); i++) {
				if(buttons[yol.get(yol.size() - i - 1).getX()][yol.get(yol.size() - i - 1).getY()].getBackground() == dusman.getRenk() )
					buttons[yol.get(yol.size() - i - 1).getX()][yol.get(yol.size() - i - 1).getY()].setBackground(Color.WHITE);
			}
			if (isAvailable(yeniLokasyon.getX(), yeniLokasyon.getY(), false)) {
				buttons[yeniLokasyon.getX()][yeniLokasyon.getY()]
						.setIcon(buttons[dusman.getLokasyon().getX()][dusman.getLokasyon().getY()].getIcon());
				buttons[dusman.getLokasyon().getX()][dusman.getLokasyon().getY()].setIcon(null);
				dusman.setLokasyon(yeniLokasyon);
			} else if (yeniLokasyon.getX() == oyuncu.getLokasyon().getX()
					&& yeniLokasyon.getY() == oyuncu.getLokasyon().getY()) {
				cakisma(dusman);
			}
			dusmanYol(null,false);
		}
	}

	private synchronized void cakisma(Dusman dusman) {
		Oyuncu oyuncuPuan =  new Puan(this.oyuncu,dusman);
		int puan = oyuncuPuan.PuaniGoster();
		Thread thread3 = new Thread(() -> {
			puanGuncelle(puan);
		});
		thread3.start();
		if (puan <= 0) {
			showMessageDialog(null, "Oyun Bitti. Kaybettiniz!");
			dispose();
			System.exit(0);
		} else {
			buttons[dusman.getIlkYer().getX()][dusman.getIlkYer().getY()]
					.setIcon(buttons[dusman.getLokasyon().getX()][dusman.getLokasyon().getY()].getIcon());
			if(dusman.getLokasyon().getX() == dusman.getIlkYer().getX() && dusman.getLokasyon().getY() == dusman.getIlkYer().getY()) {

			} else {
				buttons[dusman.getLokasyon().getX()][dusman.getLokasyon().getY()].setIcon(null);
			}
			dusman.setLokasyon(dusman.getIlkYer());
		}

	}

	private void altinGoster() {
		int randx = 0, randy = 0;
		try {
			while (true) {
				Thread.sleep((rand.nextInt(10) + 1) * 1000);
				for (int i = 0; i < altinSayi; i++) {
					randx = 0;
					randy = 0;
					while (harita[randx][randy] == 0 || !isAvailable(randx, randy, true)
							|| doorInfo(randx, randy) != "") {
						randx = rand.nextInt(sayiYukseklik);
						randy = rand.nextInt(sayiGenislik);
					}
					altinlar.add(new Altin("Altin", 5, 10, 5, true, new Lokasyon(randx, randy)));
				}
				for (Obje altin : altinlar) {
					if (isAvailable(altin.getLokasyon().getX(), altin.getLokasyon().getY(), false)) {
						buttons[altin.getLokasyon().getX()][altin.getLokasyon().getY()]
								.setIcon(getIconImage("altin", 25, 25));
					} else if (altin.getLokasyon().getX() == oyuncu.getLokasyon().getX()
							&& altin.getLokasyon().getY() == oyuncu.getLokasyon().getY()) {
						oyuncu.setPuan(oyuncu.getPuan() + altin.getPuan());
						puanGuncelle(oyuncu.PuaniGoster());
					}

				}
				Thread.sleep(5 * 1000);
				for (Obje altin : altinlar) {
					if (altin.isAktif() && isAvailable(altin.getLokasyon().getX(), altin.getLokasyon().getY(), false)) {
						buttons[altin.getLokasyon().getX()][altin.getLokasyon().getY()].setIcon(null);
					}
				}
				altinlar.clear();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void mantarGoster() {
		int randx = 0, randy = 0;
		try {
			while (true) {
				Thread.sleep((rand.nextInt(20) + 1) * 1000);
				randx = 0;
				randy = 0;
				while (harita[randx][randy] == 0 || !isAvailable(randx, randy, true) || doorInfo(randx, randy) != "") {
					randx = rand.nextInt(sayiYukseklik);
					randy = rand.nextInt(sayiGenislik);
				}
				mantar = new Mantar("Mantar", 50, 20, 7, true, new Lokasyon(randx, randy));
				if (isAvailable(mantar.getLokasyon().getX(), mantar.getLokasyon().getY(), false)) {
					buttons[mantar.getLokasyon().getX()][mantar.getLokasyon().getY()]
							.setIcon(getIconImage("mantar", 25, 25));
				} else if (mantar.getLokasyon().getX() == oyuncu.getLokasyon().getX()
						&& mantar.getLokasyon().getY() == oyuncu.getLokasyon().getY()) {
					oyuncu.setPuan(oyuncu.getPuan() + mantar.getPuan());
					puanGuncelle(oyuncu.PuaniGoster());
				}

				Thread.sleep(mantar.getBeklemeSure() * 1000);
				if (mantar.isAktif() && isAvailable(mantar.getLokasyon().getX(), mantar.getLokasyon().getY(), false)) {
					buttons[mantar.getLokasyon().getX()][mantar.getLokasyon().getY()].setIcon(null);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isAvailable(int x, int y, boolean check) {
		karkaterListeGuncelle();
		for (Karakter karakter : karakterler) {
			if (karakter.getLokasyon().getX() == x && karakter.getLokasyon().getY() == y)
				return false;
		}
		if (check) {
			for (Obje obje : altinlar) {
				if (obje.getLokasyon().getX() == x && obje.getLokasyon().getY() == y)
					return false;
			}
			if (mantar != null) {
				if (mantar.getLokasyon().getX() == x && mantar.getLokasyon().getY() == y)
					return false;
			}
		}

		return true;
	}

	private synchronized void ilerle(int keyCode) throws InterruptedException {
		int x, y, birim;
		x = oyuncu.getLokasyon().getX();
		y = oyuncu.getLokasyon().getY();
		birim = oyuncu.getOyuncuDavranis().getBirim();
		Dusman dusman;
		Dusman temp;
		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (x - birim > 0) {
				if (harita[x - birim][y] != 0) {
					dusman = null;
					for (int i = 1; i <= birim; i++) {
						if (harita[x - i][y] != 0) {
							objeKontrol(x - i, y);
							temp = dusmanKontrol(x - i, y);
							if(temp != null) {
								dusman  = temp;
							}
							oyuncu.getLokasyon().setX(x - i);
							buttons[x - i][y].setIcon(buttons[x - i + 1][y].getIcon());
							buttons[x - i + 1][y].setIcon(null);
							Thread.sleep(250);
							if(i == birim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}

					}
					
				}
			}
			break;
		case KeyEvent.VK_DOWN:
			if (x + birim < sayiYukseklik) {
				if (harita[x + birim][y] != 0) {
					dusman = null;
					for (int i = 1; i <= birim; i++) {
						if (harita[x + i][y] != 0) {
							objeKontrol(x + i, y);
							temp = dusmanKontrol(x + i, y);
							if(temp != null) {
								dusman  = temp;
							}
							oyuncu.getLokasyon().setX(x + i);
							buttons[x + i][y].setIcon(buttons[x + i - 1][y].getIcon());
							buttons[x + i - 1][y].setIcon(null);
							Thread.sleep(250);
							if(i == birim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (y + birim < sayiGenislik) {
				if (harita[x][y + birim] != 0) {
					dusman = null;
					for (int i = 1; i <= birim; i++) {
						if (harita[x][y + i] != 0) {
							objeKontrol(x, y + i);
							temp = dusmanKontrol(x, y + i);
							if(temp != null) {
								dusman  = temp;
							}						
							oyuncu.getLokasyon().setY(y + i);
							buttons[x][y + i].setIcon(buttons[x][y + i - 1].getIcon());
							buttons[x][y + i - 1].setIcon(null);
							Thread.sleep(250);
							if (x == 7 && (y + i) == 12) {
								showMessageDialog(null, "Oyun Bitti. Tebrikler!");
								dispose();
								System.exit(0);
							}
							if(i == birim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		case KeyEvent.VK_LEFT:
			if (y - birim > 0) {
				if (harita[x][y - birim] != 0) {
					dusman = null;
					for (int i = 1; i <= birim; i++) {
						if (harita[x][y - i] != 0) {
							objeKontrol(x, y - i);
							temp = dusmanKontrol(x, y - i);
							if(temp != null) {
								dusman  = temp;
							}
							oyuncu.getLokasyon().setY(y - i);
							buttons[x][y - i].setIcon(buttons[x][y - i + 1].getIcon());
							buttons[x][y - i + 1].setIcon(null);
							Thread.sleep(250);
							if(i == birim) {
								dusmanYol(dusman,true);
							}
						} else {
							break;
						}
					}
				}
			}
			break;
		}
	}

	private void puanGuncelle(int oyuncuPuan) {
		puan.setText("Puan : "+ oyuncuPuan);
	}

	private void objeKontrol(int x, int y) {
		for (Obje obje : altinlar) {
			if (obje.getLokasyon().getX() == x && obje.getLokasyon().getY() == y && obje.isAktif()) {
				oyuncu.setPuan(oyuncu.getPuan() + obje.getPuan());
				obje.setAktif(false);
				Thread thread3 = new Thread(() -> {
					puanGuncelle(oyuncu.PuaniGoster());
				});
				thread3.start();
			}
		}
		if (mantar != null) {
			if (mantar.getLokasyon().getX() == x && mantar.getLokasyon().getY() == y && mantar.isAktif()) {
				oyuncu.setPuan(oyuncu.getPuan() + mantar.getPuan());
				mantar.setAktif(false);
				Thread thread4 = new Thread(() -> {
					puanGuncelle(oyuncu.PuaniGoster());
				});
				thread4.start();

			}
		}
	}

	private Dusman dusmanKontrol(int x, int y) {
		Dusman dusman = null;
		for (Dusman item : dusmanlar) {
			if (item.getLokasyon().getX() == x && item.getLokasyon().getY() == y) {
				dusman = item;
				break;
			}
		}
		if (dusman != null) {
			cakisma(dusman);
			return dusman;
		}
		return null;

	}

	private void karkaterListeGuncelle() {
		karakterler.clear();
		karakterler.add(oyuncu);
		karakterler.addAll(dusmanlar);
	}

	private void haritaOlustur(JPanel panel) {
		int buttonWidth = 40;
		int buttonHeight = 35;
		String door;
		karkaterListeGuncelle();
		buttons = new JButton[sayiYukseklik][sayiGenislik];
		for (int i = 0; i < sayiYukseklik; i++) {
			for (int j = 0; j < sayiGenislik; j++) {
				door = doorInfo(i, j);
				buttons[i][j] = new JButton();
				buttons[i][j].setForeground(Color.BLACK);
				buttons[i][j].setBorder(new LineBorder(Color.BLACK));

				if (harita[i][j] == 0) {
					buttons[i][j].setBackground(new Color(152, 152, 152));
				} else if (door != "") {
					buttons[i][j].setBackground(new Color(255, 51, 51));
					for (Karakter karakter : karakterler) {
						if (karakter.getLokasyon().getX() == i && karakter.getLokasyon().getY() == j) {
							buttons[i][j].setBackground(Color.WHITE);
						} else {
							buttons[i][j].setText(door);
						}
					}
					if(buttons[i][j].getBackground() != Color.WHITE) {
						harita[i][j] = 0;
					}
				} else {
					buttons[i][j].setBackground(Color.WHITE);
				}

				buttons[i][j].setBounds(20 + j * buttonWidth, 20 + i * buttonHeight, buttonWidth, buttonHeight);
				buttons[i][j].addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {

					}

					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN
								|| e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
							Thread thread = new Thread(() -> {
								try {
									ilerle(e.getKeyCode());
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
							});
							thread.start();
						}
					}

					@Override
					public void keyReleased(KeyEvent e) {

					}
				});

				panel.add(buttons[i][j]);
			}
		}
	}

	private void haritaGuncelle() {
		String imageName = "";
		int x, y;
		karkaterListeGuncelle();
		for (Karakter karakter : karakterler) {
			x = karakter.getLokasyon().getX();
			y = karakter.getLokasyon().getY();
			switch (karakter.getAd()) {
			case "Gargamel":
				imageName = "gargamel";
				break;
			case "Azman":
				imageName = "azman";
				break;
			case "Gözlüklü Şirin":
				imageName = "gozlukluSirin";
				break;
			case "Tembel Şirin":
				imageName = "tembelSirin";
				break;
			}
			buttons[x][y].setText("");
			buttons[x][y].setIcon(getIconImage(imageName, 25, 25));

		}
	}

	private ImageIcon getIconImage(String imageName, int witdh, int height) {
		Image image;
		ImageIcon imageIcon = null;
		try {
			image = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/" + imageName + ".png"))
					.getScaledInstance(witdh, height, java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(image);
		} catch (IOException ex) {
			System.out.print(ex);
		}
		return imageIcon;
	}

	private String doorInfo(int i, int j) {
		if (i == 0 && j == 3) {
			return "A";
		} else if (i == 0 && j == 10) {
			return "B";
		} else if (i == 5 && j == 0) {
			return "C";
		} else if (i == 10 && j == 3) {
			return "D";
		} else if (i == 5 && j == 6) {
			return "S";
		} else {
			return "";
		}
	}

	private void readFile(String filePath) {
		Dusman dusman;
		Lokasyon lokasyon = new Lokasyon(0, 0);
		harita = new int[sayiYukseklik][sayiGenislik];
		dusmanlar = new ArrayList<Dusman>();
		int dusmanSayi = 0, satirSayi = 0, dusmanId;
		String dusmanAd = "";
		try {
			Scanner scn = new Scanner(this.getClass().getResourceAsStream(filePath));
			String satir;
			while (scn.hasNextLine()) {
				satir = scn.nextLine();
				if (satir.startsWith("Karakter:")) {
					dusmanId = dusmanSayi++;
					for (var item : satir.split(",")) {
						item = item.substring(item.indexOf(":") + 1);
						if (item.length() > 1) {
							dusmanAd = item;
						} else {
							switch (item) {
							case "A":
								lokasyon = new Lokasyon(0, 3);
								break;
							case "B":
								lokasyon = new Lokasyon(0, 10);
								break;
							case "C":
								lokasyon = new Lokasyon(5, 0);
								break;
							case "D":
								lokasyon = new Lokasyon(10, 3);
								break;
							} 
						}
					}
					if (dusmanAd.startsWith("Gargamel")) {
						dusman = new Gargamel(new DusmanDavranis(2, true), dusmanId, dusmanAd,
								new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), lokasyon);
						dusman.setIlkYer(lokasyon);
						dusmanlar.add(dusman);
					} else if (dusmanAd.startsWith("Azman")) {
						dusman = new Azman(new DusmanDavranis(1, false), dusmanId, dusmanAd,
								new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()), lokasyon);
						dusman.setIlkYer(lokasyon);
						dusmanlar.add(dusman);
					}

				} else {
					int i = 0;
					for (var item : satir.split("	")) {
						harita[satirSayi][i] = Integer.parseInt(item);
						i++;
					}
					satirSayi++;
				}
			}
		} catch (Exception e) {
			System.err.println("Error:" + e);
		}
	}

}
