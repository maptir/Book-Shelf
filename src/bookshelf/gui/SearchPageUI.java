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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bookshelf.Book;
import bookshelf.BookFactory;
import bookshelf.Database;
import bookshelf.TypeFactory;

public class SearchPageUI extends JPanel {
	private Database data;
	private List<Book> bookList;
	private List<String> typeList;
	private BookFactory bookFactory;
	private TypeFactory typeFactory;
	private String type = "";
	private int havePage;
	private JLabel searchLabel;
	private JLabel typeLabel;
	private JLabel emptyLine;
	private JTextField searchField;
	private JComboBox<Object> typeComboBox;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JButton searchButton;
	private JButton nextButton;
	private JButton preButton;
	private JButton homeButton;
	private JButton addFavorButton;
	private JLabel bgLabel;
	private JLabel pageLabel;
	private JLabel searchResult = new JLabel();;
	private ImageIcon iconBook;
	private ImageIcon iconDesk;
	private int[] starterPage = { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36 };
	private int currentPage = 1;
	private boolean isDoubleClick = false;

	public SearchPageUI(String type) {
		this.type = type;
		databaseSetUp(type, "");
		initComponents();
	}

	public SearchPageUI(String name, String type) {
		this.type = type;
		databaseSetUp(type, "");
		initComponents();
		typeComboBox.setSelectedItem(type);
	}

	private void initComponents() {
		this.setPreferredSize(new Dimension(750, 580));
		this.setBackground(Color.BLACK);
		northPanel = new JPanel(new FlowLayout());
		centerPanel = new JPanel(new FlowLayout());
		southPanel = new JPanel(new FlowLayout());
		searchField = new JTextField(15);
		searchLabel = new JLabel("Search  :  ");
		typeLabel = new JLabel("Type : ");
		typeComboBox = new JComboBox<>(typeList.toArray());
		searchButton = new JButton();
		preButton = new JButton();
		nextButton = new JButton();
		homeButton = new JButton();
		addFavorButton = new JButton("add to Favourite");
		bgLabel = new JLabel();
		pageLabel = new JLabel("Page : " + currentPage);
		emptyLine = new JLabel(
				"                                                                                                                                                                                                          ");

		ImageIcon iconSearch = new ImageIcon("Picture//search.png");
		Image imgSearch = iconSearch.getImage();
		Image newimg = imgSearch.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		iconSearch = new ImageIcon(newimg);

		iconDesk = new ImageIcon("Picture//desk.jpg");
		Image imgDesk = iconDesk.getImage();
		Image newimg2 = imgDesk.getScaledInstance(820, 600, Image.SCALE_SMOOTH);
		iconDesk = new ImageIcon(newimg2);

		iconBook = new ImageIcon("Picture//sampleBook.jpg");
		Image imgBook = iconBook.getImage();
		Image newimg3 = imgBook.getScaledInstance(150, 190, Image.SCALE_SMOOTH);
		iconBook = new ImageIcon(newimg3);

		ImageIcon iconNext = new ImageIcon("Picture//nextButton.png");
		Image imgNext = iconNext.getImage();
		Image newimg4 = imgNext.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconNext = new ImageIcon(newimg4);

		ImageIcon iconPre = new ImageIcon("Picture//previousButton.png");
		Image imgPre = iconPre.getImage();
		Image newimg5 = imgPre.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconPre = new ImageIcon(newimg5);

		ImageIcon iconHome = new ImageIcon("Picture//houseW.png");
		Image imgHome = iconHome.getImage();
		Image newimg7 = imgHome.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		iconHome = new ImageIcon(newimg7);

		nextButton.setIcon(iconNext);
		nextButton.setPreferredSize(new Dimension(35, 35));
		nextButton.setBackground(Color.BLACK);
		nextButton.addActionListener(new PageButtonAction());
		preButton.addActionListener(new PageButtonAction());
		preButton.setIcon(iconPre);
		preButton.setPreferredSize(new Dimension(35, 35));
		preButton.setBackground(Color.BLACK);
		preButton.setEnabled(false);
		if (havePage <= 1) {
			nextButton.setEnabled(false);
		}

		bgLabel.setIcon(iconDesk);
		bgLabel.add(emptyLine);

		pageLabel.setForeground(Color.WHITE);

		homeButton.setIcon(iconHome);
		homeButton.setPreferredSize(new Dimension(35, 35));
		homeButton.setBackground(Color.BLACK);

		searchButton.setIcon(iconSearch);
		searchButton.setBackground(Color.WHITE);
		searchButton.setPreferredSize(new Dimension(25, 25));
		searchResult.setForeground(Color.WHITE);
		searchLabel.setForeground(Color.WHITE);
		searchButton.addActionListener(new SearchAction());
		typeLabel.setForeground(Color.WHITE);

		searchField.addKeyListener(new SearchAction());

		northPanel.add(searchLabel);
		northPanel.add(searchField);
		northPanel.add(typeLabel);
		northPanel.add(typeComboBox);
		northPanel.add(searchButton);
		northPanel.add(searchResult);
		northPanel.setBackground(Color.BLACK);

		southPanel.setBackground(Color.BLACK);
		southPanel.add(homeButton);
		southPanel
				.add(new JLabel("                                                                                  "));
		southPanel.add(preButton);
		southPanel.add(pageLabel);
		southPanel.add(nextButton);
		southPanel.add(new JLabel(
				"                                                                                                "));

		createPanelPerPage(0);
		centerPanel.add(bgLabel);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}

	private void databaseSetUp(String type, String name) {
		data = new Database();
		bookFactory = BookFactory.getInstances();
		bookList = bookFactory.getBookList();
		typeFactory = TypeFactory.getInstances();
		typeList = typeFactory.getTypeList();
		Predicate<Book> filByType = (s) -> (s.getType().equalsIgnoreCase(type));
		Predicate<Book> filByName = (s) -> (s.getName().toLowerCase().contains(name.toLowerCase()));
		bookList = bookList.stream().filter(filByType).collect(Collectors.toList());
		if (!name.equals("")) {
			bookList = bookList.stream().filter(filByName).collect(Collectors.toList());
		}
		searchResult.setText("Search Result : " + bookList.size());

		havePage = (int) Math.ceil(bookList.size() / 2.0);
	}

	private JLabel createPanelPerPage(int start) {
		bgLabel = new JLabel(iconDesk);
		int line = 0;
		for (; start < bookList.size(); start++) {
			Book book = bookList.get(start);
			if (line == 2) {
				break;
			}
			JButton bookButton = new JButton(iconBook);
			JTextArea detailArea = new JTextArea();
			JLabel emptyLabel = new JLabel("      ");
			String detail = String.format("%s\nType : %s\nFile Location : %s\nDetail : %s", book.getName(),
					book.getType(), book.getLocation(), book.getDescription());
			bookButton.setPreferredSize(new Dimension(150, 190));
			bookButton.addActionListener(new BookClickAction());
			bookButton.addMouseListener(new ClickBookMouseAction());
			bookButton.setActionCommand("" + start);
			detailArea.setFont(new Font("Apple Casual", 3, 17));
			detailArea.setLineWrap(true);
			detailArea.setText(detail);
			detailArea.setPreferredSize(new Dimension(450, 175));
			detailArea.setBorder(new EmptyBorder(10, 10, 10, 10));
			detailArea.setBackground(Color.BLACK);
			detailArea.setForeground(Color.WHITE);
			bgLabel.add(bookButton);
			bgLabel.add(emptyLabel);
			bgLabel.add(detailArea);
			bgLabel.setLayout(new FlowLayout());
			line++;
		}
		return bgLabel;
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

	public void updateFrame() {
		havePage = (int) Math.ceil(bookList.size() / 2.0);
		pageLabel.setText("Page : " + currentPage);
		centerPanel.removeAll();
		centerPanel.add(createPanelPerPage((starterPage[currentPage - 1])));
		if (currentPage <= 1) {
			preButton.setEnabled(false);
		}
		if (currentPage + 1 > havePage) {
			nextButton.setEnabled(false);
		} else {
			nextButton.setEnabled(true);
		}

		// frame.validate();
	}

	public void run() {
		this.setVisible(true);
	}

	// public static void main(String[] args) {
	// SearchPageUI s = new SearchPageUI("Comic");
	// s.run();
	// }

	public class PageButtonAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
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

	public class ClickBookMouseAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				isDoubleClick = true;
			}
		}
	}

	public class BookClickAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isDoubleClick) {
				String fileLocation = bookList.get(Integer.parseInt(e.getActionCommand())).getLocation();
				openFile(fileLocation);
				isDoubleClick = false;
			}
		}
	}

	public class SearchAction implements ActionListener, KeyListener {
		private void doSearchAction() {
			currentPage = 1;
			String name = searchField.getText();
			type = typeComboBox.getSelectedItem().toString();
			databaseSetUp(type, name);
			updateFrame();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == searchButton) {
				doSearchAction();
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				doSearchAction();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

}
