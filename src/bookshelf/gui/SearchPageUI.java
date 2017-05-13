package bookshelf.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bookshelf.Book;
import bookshelf.Database;

public class SearchPageUI implements Runnable {
	private JFrame frame;
	private JLabel backGLabel;
	private JPanel panelCenter;
	private JPanel panelSouth;
	private JButton preButton;
	private JButton nextButton;
	private JLabel emptyLabel;
	protected List<JButton> bookListButton;
	protected List<Book> bookList;
	private String filter;

	public SearchPageUI(String filter) {
		this.filter = filter;
		databaseSetUp();
		initComponents();
	}

	private void initComponents() {
		frame = new JFrame("Search Result");
		emptyLabel = new JLabel("                                                  ");
		ImageIcon img = new ImageIcon("Picture//backGR.jpg");
		backGLabel = new JLabel(img);
		backGLabel.setLayout(new FlowLayout());
		panelCenter = new JPanel();
		panelSouth = new JPanel();
		nextButton = new JButton();
		preButton = new JButton();

		ImageIcon iconPre = new ImageIcon("Picture//previousButton.png");
		Image imgPreButt = iconPre.getImage();
		Image newimg = imgPreButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconPre = new ImageIcon(newimg);

		ImageIcon iconNext = new ImageIcon("Picture//nextButton.png");
		Image imgNextButt = iconNext.getImage();
		Image newimg2 = imgNextButt.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		iconNext = new ImageIcon(newimg2);

		preButton.setIcon(iconPre);
		preButton.setPreferredSize((new Dimension(100, 100)));
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

		panelSouth.add(preButton);
		panelSouth.add(emptyLabel);
		panelSouth.add(nextButton);
		panelSouth.setLayout(new FlowLayout());
		panelSouth.setBackground(new Color(38, 30, 19));
		panelSouth.setPreferredSize(new Dimension(80, 80));
		panelSouth.setLocation(200, 200);
		panelCenter.add(backGLabel);

		for (JButton bookBut : bookListButton) {
			bookBut.setHorizontalTextPosition(SwingConstants.CENTER);
			bookBut.setFont(new Font("Arial",0, 35));
			backGLabel.add(bookBut,BorderLayout.CENTER);
			backGLabel.add(new JLabel("            "));
		}
		
		frame.add(panelCenter,BorderLayout.CENTER);
		frame.add(panelSouth, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

	}

	private void databaseSetUp() {
		Database data = new Database();
		bookListButton = new ArrayList<>();
		bookList = data.getBookList();
		Predicate<Book> fil = (s) -> (s.getType().equalsIgnoreCase(this.filter));
		List<Book> temp = bookList.stream().filter(fil).collect(Collectors.toList());
		
		ImageIcon iconBook = new ImageIcon("Picture//sampleBook.jpg");
		Image imgBook = iconBook.getImage();
		Image newimg3 = imgBook.getScaledInstance(250,380, Image.SCALE_SMOOTH);
		iconBook = new ImageIcon(newimg3);
		
		for (Book book : temp) {
			System.out.println(book.getName());
			JButton bookBut = new JButton(book.getName(),iconBook);
			bookBut.setHorizontalAlignment(SwingConstants.CENTER);
			bookBut.setVerticalAlignment(SwingConstants.CENTER);
			bookBut.setPreferredSize(new Dimension(250, 380));
			bookListButton.add(bookBut);
		}

	}

	@Override
	public void run() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SearchPageUI r = new SearchPageUI("Novel");
		r.run();
	}

}
