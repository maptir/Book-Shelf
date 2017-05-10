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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class BookShelfUI extends JFrame {
	JTextField search;
	JLabel searchText;
	JButton box1, box2, box3, box4, add;
	JPanel panel1, panel2, panelAll;
	private final JScrollPane scrollPane = new JScrollPane();
	private JPanel panel;
	boolean direction = true;

	String newFolder = "";

	public BookShelfUI() {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
	}

	public void initcomponents() {
		panel1 = new JPanel();
		panel2 = new JPanel();
		panelAll = new JPanel();
		box1 = new JButton("WORK");
		box2 = new JButton("NOVEL");
		box3 = new JButton("COMIC");
		box4 = new JButton("EDUCATION");
		add = new JButton("+");
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
		panelAll.add(panel1);
		panelAll.add(panel2);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newFolder = JOptionPane.showInputDialog("Folder Name");
				if (newFolder != "")
					addNewFolder(newFolder);
			}
		});
		getContentPane().add(panelAll, BorderLayout.CENTER);
		panelAll.add(scrollPane);

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		searchText = new JLabel("Search: ");
		panel.add(searchText);
		search = new JTextField(10);
		panel.add(search);
		getContentPane().add(add, BorderLayout.EAST);
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
		this.pack();
	}

	public void run() {
		this.setVisible(true);
	}
}
