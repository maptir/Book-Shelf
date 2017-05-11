package bookshelf;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import bookshelf.gui.BookShelfUI;

public class Main {
public static void main(String[] args) {
	List<JPanel> list = new ArrayList<JPanel>();
	new BookShelfUI().run();
}
}
