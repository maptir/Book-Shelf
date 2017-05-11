package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import javax.swing.JScrollPane;

public class BookShelfUI extends JFrame {
	JTextField search;
	JLabel searchText;
	JButton box1, box2, box3, box4, add;
	JPanel panel1, panel2, panel3, panelAll;
	JScrollPane scrollPane;
	JLayeredPane layer;
	private JPanel panel;
	boolean direction = true;
	String newFolder;

	public BookShelfUI() {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
	}

	public void initcomponents() {
		this.setSize(1050,650);
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panelAll = new JPanel();

		scrollPane = new JScrollPane();

		layer = new JLayeredPane();

		box1 = new JButton("WORK");
		box2 = new JButton("NOVEL");
		box3 = new JButton("COMIC");
		box4 = new JButton("EDUCATION");
		add = new JButton("+");
		panel = new JPanel();
		searchText = new JLabel("Search: ");
		search = new JTextField(10);

		panel.add(searchText);
		panel.add(search);

		try {
			Image img = ImageIO.read(new File("box.jpg"));
			box1.setIcon(new ImageIcon(img));
			box2.setIcon(new ImageIcon(img));
			box3.setIcon(new ImageIcon(img));
			box4.setIcon(new ImageIcon(img));
			add.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}

		box1.setHorizontalTextPosition(SwingConstants.CENTER);
		box2.setHorizontalTextPosition(SwingConstants.CENTER);
		box3.setHorizontalTextPosition(SwingConstants.CENTER);
		box4.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setHorizontalTextPosition(SwingConstants.CENTER);

		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		panel1.add(box1);
		panel2.add(box2);
		panel1.add(box3);
		panel2.add(box4);
		panel3.add(panel1);
		panel3.add(panel2);
		panel3.add(add);
		panelAll.add(panel3);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFolder = JOptionPane.showInputDialog("Folder Name");
				if (newFolder.chars().allMatch(Character::isLetter))
					addNewFolder(newFolder);
			}
		});

		this.add(panelAll, BorderLayout.CENTER);
		this.add(panel, BorderLayout.NORTH);
	}

	public void addNewFolder(String newFolder) {
		JButton newButton = new JButton(newFolder);
		try {
			Image img = ImageIO.read(new File("box.jpg"));
			newButton.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		newButton.setHorizontalTextPosition(SwingConstants.CENTER);
		if (direction)
			panel1.add(newButton);
		else
			panel2.add(newButton);
		direction = !direction;
		this.pack();
	}

	public void run() {
		this.setVisible(true);
	}
}
