import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.border.LineBorder;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		Image gozlukluSirinResim;
		ImageIcon gozlukluSirinIkon = new ImageIcon();
		Image tembelSirinResim;
		ImageIcon tembelSirinIkon = new ImageIcon();

		setTitle("The Smurfs");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 220);
		setResizable(false);
		setLocation(((screenSize.width - getWidth()) / 2), ((screenSize.height - getHeight()) / 2));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		setContentPane(panel);
		panel.setLayout(null);

		JLabel title = new JLabel("Bir oyuncu seçiniz");
		title.setForeground(Color.BLUE);
		title.setBounds(100, 5, 200, 35);

		try {
			gozlukluSirinResim = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/gozlukluSirin.png"))
					.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

			gozlukluSirinIkon = new ImageIcon(gozlukluSirinResim);

			tembelSirinResim = ImageIO.read(this.getClass().getResourceAsStream("/resources/images/tembelSirin.png"))
					.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

			tembelSirinIkon = new ImageIcon(tembelSirinResim);
		} catch (IOException ex) {
			System.out.print(ex);
		}

		JButton buttonGozlukluSirin = new JButton(gozlukluSirinIkon);
		buttonGozlukluSirin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Oyun(new GozlukluSirin(new OyuncuDavranis(2,5,15),1, "Gözlüklü Şirin", 20, new Lokasyon(5, 6)));
				dispose();
			}
		});
		buttonGozlukluSirin.setForeground(Color.WHITE);
		buttonGozlukluSirin.setBackground(Color.WHITE);
		buttonGozlukluSirin.setBounds(50, 50, 100, 100);

		JButton buttonTembelSirin = new JButton(tembelSirinIkon);
		buttonTembelSirin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Oyun(new GozlukluSirin(new OyuncuDavranis(1,5,15),1, "Tembel Şirin", 20, new Lokasyon(5, 6)));
				dispose();
			}
		});
		buttonTembelSirin.setForeground(Color.WHITE);
		buttonTembelSirin.setBackground(Color.WHITE);
		buttonTembelSirin.setBounds(170, 50, 100, 100);

		panel.add(title);
		panel.add(buttonGozlukluSirin);
		panel.add(buttonTembelSirin);

	}
}
