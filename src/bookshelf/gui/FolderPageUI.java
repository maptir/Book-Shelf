package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bookshelf.Book;
import bookshelf.Database;

public class FolderPageUI implements Runnable {
	private JFrame frame;
	private JLabel backGLabel;
	private JPanel panelCenter;
	private JPanel panelSouth;
	private JButton preButton;
	private JButton nextButton;
	private JButton addBookButton;
	private JTextField emptyLabel2, emptyLabel3, emptyLabel4, emptyLabel5;
	private JLabel emptyLabel,garbageLabel;
	private ImageIcon img;
	protected List<JButton> bookListButton;
	protected List<Book> bookList;
	protected List<JLabel> eachPerPage;
	private String filter;
	private int currentPage = 1;
	private int havePage = 0;
	private int starterPageNum = 0;
	private JLabel woodPic;
	private int[] starterPage = { 0, 6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72 };

	public FolderPageUI(String filter) {
		this.filter = filter;
		databaseSetUp();
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame("Search Result");
		frame.setSize(1000, 900);
		frame.setPreferredSize(new Dimension(1000, 900));
		emptyLabel = new JLabel("Page : " + currentPage);
		emptyLabel2 = new JTextField(100);
		emptyLabel3 = new JTextField(100);
		emptyLabel4 = new JTextField(100);
		emptyLabel5 = new JTextField(100);
		garbageLabel = new JLabel();
		img = new ImageIcon("Picture//backGR.jpg");
		img = new ImageIcon(img.getImage().getScaledInstance(1000, 800, Image.SCALE_SMOOTH));
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
		Image newimg3 = imgAddBoo.getScaledInstance(150, 155, Image.SCALE_SMOOTH);
		iconAddBook = new ImageIcon(newimg3);
		
		ImageIcon iconGarbage = new ImageIcon("Picture//bin.png");
		Image imgGarbage = iconGarbage.getImage();
		Image newimg4 = imgGarbage.getScaledInstance(150, 155, Image.SCALE_SMOOTH);
		iconGarbage = new ImageIcon(newimg4);

		emptyLabel.setPreferredSize(new Dimension(90, 40));
		emptyLabel.setFont(new Font("Arial", 0, 20));
		emptyLabel.setForeground(Color.WHITE);
		emptyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		emptyLabel.setVerticalTextPosition(SwingConstants.NORTH);

		preButton.setIcon(iconPre);
		preButton.setPreferredSize((new Dimension(100, 100)));
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

		addBookButton.setIcon(iconAddBook);
		addBookButton.setPreferredSize((new Dimension(150, 155)));
		addBookButton.setVerticalAlignment(SwingConstants.NORTH);
		addBookButton.setHorizontalAlignment(SwingConstants.CENTER);
		addBookButton.setBackground(new Color(38, 30, 19));
		
		garbageLabel.setIcon(iconGarbage);
		garbageLabel.setPreferredSize((new Dimension(150, 155)));
		garbageLabel.setVerticalAlignment(SwingConstants.NORTH);
		garbageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		garbageLabel.setBackground(new Color(38, 30, 19));
		garbageLabel.setDropTarget(new DropTarget());

		if (bookList.size() >= 8) {
			nextButton.setEnabled(true);
		} else {
			nextButton.setEnabled(false);
		}

		panelSouth.add(preButton);
		panelSouth.add(emptyLabel);
		panelSouth.add(nextButton);
		panelSouth.setLayout(new FlowLayout());
		panelSouth.setBackground(new Color(38, 30, 19));
		panelSouth.setPreferredSize(new Dimension(80, 80));
		panelSouth.setLocation(200, 200);
		nextButton.addActionListener(new ButtonAction());
		preButton.addActionListener(new ButtonAction());

		panelCenter.add(createBookPerPage(starterPageNum));

		frame.add(panelCenter, BorderLayout.CENTER);
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

	}

	private JLabel createBookPerPage(int start) {
		backGLabel = new JLabel(img);
		backGLabel.setLayout(new FlowLayout());
		emptyLabel3.setBackground(new Color(180, 115, 73));
		emptyLabel3.setEditable(false);
		emptyLabel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		backGLabel.add(emptyLabel3);
		int countPerLine = 0;
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
			but.setFont(new Font("Arial", 0, 35));
			but.setActionCommand("" + start);
			but.addActionListener(new BookClickAction());
			
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
		backGLabel.add(addBookButton);
		backGLabel.add(new JLabel("                                                                                                                                                                                                                         "));
		backGLabel.add(garbageLabel);
		return backGLabel;
	}

	private void databaseSetUp() {
		Database data = new Database();
		bookListButton = new ArrayList<>();
		bookList = data.getBookList();
		Predicate<Book> fil = (s) -> (s.getType().equalsIgnoreCase(this.filter));
		List<Book> temp = bookList.stream().filter(fil).collect(Collectors.toList());

		ImageIcon iconBook = new ImageIcon("Picture//sampleBook.jpg");
		Image imgBook = iconBook.getImage();
		Image newimg3 = imgBook.getScaledInstance(180, 265, Image.SCALE_SMOOTH);
		iconBook = new ImageIcon(newimg3);

		for (Book book : temp) {
			JButton bookBut = new JButton(book.getName(), iconBook);
			bookBut.setHorizontalAlignment(SwingConstants.CENTER);
			bookBut.setVerticalAlignment(SwingConstants.CENTER);
			bookBut.setPreferredSize(new Dimension(180, 265));
			String toolTip = String.format("Description : %s", book.getDescription());
			bookBut.setToolTipText(toolTip);
			bookListButton.add(bookBut);
		}
		havePage = bookList.size() / 6;
	}

	private void openFile(String fileLocation) {
		if (Desktop.isDesktopSupported()) {
			try {
				File myFile = new File(fileLocation);
				Desktop.getDesktop().open(myFile);
			} catch (IOException | IllegalArgumentException ex) {
				System.out.println("File Not Found");
			}
		}
	}

	@Override
	public void run() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		FolderPageUI r = new FolderPageUI("Novel");
		r.run();
	}

	public class ButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nextButton) {
				preButton.setEnabled(true);
				currentPage++;
				emptyLabel.setText("Page : " + currentPage);
				panelCenter.removeAll();
				panelCenter.add(createBookPerPage(starterPage[currentPage - 1]));
				frame.setSize(1000, 900);
				if (currentPage + 1 >= havePage) {
					nextButton.setEnabled(false);
				}
			} else if (e.getSource() == preButton) {
				nextButton.setEnabled(true);
				currentPage--;
				emptyLabel.setText("Page : " + currentPage);
				panelCenter.removeAll();
				panelCenter.add(createBookPerPage(starterPage[currentPage - 1]));
				frame.setSize(1000, 900);
				if (currentPage <= 1) {
					preButton.setEnabled(false);
				}
			}
		}

	}

	public class BookClickAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileLocation = bookList.get((Integer.parseInt(e.getActionCommand()))).getLocation();
			openFile(fileLocation);
		}

	}

}
