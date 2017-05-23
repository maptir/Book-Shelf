package bookshelf;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * The Drag n drop file that want to add into the home page and the folder page.
 * 
 * @author Archawin Tirugsapun,Triwith Mutitakul
 *
 */
public abstract class FileDragNDrop extends TransferHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// The file type that can add into the app.
	String[] file = { "pdf", "txt", "doc", "docx", "ppt", "pptx", "xls" };

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean canImport(TransferSupport ts) {
		return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	/**
	 * The data that you drag n drop into the app.
	 * 
	 * @return false if the drop file are not thing if it have a file return
	 *         true.
	 */
	public boolean importData(TransferSupport ts) {
		try {
			@SuppressWarnings("rawtypes")
			List data = (List) ts.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
			if (data.size() < 1) {
				return false;
			}

			for (Object item : data) {
				File file = (File) item;
				if (!file.isDirectory() && isReadFile(file.getName())) {
					importData(file);
				}
			}
			return true;

		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * True if the file are correct to the file that can add to the app else
	 * false.
	 * 
	 * @param name
	 *            of the file that you drag into the app.
	 * @return true if the file are correct to the file that can add to the app
	 *         else false.
	 */
	public boolean isReadFile(String name) {
		String[] split = name.split("\\.");
		for (String file : file) {
			if (split[split.length - 1].equals(file))
				return true;
		}
		return false;
	}

	/**
	 * Abstract method so that you can handle the file.
	 * 
	 * @param file
	 *            that you drag n drop it.
	 */
	public abstract void importData(File file);
}