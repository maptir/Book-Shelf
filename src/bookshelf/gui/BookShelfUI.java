package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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
import javax.swing.border.EmptyBorder;

import bookshelf.Database;

import javax.swing.JScrollPane;

public class BookShelfUI extends JFrame {
	Database data;
	JTextField search;
	JLabel searchText;
	JButton box1, box2, box3, box4, addFolder;
	JPanel panel1, panel2, panelAll;
	JScrollPane scrollPane;
	JLayeredPane layer;
	private JPanel panelText;
	boolean direction = true;
	String newFolder;

	public BookShelfUI() {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
	}

	public void initcomponents() {
		this.setSize(1050, 650);
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.orange));

		panel1 = new JPanel();
		panel2 = new JPanel();
		panelAll = new JPanel();

		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		layer = new JLayeredPane();

		panelText = new JPanel();
		searchText = new JLabel("Search: ");
		search = new JTextField(10);
		panelText.add(searchText);
		panelText.add(search);

		data = new Database();
		for (String type : data.getTypeList())
			addNewFolder(type);
		try {
			addFolder = new JButton("+");
			Image img = ImageIO.read(new File("box.jpg"));
			addFolder.setIcon(new ImageIcon(img));
			addFolder.setHorizontalTextPosition(SwingConstants.CENTER);
			this.add(addFolder, BorderLayout.EAST);
		} catch (IOException e) {
			e.printStackTrace();
		}

		addFolder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFolder = JOptionPane.showInputDialog("Folder Name");
				if (newFolder == null)
					return;
				if (newFolder.chars().allMatch(Character::isLetter) && !data.getTypeList().contains(newFolder)) {
					addNewFolder(newFolder);
					data.addType(newFolder);
				}
			}
		});
		panel1.setBorder(new EmptyBorder(30, 30, 30, 15));
		panel2.setBorder(new EmptyBorder(30, 15, 30, 30));
		panelAll.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelAll.add(panel1);
		panelAll.add(panel2);
		this.add(panelAll, BorderLayout.CENTER);
		this.add(panelText, BorderLayout.NORTH);
		this.pack();
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
		newButton.addActionListener(pressButton());
		this.pack();
	}

	public ActionListener pressButton() {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(555);
			}
		};
		return action;
	}

	public void run() {
		this.setVisible(true);
	}
}
