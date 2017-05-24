package bookshelf.gui;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The UI that use to switch page in the application by CardLayout.
 * 
 * @author Archawin Tirugsapun,Triwith Mutitakul
 *
 */
public class BookShelfUI {
	static JFrame frame;
	static JPanel bsPanel;
	static HomeUI home;
	static SearchPageUI search;
	static FolderPageUI folder;
	static CardLayout c;

	/**
	 * Set the frame of the application.
	 * 
	 * @throws IOException
	 */
	public BookShelfUI() throws IOException {
		frame = new JFrame("Book-Shelf");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(230, 20);
		frame.setResizable(false);
		initscomponents();
	}

	/**
	 * Initialize all the components in the frame.
	 * 
	 * @throws IOException
	 */
	public void initscomponents() throws IOException {
		home = new HomeUI();
		c = new CardLayout();
		bsPanel = new JPanel(c);

		frame.setTitle("Book-Shelf");
		bsPanel.add(home, "home");
		c.show(bsPanel, "home");

		frame.add(bsPanel);
		frame.pack();
	}

	/**
	 * Set change of the layout to the home page.
	 */
	public static void setHomeLayOut() {
		frame.setTitle("Book-Shelf");
		c.show(bsPanel, "home");
	}

	/**
	 * Set change of the layout to the search page by the type and name that you
	 * want to search.
	 * 
	 * @param type
	 *            is the type that you want to search in.
	 * @param name
	 *            is the name of the book that want to search.
	 * 
	 */
	public static void setSearchLayOut(String type, String name) {
		search = new SearchPageUI(type, name);
		bsPanel.add(search, "search");
		c.show(bsPanel, "search");
	}

	/**
	 * Set change of the layout to the folder page by type.
	 * 
	 * @param type
	 *            is the type that you want to see.
	 */
	public static void setFolderLayOut(String type) {
		frame.setTitle(type + "-Shelf");
		folder = new FolderPageUI(type);
		bsPanel.add(folder, "folder");
		c.show(bsPanel, "folder");
	}

	/**
	 * Set visible to the JFrame.
	 */
	public void run() {
		frame.setVisible(true);
	}
}
