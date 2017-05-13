package bookshelf;

import java.io.IOException;

import bookshelf.gui.BookShelfUI;

public class BookShelf {
	public static void main(String[] args) throws IOException {
		BookShelfUI ui = new BookShelfUI();
		ui.run();
	}
}
