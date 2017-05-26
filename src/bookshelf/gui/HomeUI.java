package bookshelf.gui;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

import bookshelf.*;
import bookshelf.gui.FolderPageUI.DragBookAction;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is the home page of the application that can search, add, remove the
 * file that you want to deal with.
 * 
 * @author Archawin Tirugsapun,Triwith Mutitakul
 *
 */
public class HomeUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Database data;
	private JTextField searchText;
	private JLabel searchLabel, logoLabel, binLabel, pageLabel, programName;
	private JButton searchButton, addFolder, addImageadd, right, left, favor;
	private JPanel panelAdd, panelText, panelButton, panelAll, panelChange;
	private JComboBox<String> cBox;
	private Image logo, backGround, addImage, addType, delete;
	private TypeFactory typeFactory = TypeFactory.getInstances();
	private BookFactory bookFactory = BookFactory.getInstances();

	private boolean isClick = false;
	private String newFolder;
	private String[] typeArr = {};
	private int numFol = 0;
	private int currentPage = 1;
	private int havePage = 0;
	private final int MAX_FOLDER = 8;

	public HomeUI() throws IOException {
		initcomponents();
	}

	/**
	 * Initialize all the components in the frame.
	 * 
	 * @throws IOException
	 */
	public void initcomponents() throws IOException {
		this.setLocation(230, 20);
		this.setLayout(new FlowLayout());

		logo = ImageIO.read(new File("Picture//Logo.png"));
		addImage = ImageIO.read(new File("Picture//Add.png"));
		addType = ImageIO.read(new File("Picture//AddBook.png"));
		delete = ImageIO.read(new File("Picture//bin.png"));
		backGround = ImageIO.read(new File("Picture//bg1.jpg"));
		backGround = backGround
				.getScaledInstance(820, 700, Image.SCALE_DEFAULT);

		searchButton = new JButton();
		addFolder = new JButton();
		addImageadd = new JButton();
		right = new JButton("NEXT");
		left = new JButton("PREV");
		favor = new JButton();

		logoLabel = new JLabel(new ImageIcon(logo.getScaledInstance(110, 90,
				Image.SCALE_DEFAULT)));
		addFolder.setIcon(new ImageIcon(addImage.getScaledInstance(100, 80,
				Image.SCALE_DEFAULT)));
		addImageadd.setIcon(new ImageIcon(addType.getScaledInstance(80, 60,
				Image.SCALE_DEFAULT)));
		binLabel = new JLabel(new ImageIcon(delete.getScaledInstance(90, 70,
				Image.SCALE_DEFAULT)));
		programName = new JLabel("The Book-Shelf 1.0");

		ImageIcon iconPre = new ImageIcon("Picture//previousButton.png");
		Image imgPreButt = iconPre.getImage();
		Image newimg = imgPreButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconPre = new ImageIcon(newimg);
		left.setIcon(iconPre);
		left.setHorizontalTextPosition(SwingConstants.LEFT);
		left.setBorderPainted(false);
		left.setContentAreaFilled(false);
		left.addActionListener(changePage());

		ImageIcon iconNext = new ImageIcon("Picture//nextButton.png");
		Image imgNextButt = iconNext.getImage();
		Image newimg2 = imgNextButt.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH);
		iconNext = new ImageIcon(newimg2);
		right.setIcon(iconNext);
		right.setHorizontalTextPosition(SwingConstants.RIGHT);
		right.setBorderPainted(false);
		right.setContentAreaFilled(false);
		right.addActionListener(changePage());

		ImageIcon iconSearch = new ImageIcon("Picture//search.png");
		Image imgSearch = iconSearch.getImage();
		Image newimg3 = imgSearch.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		iconSearch = new ImageIcon(newimg3);

		searchButton.setIcon(iconSearch);
		searchButton.setBackground(Color.WHITE);
		searchButton.setPreferredSize(new Dimension(25, 25));

		ImageIcon iconFavor = new ImageIcon("Picture//star3.png");
		Image imgstar = iconFavor.getImage();
		Image newimg6 = imgstar.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		iconFavor = new ImageIcon(newimg6);

		ImageIcon iconEdit = new ImageIcon("Picture//edit.png");
		Image imgEdit = iconEdit.getImage();
		Image newimg8 = imgEdit.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		iconEdit = new ImageIcon(newimg8);

		favor.setIcon(iconFavor);
		favor.setBorderPainted(false);
		favor.setContentAreaFilled(false);
		favor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookShelfUI.setFolderLayOut("Favor");
			}
		});

		panelAdd = new JPanel();
		panelText = new JPanel();
		panelButton = new JPanel();
		panelChange = new JPanel();
		panelAll = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backGround, 0, 0, null);
			};
		};

		data = new Database();
		for (String type : typeFactory.getTypeList())
			addNewFolder(type);

		searchLabel = new JLabel("SEARCH : ");
		searchLabel.setFont(new Font("Apple Casual", 1, 20));
		searchLabel.setForeground(Color.WHITE);
		searchText = new JTextField(15);
		typeArr = typeFactory.getTypeList().toArray(typeArr);
		cBox = new JComboBox<>(typeArr);
		cBox.setPrototypeDisplayValue("                              ");
		searchText.addFocusListener(new FocusListener() {
			String promptText = "What you need ?";

			@Override
			public void focusLost(FocusEvent e) {
				if (searchText.getText().isEmpty()) {
					searchText.setText(promptText);
					searchText.setForeground(Color.gray);
				}
				searchText.setBorder(BorderFactory.createEmptyBorder());
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (searchText.getText().equals(promptText)) {
					searchText.setText("");
					searchText.setForeground(Color.BLACK);
					searchText.setBorder(BorderFactory.createLineBorder(
							new Color(95, 206, 243), 2));
				}
			}
		});
		searchButton.addActionListener(searchPage());
		searchText.addActionListener(searchPage());
		panelText.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelText.setBackground(Color.BLACK);

		panelText.add(logoLabel);
		panelText.add(searchLabel);
		panelText.add(searchText);
		panelText.add(cBox);
		panelText.add(searchButton);
		panelText.setPreferredSize(new Dimension(820, 100));

		addFolder.setBorder(BorderFactory.createEmptyBorder(60, 20, 40, 10));
		addImageadd.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 10));
		favor.setBorder(BorderFactory.createEmptyBorder(0, 20, 40, 10));
		binLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

		binLabel.setTransferHandler(new DropAction());

		panelAdd.add(addFolder);
		panelAdd.add(addImageadd);
		panelAdd.add(favor);
		panelAdd.add(binLabel);
		panelAdd.setLayout(new BoxLayout(panelAdd, BoxLayout.Y_AXIS));
		panelAdd.setBackground(Color.BLACK);

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
				if (isAlphaNumeric(newFolder)
						&& !newFolder.isEmpty()
						&& !typeFactory.getTypeList().stream()
								.filter((s) -> s.equalsIgnoreCase(newFolder))
								.findFirst().isPresent()) {
					typeFactory.addType(newFolder);
					addNewFolder(newFolder);
					data.close();
					setNewComboBox();
					havePage = (int) Math.ceil(typeFactory.getTypeList().size()
							/ (double) MAX_FOLDER);
					if (currentPage < havePage)
						right.setEnabled(true);
				}
			}
		});

		panelButton.setPreferredSize(new Dimension(600, 520));

		panelAdd.setOpaque(false);
		panelButton.setOpaque(false);
		panelChange.setOpaque(false);

		pageLabel = new JLabel("Page : " + currentPage);
		panelChange
				.add(new JLabel(
						"                                                                       "));
		panelChange.add(left);
		panelChange.add(pageLabel);
		panelChange.add(right);
		panelChange
				.add(new JLabel("                                          "));
		panelChange.add(programName);
		panelChange.setBackground(Color.BLACK);

		panelAll.setLayout(new BorderLayout());
		panelAll.add(panelText, BorderLayout.NORTH);
		panelAll.add(panelButton, BorderLayout.CENTER);
		panelAll.add(panelAdd, BorderLayout.EAST);
		panelAll.add(panelChange, BorderLayout.SOUTH);

		updateFrame();
		this.add(panelAll);
	}

	/**
	 * Add a new folder button into the page.
	 * 
	 * @param newFolder
	 *            is the text that show in the middle of the folder.
	 */
	public void addNewFolder(String newFolder) {
		if (numFol >= MAX_FOLDER)
			return;
		JButton newButton;
		if (newFolder.length() <= 9)
			newButton = new JButton(newFolder);
		else
			newButton = new JButton(newFolder.substring(0, 7) + "...");
		newButton.setFont(new Font("Rockwell", 0, 20));
		try {
			Image img = ImageIO.read(new File("Picture//folder.png"))
					.getScaledInstance(120, 120, Image.SCALE_DEFAULT);
			newButton.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			e.printStackTrace();
		}
		newButton.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				JButton button = (JButton) e.getSource();
				TransferHandler handle = button.getTransferHandler();
				handle.exportAsDrag(button, e, TransferHandler.COPY);
			}
		});
		newButton.setHorizontalTextPosition(SwingConstants.CENTER);
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				if (isClick) {
					BookShelfUI.setFolderLayOut(button.getText());
					isClick = false;
				}
			}
		});
		newButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isClick) {
					isClick = false;
				} else {
					isClick = true;
					Timer t = new Timer("", false);
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							isClick = false;
						}
					}, 500);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				newButton.setTransferHandler(new DragBookAction(
						getIndexList(newFolder)));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				newButton.setTransferHandler(new FileDragNDrop() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void importData(File file) {
						addType(file.getName(), newFolder,
								file.getAbsolutePath(), "");
						updateFrame();
					}
				});
			}
		});
		if (!newFolder.equalsIgnoreCase("all")) {
			newButton.setTransferHandler(new FileDragNDrop() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void importData(File file) {
					addType(file.getName(), newFolder, file.getAbsolutePath(),
							"");
					updateFrame();
				}
			});
		}
		newButton.setBorderPainted(false);
		newButton.setContentAreaFilled(false);
		newButton.setToolTipText(newFolder);
		panelButton.add(new JLabel(
				"                                           "));
		panelButton.add(newButton);
		numFol++;
	}

	/**
	 * Get index in the database by name of the folder.
	 * 
	 * @param name
	 *            of the folder that want its index.
	 * @return the index of the folder in string.
	 */
	public String getIndexList(String name) {
		int index = 0;
		for (int i = 0; i < typeFactory.getTypeList().size(); i++) {
			if (typeFactory.getTypeList().get(i).equals(name))
				index = i;
		}
		String indexS = Integer.toString(index);
		return indexS;
	}

	/**
	 * Add the file into a folder.
	 * 
	 * @param aName
	 *            is the name that you want to name the file in the app.
	 * @param aType
	 *            is the folder name that selected by the combobox.
	 * @param aLocation
	 *            is the location of the file that you want to add from your
	 *            computer.
	 * @param aDescription
	 *            is the description of the file that you want to describe the
	 *            file in the app.
	 */
	public void addType(String aName, String aType, String aLocation,
			String aDescription) {
		String[] typeArr = {};
		typeArr = typeFactory.getTypeList().toArray(typeArr);
		JComboBox<String> comboBox = new JComboBox<>();

		for (String type : typeFactory.getTypeList())
			if (!type.equalsIgnoreCase("all"))
				comboBox.addItem(type);

		comboBox.setSelectedItem(aType);
		comboBox.setPrototypeDisplayValue("                              ");
		JButton browse = new JButton("BROWSE");
		JTextField textName = new JTextField(20);
		JTextField textLoc = new JTextField(10);
		JTextArea textDesc = new JTextArea(5, 17);
		textName.setText(aName);
		textDesc.setText(aDescription);
		textLoc.setText(aLocation);
		textLoc.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textDesc);
		textDesc.setLineWrap(true);
		browse.addActionListener((e) -> {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter(
					"PDF or read file", "pdf", "txt", "doc", "docx", "ppt",
					"pptx", "xls", "rtf"));
			int result = chooser.showOpenDialog(HomeUI.this);
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

		panel.add(comboBox);
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

		int choose = JOptionPane.showConfirmDialog(null, panel, "Add a Book",
				JOptionPane.OK_CANCEL_OPTION);

		if (JOptionPane.OK_OPTION == choose) {
			String name = textName.getText();
			String type = comboBox.getSelectedItem().toString();
			String location = textLoc.getText();
			String description = textDesc.getText();

			if (name.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"You forget to input a name !");
				addType(name, type, location, description);
			} else if (location.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Invalid Path File");
				addType(name, type, location, description);
			} else if (description.isEmpty()) {
				JOptionPane.showMessageDialog(null,
						"You forget to input a description !");
				addType(name, type, location, description);
			} else {
				bookFactory.add(name, type, location, description);
				data.close();
				if (!bookFactory.isAdd())
					JOptionPane.showMessageDialog(this,
							"This file is already in shelf!");
			}
		}
	}

	/**
	 * Return true if the string are have only a letter or digit else false.
	 * 
	 * @param s
	 *            is the string that want to check.
	 * @return true if the string are have only a letter or digit else false.
	 */
	public boolean isAlphaNumeric(String s) {
		for (char c : s.toCharArray())
			if (!(Character.isLetter(c) || Character.isDigit(c)))
				return false;
		return true;
	}

	/**
	 * The ActionListener for the button that use to change the page of the app.
	 * 
	 * @return ActionLister
	 */
	public ActionListener changePage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton pressButton = (JButton) e.getSource();
				if (pressButton == right) {
					left.setEnabled(true);
					currentPage++;
					updateFrame();
				} else if (pressButton == left) {
					right.setEnabled(true);
					currentPage--;
					updateFrame();
				}
			}
		};
	}

	/**
	 * The ActionListener for the button and textfield that use to search from
	 * name and type.
	 * 
	 * @return ActionListener
	 */
	public ActionListener searchPage() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookShelfUI.setSearchLayOut(cBox.getSelectedItem().toString(),
						searchText.getText());
			}
		};
	}

	/**
	 * Reset the page in UI.
	 */
	public void updateFrame() {
		numFol = 0;
		setNewComboBox();
		havePage = (int) Math.ceil(typeFactory.getTypeList().size()
				/ (double) MAX_FOLDER);
		pageLabel.setText("Page : " + currentPage);
		panelButton.removeAll();
		panelButton.repaint();
		for (String nameType : typeFactory.getTypeList().subList(
				(currentPage - 1) * MAX_FOLDER,
				typeFactory.getTypeList().size())) {
			addNewFolder(nameType);
		}

		if (currentPage <= 1)
			left.setEnabled(false);

		if (currentPage + 1 > havePage)
			right.setEnabled(false);
	}

	/**
	 * Set the new combobox that use to search.
	 */
	public void setNewComboBox() {
		cBox.removeAllItems();
		for (String type : typeFactory.getTypeList())
			cBox.addItem(type);
	}

	/**
	 * The transferhandler that drop the file to delete it from the app.
	 * 
	 * @author Archawin Tirugsapun,Triwith Mutitakul
	 *
	 */
	public class DropAction extends TransferHandler {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;

		@Override
		public boolean canImport(TransferHandler.TransferSupport support) {
			return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
		}

		@Override
		public boolean importData(TransferHandler.TransferSupport support) {
			boolean accept = false;
			if (canImport(support)) {
				try {
					Transferable t = support.getTransferable();
					Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
					if (value instanceof String) {
						Component component = support.getComponent();
						if (component instanceof JLabel) {
							if (support.getComponent().equals(binLabel)) {
								int choose = JOptionPane
										.showConfirmDialog(
												null,
												String.format(
														"Remove %s? This will remove all of the book inside this folder",
														typeFactory
																.getTypeList()
																.get(Integer
																		.parseInt(value
																				.toString()))),
												"Delete The Folder",
												JOptionPane.OK_CANCEL_OPTION);
								if (choose == JOptionPane.OK_OPTION) {
									typeFactory.removeType(typeFactory
											.getTypeList().get(
													Integer.parseInt(value
															.toString())));
									data.close();
									if (panelButton.getComponentCount() <= 2) {
										right.setEnabled(false);
										currentPage--;
									}
									updateFrame();
								}
								accept = true;
							}
						}
					}
				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}
			return accept;
		}
	}

}