package bookshelf.gui;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BookShelfUI extends JFrame {
	static JPanel bsPanel;
	static HomeUI home;
	static CardLayout c;
	static SearchPageUI search;

	public BookShelfUI() throws IOException {
		super("Book-Shelf");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(230, 20);
		initscomponents();
	}

	public void initscomponents() throws IOException {
		home = new HomeUI();
		search = new SearchPageUI("");
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

	public void run() {
		this.setVisible(true);
	}
}
