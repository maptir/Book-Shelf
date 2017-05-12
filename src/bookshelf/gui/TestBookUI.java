package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;




public class TestBookUI implements Runnable {
	private JFrame frame;
	private JLabel background;
	private JPanel panelOfTop;
	private JPanel panelEast;
	private JPanel panelWest;
	private JPanel panelOfEastImage;
	private JLabel backgroundForNorth;
	private JLabel searchLabel;
	private JTextField searchField;
	private JLabel typeLabel;
	private JComboBox<String> typeChooser;
	private JButton addFolder;
	private JButton addBook;

	public TestBookUI() {
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame("Book-Shelf");
		frame.setPreferredSize((new Dimension(1200, 800)));
		searchLabel = new JLabel("Search :");
		searchField = new JTextField(20);
		panelOfTop = new JPanel();
		background = new JLabel(new ImageIcon("backG.jpg"));
		typeChooser = new JComboBox<>();
		typeLabel = new JLabel("Type :");
		panelEast = new JPanel();
		panelWest = new JPanel();
		panelOfEastImage = new JPanel();
		addFolder = new JButton();
		addBook = new JButton();

		backgroundForNorth = new JLabel(new ImageIcon("backG.jpg"));
		backgroundForNorth.setLayout(new FlowLayout());
		backgroundForNorth.add(searchLabel);
		backgroundForNorth.add(searchField);
		backgroundForNorth.add(typeLabel);
		backgroundForNorth.add(typeChooser);

		ImageIcon icon = new ImageIcon("Picture//Add.png");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(340, 600, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		
		ImageIcon icon2 = new ImageIcon("Picture//AddBook.png");
		Image img2 = icon2.getImage();
		Image newimg2 = img2.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
		icon2 = new ImageIcon(newimg2);
		
		addBook.setIcon(icon2);
		addBook.setPreferredSize((new Dimension(200, 210)));
		addBook.setBorder(new EmptyBorder(10, 10, 10, 10));
		addBook.setHorizontalAlignment(SwingConstants.CENTER);
		addBook.setBackground(new Color(220, 169, 125));
		addFolder.setIcon(icon);
		addFolder.setPreferredSize((new Dimension(200, 220)));
		addFolder.setBorder(new EmptyBorder(90, 0, 0, 10));
		addFolder.setHorizontalAlignment(SwingConstants.CENTER);

		panelOfTop.add(backgroundForNorth);
		panelOfTop.setPreferredSize(new Dimension(200, 100));
		panelOfEastImage.setLayout(new BoxLayout(panelOfEastImage, BoxLayout.Y_AXIS));
		panelOfEastImage.add(addBook);
		panelOfEastImage.add(addFolder);


		panelEast.add(panelOfEastImage);

		frame.setContentPane(background);
		frame.setLayout(new BorderLayout());
		frame.add(panelOfTop, BorderLayout.NORTH);
		frame.add(panelEast, BorderLayout.EAST);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.pack();
	}

	@Override
	public void run() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		TestBookUI r = new TestBookUI();
		r.run();
	}

}
