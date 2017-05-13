package bookshelf.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import bookshelf.Database;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BookShelfUI extends JFrame {
	Database data;
	JTextField search;
	JLabel searchText, logoLabel, backGroundLabel;
	JButton box1, box2, box3, box4, addFolder, addImageadd;
	JPanel panel1, panel2, panelAdd, panelButton, panelAll;
	JScrollPane scrollPane;
	JLayeredPane layer;
	Image logo, backGround, addImage, addType;
	private JPanel panelText;
	boolean direction = true;
	String newFolder;

	public BookShelfUI() throws IOException {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initcomponents();
	}

	public void initcomponents() throws IOException {
		this.setSize(900, 700);
		this.setLocation(230, 20);

		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.orange));
		logo = ImageIO.read(new File("Picture//Logo.png"));
		addImage = ImageIO.read(new File("Picture//Add.png"));
		addType = ImageIO.read(new File("Picture//AddBook.png"));
		// backGround = ImageIO.read(new File("Picture//background.jpg"));
		// backGroundLabel = new JLabel(new ImageIcon(backGround));
		// getRootPane().setContentPane(backGroundLabel);
		logoLabel = new JLabel(new ImageIcon(logo.getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
		addImageadd = new JButton();
		addImageadd.setIcon(new ImageIcon(addType.getScaledInstance(90, 70, Image.SCALE_DEFAULT)));
		getContentPane().setLayout(new FlowLayout());

		panel1 = new JPanel();
		panel2 = new JPanel();
		panelAdd = new JPanel();
		panelAll = new JPanel();
		panelButton = new JPanel();

		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		panelText = new JPanel();
		searchText = new JLabel("SEARCH : ");
		searchText.setFont(new Font("Rockwell", 1, 29));
		search = new JTextField(15);
		panelText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		addImageadd.setBorderPainted(false);
		panelText.add(logoLabel);
		panelText.add(searchText);
		panelText.add(search);

		data = new Database();
		for (String type : data.getTypeList())
			addNewFolder(type);

		addFolder = new JButton();
		addFolder.setIcon(new ImageIcon(addImage.getScaledInstance(110, 90, Image.SCALE_DEFAULT)));
		addFolder.setBorder(new EmptyBorder(0, 30, 0, 0));
		panelAdd.add(addFolder);
		panelAdd.add(addImageadd);
		panelAdd.setLayout(new BoxLayout(panelAdd, BoxLayout.Y_AXIS));
		addImageadd.setBorderPainted(false);
		addImageadd.setContentAreaFilled(false);
		addFolder.setBorderPainted(false);
		addFolder.setContentAreaFilled(false);

		addImageadd.addActionListener((e) -> addType("", "", "", ""));
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

		panel1.setBorder(new EmptyBorder(30, 30, 30, 30));
		panel2.setBorder(new EmptyBorder(30, 30, 30, 30));
		panelButton.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
		panelButton.setPreferredSize(new Dimension(600, 500));
		panelButton.setLayout(new FlowLayout());
		panelButton.add(panel1, BorderLayout.WEST);
		panelButton.add(panel2, BorderLayout.EAST);
		scrollPane = new JScrollPane(panelButton);

		panelAll.setLayout(new BorderLayout());
		panelAll.add(panelText, BorderLayout.NORTH);
		panelAll.add(scrollPane, BorderLayout.CENTER);
		panelAll.add(panelAdd, BorderLayout.EAST);

		this.add(panelAll);
	}

	public void addNewFolder(String newFolder) {
		JButton newButton = new JButton(newFolder);
		newButton.setFont(new Font("Rockwell", 0, 20));
		try {
			Image img = ImageIO.read(new File("Picture//folder.png")).getScaledInstance(120, 120, Image.SCALE_DEFAULT);
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
		newButton.setBorderPainted(false);
		newButton.setContentAreaFilled(false);
		newButton.addActionListener(pressButton());
		this.setSize(900, 700);
	}

	public ActionListener pressButton() {
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		};
		return action;
	}

	public void addType(String aName, String aType, String aLocation, String aDescription) {
		String[] typeArr = {};
		typeArr = data.getTypeList().toArray(typeArr);
		JComboBox<String> cBox = new JComboBox<>(typeArr);
		JButton browse = new JButton("BROWSE");
		JTextField textName = new JTextField(20);
		JTextField textLoc = new JTextField(10);
		JTextArea textDesc = new JTextArea(5, 20);
		textName.setText(aName);
		textDesc.setText(aDescription);
		textLoc.setText(aLocation);
		JScrollPane scrollPane = new JScrollPane(textDesc);
		textDesc.setLineWrap(true);
		browse.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(
					new FileNameExtensionFilter("PDF or read file", "pdf", "txt", "doc", "docx", "ppt", "pptx"));
			int result = chooser.showOpenDialog(BookShelfUI.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				textLoc.setText(file.getPath());
				textName.setText(file.getName());
			}
		});
		JPanel panel = new JPanel();
		JPanel panelName = new JPanel();
		JPanel panelLoca = new JPanel();
		JPanel panelDes = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panelName.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLoca.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelDes.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(cBox);
		panelName.add(new JLabel("Name:"));
		panelName.add(textName);
		panelLoca.add(new JLabel("Location:"));
		panelLoca.add(textLoc);
		panelLoca.add(browse);
		panelDes.add(new JLabel("Description:"));
		panelDes.add(scrollPane);
		panel.add(panelName);
		panel.add(panelLoca);
		panel.add(panelDes);

		int choose = JOptionPane.showConfirmDialog(null, panel, "Add a Book", JOptionPane.OK_CANCEL_OPTION);

		if (JOptionPane.OK_OPTION == choose) {
			String name = textName.getText();
			String type = cBox.getSelectedItem().toString();
			String location = textLoc.getText();
			String description = textDesc.getText();
			if (name.length() == 0) {
				JOptionPane.showMessageDialog(null, "You forget to input a name !");
				addType(name, type, location, description);
			} else if (!location.matches("([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?")) {
				JOptionPane.showMessageDialog(null, "Invalid Path File");
				addType(name, type, location, description);
			} else if (description.length() == 0) {
				description = " ";
				addType(name, type, location, description);
			} else
				data.add(name, type, location, description);
		}
	}

	public void run() {
		this.setVisible(true);
	}
}