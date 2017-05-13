package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
	private JTextField emptyLabel2, emptyLabel3, emptyLabel4;
	private JLabel emptyLabel;
	private ImageIcon img;
	protected List<JButton> bookListButton;
	protected List<Book> bookList;
	protected List<JLabel> eachPerPage;
	private String filter;
	private int currentPage = 1;
	private int havePage = 0;
	private int starterPageNum = 0;
	private JLabel woodPic;
	private int[] starterPage = { 0, 8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96 };

	public FolderPageUI(String filter) {
		this.filter = filter;
		databaseSetUp();
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame("Search Result");
		frame.setSize(1330, 1000);
		emptyLabel = new JLabel("Page : " + currentPage);
		emptyLabel2 = new JTextField(110);
		emptyLabel3 = new JTextField(110);
		emptyLabel4 = new JTextField(110);
		img = new ImageIcon("Picture//backGR.jpg");
		panelCenter = new JPanel();
		panelSouth = new JPanel();
		nextButton = new JButton();
		preButton = new JButton();
		try {
			Image woodImg = ImageIO.read(new File("Picture//wood.png")).getScaledInstance(1200, 20, Image.SCALE_SMOOTH);
			woodPic = new JLabel(new ImageIcon(woodImg));
		} catch (IOException e) {
			e.printStackTrace();
		}

		woodPic.setPreferredSize(new Dimension(1200, 20));
		ImageIcon iconPre = new ImageIcon("Picture//previousButton.png");
		Image imgPreButt = iconPre.getImage();
		Image newimg = imgPreButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconPre = new ImageIcon(newimg);

		ImageIcon iconNext = new ImageIcon("Picture//nextButton.png");
		Image imgNextButt = iconNext.getImage();
		Image newimg2 = imgNextButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconNext = new ImageIcon(newimg2);

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

		nextButton.setIcon(iconNext);
		nextButton.setPreferredSize((new Dimension(100, 100)));
		nextButton.setBorder(new EmptyBorder(10, 10, 10, 10));
		nextButton.setVerticalAlignment(SwingConstants.NORTH);
		nextButton.setHorizontalAlignment(SwingConstants.CENTER);
		nextButton.setBackground(new Color(38, 30, 19));
		preButton.setEnabled(false);
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
		// frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
			if (countPerLine == 8) {
				emptyLabel2.setBackground(new Color(180, 115, 73));
				emptyLabel2.setEditable(false);
				emptyLabel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				backGLabel.add(emptyLabel2);
				break;
			} else if (countPerLine == 4) {
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
		}
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
		Image newimg3 = imgBook.getScaledInstance(250, 380, Image.SCALE_SMOOTH);
		iconBook = new ImageIcon(newimg3);

		for (Book book : temp) {
			JButton bookBut = new JButton(book.getName(), iconBook);
			bookBut.setHorizontalAlignment(SwingConstants.CENTER);
			bookBut.setVerticalAlignment(SwingConstants.CENTER);
			bookBut.setPreferredSize(new Dimension(250, 380));
			String toolTip = String.format("Description : %s", book.getDescription());
			bookBut.setToolTipText(toolTip);
			bookListButton.add(bookBut);
		}
		havePage = bookList.size() / 8;
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
		FolderPageUI r = new FolderPageUI("Comic");
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
				frame.pack();
				if (currentPage + 1 >= havePage) {
					nextButton.setEnabled(false);
				}
			} else if (e.getSource() == preButton) {
				nextButton.setEnabled(true);
				currentPage--;
				emptyLabel.setText("Page : " + currentPage);
				panelCenter.removeAll();
				panelCenter.add(createBookPerPage(starterPage[currentPage - 1]));
				frame.pack();
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
