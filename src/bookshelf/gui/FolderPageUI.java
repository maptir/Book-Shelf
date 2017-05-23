package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;

import bookshelf.*;
import jdk.nashorn.internal.runtime.regexp.joni.Warnings;

/**
 * The folder page of this program.
 * 
 * @author Triwith Mutitakul
 *
 */
public class FolderPageUI implements Runnable {
	private Database data;
	private BookFactory bookFactory;
	protected List<JButton> bookListButton;
	protected List<Book> bookList;
	protected List<String> favorList;
	private int[] starterPage = { 0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72 };
	private int currentPage = 1;
	private int havePage = 0;
	private int starterPageNum = 0;
	private String filter;
	private boolean isDoubleClick = false;
	private boolean isRemoveState = false;

	private JFrame frame;
	private JFrame detailFrame;
	private JLabel backGLabel;
	private JPanel panelCenter;
	private JPanel panelSouth;
	private JButton preButton;
	private JButton nextButton;
	private JButton addBookButton;
	private JTextField emptyLabel2, emptyLabel3, emptyLabel4, emptyLabel5;
	private JLabel emptyLabel, garbageLabel, favorLabel, favorText;
	private ImageIcon img;
	private JLabel woodPic;
	private ImageIcon iconBook;

	public FolderPageUI(String filter) {
		this.filter = filter;
		databaseSetUp();
		initComponents();
		frame.setResizable(false);
	}

	private void initComponents() {
		UIManager.put("ToolTip.background", Color.BLACK);
		UIManager.put("ToolTip.foreground", Color.WHITE);

		frame = new JFrame(filter + " Shelf");

		detailFrame = new JFrame();
		detailFrame.setUndecorated(true);
		frame.setSize(900, 700);
		frame.setPreferredSize(new Dimension(900, 700));
		emptyLabel = new JLabel("Page : " + currentPage);
		emptyLabel2 = new JTextField(100);
		emptyLabel3 = new JTextField(100);
		emptyLabel4 = new JTextField(100);
		emptyLabel5 = new JTextField(100);
		garbageLabel = new JLabel();
		favorLabel = new JLabel();
		favorText = new JLabel("<html><p>                     Add Favourite<br>           by drop on star</p></html>");
		img = new ImageIcon("Picture//backGR.jpg");
		img = new ImageIcon(img.getImage().getScaledInstance(900, 650, Image.SCALE_SMOOTH));
		panelCenter = new JPanel();
		panelSouth = new JPanel();
		nextButton = new JButton();
		preButton = new JButton();
		addBookButton = new JButton();
		try {
			Image woodImg = ImageIO.read(new File("Picture//wood.png")).getScaledInstance(1200, 20, Image.SCALE_SMOOTH);
			woodPic = new JLabel(new ImageIcon(woodImg));
		} catch (IOException e) {
			e.printStackTrace();
		}

		woodPic.setPreferredSize(new Dimension(400, 25));
		ImageIcon iconPre = new ImageIcon("Picture//previousButton.png");
		Image imgPreButt = iconPre.getImage();
		Image newimg = imgPreButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconPre = new ImageIcon(newimg);

		ImageIcon iconNext = new ImageIcon("Picture//nextButton.png");
		Image imgNextButt = iconNext.getImage();
		Image newimg2 = imgNextButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconNext = new ImageIcon(newimg2);

		ImageIcon iconAddBook = new ImageIcon("Picture//AddBook.png");
		Image imgAddBoo = iconAddBook.getImage();
		Image newimg3 = imgAddBoo.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		iconAddBook = new ImageIcon(newimg3);

		ImageIcon iconGarbage = new ImageIcon("Picture//bin.png");
		Image imgGarbage = iconGarbage.getImage();
		Image newimg4 = imgGarbage.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		iconGarbage = new ImageIcon(newimg4);

		ImageIcon iconFavor = new ImageIcon("Picture//star3.png");
		Image imgstar = iconFavor.getImage();
		Image newimg6 = imgstar.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		iconFavor = new ImageIcon(newimg6);

		emptyLabel.setPreferredSize(new Dimension(90, 40));
		emptyLabel.setFont(new Font("Arial", 0, 20));
		emptyLabel.setForeground(Color.WHITE);
		emptyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		emptyLabel.setVerticalTextPosition(SwingConstants.NORTH);

		preButton.setIcon(iconPre);
		preButton.setPreferredSize(new Dimension(100, 100));
		preButton.setBorder(new EmptyBorder(10, 10, 10, 10));
		preButton.setHorizontalAlignment(SwingConstants.CENTER);
		preButton.setVerticalAlignment(SwingConstants.NORTH);
		preButton.setBackground(new Color(38, 30, 19));
		preButton.setEnabled(false);

		nextButton.setIcon(iconNext);
		nextButton.setPreferredSize((new Dimension(100, 100)));
		nextButton.setBorder(new EmptyBorder(10, 10, 10, 10));
		nextButton.setVerticalAlignment(SwingConstants.NORTH);
		nextButton.setHorizontalAlignment(SwingConstants.CENTER);
		nextButton.setBackground(new Color(38, 30, 19));

		addBookButton.setBorderPainted(false);
		addBookButton.setIcon(iconAddBook);
		addBookButton.setPreferredSize((new Dimension(70, 70)));
		addBookButton.setVerticalAlignment(SwingConstants.NORTH);
		addBookButton.setHorizontalAlignment(SwingConstants.CENTER);
		addBookButton.setBackground(new Color(38, 30, 19));
		addBookButton.addActionListener(new addBookAction());
		addBookButton.setToolTipText("Click me for adding new book");

		garbageLabel.setIcon(iconGarbage);
		garbageLabel.setPreferredSize((new Dimension(70, 70)));
		garbageLabel.setVerticalAlignment(SwingConstants.NORTH);
		garbageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		garbageLabel.setBackground(new Color(38, 30, 19));
		garbageLabel.setTransferHandler(new DropAction());
		garbageLabel.setToolTipText("Drop Book on me for delete it.");

		if (!filter.equalsIgnoreCase("favor")) {
			favorText.setForeground(Color.WHITE);
			favorText.setBackground(new Color(38, 30, 19));
			favorLabel.setIcon(iconFavor);
			favorLabel.setPreferredSize((new Dimension(70, 110)));
			favorLabel.setVerticalAlignment(SwingConstants.NORTH);
			favorLabel.setHorizontalAlignment(SwingConstants.CENTER);
			favorLabel.setBackground(new Color(38, 30, 19));
			favorLabel.setTransferHandler(new DropAction());
		}
		if (bookList.size() > 6) {
			nextButton.setEnabled(true);
		} else {
			nextButton.setEnabled(false);
		}

		panelSouth.add(garbageLabel);
		panelSouth.add(new JLabel("                                                                              "));
		panelSouth.add(preButton);
		panelSouth.add(emptyLabel);
		panelSouth.add(nextButton);
		panelSouth.add(favorText);
		panelSouth.add(favorLabel);
		panelSouth.add(addBookButton);
		panelSouth.setLayout(new FlowLayout());
		panelSouth.setBackground(new Color(38, 30, 19));
		panelSouth.setPreferredSize(new Dimension(80, 80));
		panelSouth.setLocation(200, 200);
		nextButton.addActionListener(new ButtonAction());
		preButton.addActionListener(new ButtonAction());

		panelCenter.add(createBookPerPage(starterPageNum));

		frame.add(panelCenter, BorderLayout.CENTER);
		frame.add(panelSouth, BorderLayout.SOUTH);
	}

	/**
	 * The method is use for create the page.
	 * 
	 * @param start
	 * @return label of page.
	 */
	private JLabel createBookPerPage(int start) {
		backGLabel = new JLabel(img);
		backGLabel.setLayout(new FlowLayout());
		emptyLabel3.setBackground(new Color(180, 115, 73));
		emptyLabel3.setEditable(false);
		emptyLabel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		backGLabel.add(emptyLabel3);
		int countPerLine = 0;
		// case remove last book of page
		if (isRemoveState && start >= 6 && ((start - bookListButton.size()) % 6 == 0)) {
			currentPage--;
			isRemoveState = false;
			updateFrame((int) Math.ceil(start - bookListButton.size() % 6) - 6);
		}

		for (; start < bookListButton.size(); start++) {
			if (countPerLine == 6) {
				emptyLabel2.setBackground(new Color(180, 115, 73));
				emptyLabel2.setEditable(false);
				emptyLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				backGLabel.add(emptyLabel2);
				break;
			} else if (countPerLine == 3) {
				emptyLabel4.setBackground(new Color(180, 115, 73));
				emptyLabel4.setEditable(false);
				emptyLabel4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				backGLabel.add(emptyLabel4);
			}
			JButton but = bookListButton.get(start);
			backGLabel.setLayout(new FlowLayout());
			but.setHorizontalTextPosition(SwingConstants.CENTER);
			but.setFont(new Font("Arial", 0, 20));
			but.setBackground(Color.BLACK);
			but.setForeground(Color.WHITE);
			but.setActionCommand("" + start);
			but.addActionListener(new BookClickAction());
			but.addMouseListener(new doubleClickAction());
			but.setTransferHandler(new DragBookAction(Integer.toString(start)));
			but.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					JButton button = (JButton) e.getSource();
					TransferHandler handle = button.getTransferHandler();
					handle.exportAsDrag(button, e, TransferHandler.COPY);
				}
			});

			backGLabel.add(but, BorderLayout.CENTER);
			backGLabel.add(new JLabel("            "));
			countPerLine++;
			if (start == bookListButton.size() - 1) {
				emptyLabel5.setBackground(new Color(180, 115, 73));
				emptyLabel5.setEditable(false);
				emptyLabel5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				backGLabel.add(emptyLabel5);
			}
		}
		return backGLabel;
	}

	/**
	 * The method that use for read the database file.
	 */
	private void databaseSetUp() {
		data = new Database();
		bookFactory = BookFactory.getInstances();
		bookListButton = new ArrayList<>();
		bookList = bookFactory.getBookList();
		favorList = data.getFavorList();
		if (filter.equalsIgnoreCase("favor")) {
			List<Book> tempFavor = new ArrayList<>();
			favorList = data.getFavorList();
			for (String favorIndex : favorList) {
				tempFavor.add(bookList.get(Integer.parseInt(favorIndex)));
			}
			bookList = tempFavor;
		} else if (filter.equalsIgnoreCase("all")) {
			// do nothing
		} else {
			Predicate<Book> fil = (s) -> (s.getType().equalsIgnoreCase(this.filter));
			bookList = bookList.stream().filter(fil).collect(Collectors.toList());
		}
		iconBook = new ImageIcon("Picture//basicbook.png");
		Image imgBook = iconBook.getImage();
		Image newimg3 = imgBook.getScaledInstance(140, 200, Image.SCALE_SMOOTH);
		iconBook = new ImageIcon(newimg3);

		for (Book book : bookList) {
			createBookButton(book);
		}
		havePage = (int) Math.ceil(bookList.size() / 6.0);

	}

	/**
	 * This method is use for open the file.
	 * 
	 * @param fileLocation
	 */
	private void openFile(String fileLocation,int index) {
		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File(fileLocation);
				Desktop.getDesktop().open(myFile);
				
			} catch (IOException | IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(frame, "File not found", "Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public void createBookButton(Book book) {
		JButton bookBut = new JButton(book.getName(), iconBook);
		bookBut.setHorizontalAlignment(SwingConstants.CENTER);
		bookBut.setVerticalAlignment(SwingConstants.CENTER);
		bookBut.setPreferredSize(new Dimension(140, 200));
		String toolTip = String.format(
				"<html><p width=\"250\">" + "%s<br>Type : %s<br>File Location : %s<br>Detail : %s", book.getName(),
				book.getType(), book.getLocation(), book.getDescription() + "</p></html>");
		bookBut.setToolTipText(toolTip);
		bookListButton.add(bookBut);
	}

	/**
	 * This method is use for update the frame.
	 */
	public void updateFrame() {
		havePage = (int) Math.ceil(bookList.size() / 6.0);
		emptyLabel.setText("Page : " + currentPage);
		panelCenter.removeAll();
		panelCenter.add(createBookPerPage(starterPage[currentPage - 1]));
		if (currentPage <= 1) {
			preButton.setEnabled(false);
		}
		if (currentPage + 1 > havePage) {
			nextButton.setEnabled(false);
		}
		frame.validate();
	}

	/**
	 * This method is use for update the frame by input number of starter book.
	 * 
	 * @param start
	 */
	public void updateFrame(int start) {
		havePage = (int) Math.ceil(bookList.size() / 6.0);
		emptyLabel.setText("Page : " + currentPage);
		panelCenter.removeAll();
		panelCenter.add(createBookPerPage(start));

		if (currentPage <= 1) {
			preButton.setEnabled(false);
		}
		if (currentPage + 1 > havePage) {
			nextButton.setEnabled(false);
		}
		frame.validate();
	}

	/**
	 * This method is use for run the program.
	 */
	@Override
	public void run() {
		frame.setVisible(true);
	}

	/**
	 * This class is the ActionListener of next and previous button.
	 * 
	 * @author Triwith Mutitakul
	 *
	 */
	public class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			isRemoveState = false;
			if (e.getSource() == nextButton) {
				preButton.setEnabled(true);
				currentPage++;
				updateFrame();
			} else if (e.getSource() == preButton) {
				nextButton.setEnabled(true);
				currentPage--;
				updateFrame();
			}
		}

	}

	/**
	 * This class is the ActionListener of each book.
	 * 
	 * @author Triwith Mutitakul
	 *
	 */
	public class BookClickAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			isRemoveState = false;
			if (isDoubleClick) {
				String fileLocation = bookList.get(Integer.parseInt(e.getActionCommand())).getLocation();
				openFile(fileLocation,Integer.parseInt(e.getActionCommand()));
				isDoubleClick = false;
			}
		}
	}

	/**
	 * This class is the Mouse action of each book.
	 * 
	 * @author Triwith Mutitakul
	 *
	 */
	public class doubleClickAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				isDoubleClick = true;
			}
		}
	}

	/**
	 * This class is the ActionListener that use for drag the book.
	 * 
	 * @author Triwith Mutitakul
	 *
	 */
	public static class DragBookAction extends TransferHandler {
		public final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
		private String value;

		public DragBookAction(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public int getSourceActions(JComponent c) {
			return DnDConstants.ACTION_COPY_OR_MOVE;
		}

		@Override
		protected Transferable createTransferable(JComponent c) {
			Transferable t = new StringSelection(getValue());
			return t;
		}

		@Override
		protected void exportDone(JComponent source, Transferable data, int action) {
			super.exportDone(source, data, action);
			// Decide what to do after the drop has been accepted
		}

	}

	/**
	 * This class is the ActionListener that use for drop the book.
	 * 
	 * @author Triwith Mutitakul
	 *
	 */
	public class DropAction extends TransferHandler {
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
							if (support.getComponent().equals(garbageLabel)) {
								int choose = JOptionPane.showConfirmDialog(frame,
										String.format("Remove %s?",
												bookList.get(Integer.parseInt(value.toString())).getName()),
										"Delete Book", JOptionPane.OK_CANCEL_OPTION);
								if (choose == JOptionPane.OK_OPTION) {
									bookListButton.remove(Integer.parseInt(value.toString()));
									bookFactory.removeBook(bookList.get(Integer.parseInt(value.toString())).getName(),
											bookList.get(Integer.parseInt(value.toString())).getDescription());
									data.close();
									bookList.remove(Integer.parseInt(value.toString()));
									if (bookList.size() <= 6) {
										nextButton.setEnabled(false);
									}
									isRemoveState = true;
									updateFrame();
								}
								accept = true;
							} else {
								if (!favorList.contains(value.toString())) {
									int choose = JOptionPane.showConfirmDialog(frame,
											String.format("Add %s to favourite?",
													bookList.get(Integer.parseInt(value.toString())).getName()),
											"Add to Favourite", JOptionPane.OK_CANCEL_OPTION);
									if (choose == JOptionPane.OK_OPTION) {
										data.addFavor(value.toString());
										data.close();
										updateFrame();
									}
								} else {
									int choose = JOptionPane.showConfirmDialog(frame,
											String.format("Remove %s from favourite?",
													bookList.get(Integer.parseInt(value.toString())).getName()),
											"Remove from Favourite", JOptionPane.OK_CANCEL_OPTION);
									if (choose == JOptionPane.OK_OPTION) {
										data.removeFavor(value.toString());
										data.close();
										updateFrame();
									}
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

	public class addBookAction implements ActionListener {
		public void addType(String aName, String aType, String aLocation, String aDescription) {

			JButton browse = new JButton("BROWSE");
			JTextField textName = new JTextField(20);
			JTextField textLoc = new JTextField(10);
			JTextArea textDesc = new JTextArea(5, 20);
			textName.setText(aName);
			textDesc.setText(aDescription);
			textLoc.setText(aLocation);
			JScrollPane scrollPane = new JScrollPane(textDesc);
			textDesc.setLineWrap(true);
			browse.addActionListener((s) -> {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(
						new FileNameExtensionFilter("PDF or read file", "pdf", "txt", "doc", "docx", "ppt", "pptx"));
				int result = chooser.showOpenDialog(frame);
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
				String location = textLoc.getText();
				String description = textDesc.getText();
				if (name.length() == 0) {
					JOptionPane.showMessageDialog(null, "You forget to input a name !");
					addType(name, filter, location, description);
				} else if (location.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Invalid Path File");
					addType(name, filter, location, description);
				} else if (description.length() == 0) {
					description = " ";
					addType(name, filter, location, description);
				} else {
					bookFactory.add(name, filter, location, description);
					bookList.add(new Book(name, filter, location, description));
					createBookButton(new Book(name, filter, location, description));
					updateFrame();
				}
			}

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			addType("", "", "", "");
			if (bookList.size() % 6 == 1) {
				currentPage++;
				updateFrame(starterPage[currentPage] - 6);
			}
			data.close();
		}

	}
}
