package bookshelf;

import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import bookshelf.gui.HomeUI;

public class Main {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

		HomeUI ui = new HomeUI();
		ui.run();
	}
}
