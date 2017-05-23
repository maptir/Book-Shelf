package bookshelf.gui;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BookShelfUI extends JFrame {
	static JPanel bsPanel;
	static HomeUI home;
	static SearchPageUI search;
	static FolderPageUI folder;
	static CardLayout c;

	public BookShelfUI() throws IOException {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(230, 20);
		this.setResizable(false);
		initscomponents();
	}

	public void initscomponents() throws IOException {
		home = new HomeUI();
		c = new CardLayout();
		bsPanel = new JPanel(c);

		bsPanel.add(home, "home");
		c.show(bsPanel, "home");

		this.add(bsPanel);
		this.pack();
	}

	public static void setHomeLayOut() {
		c.show(bsPanel, "home");
	}

	public static void setSearchLayOut(String type, String name) {
		search = new SearchPageUI(type, name);
		bsPanel.add(search, "search");
		c.show(bsPanel, "search");
	}

	public static void setFolderLayOut(String type) {
		folder = new FolderPageUI(type);
		bsPanel.add(folder, "folder");
		c.show(bsPanel, "folder");
	}

	public void run() {
		this.setVisible(true);
	}
}
