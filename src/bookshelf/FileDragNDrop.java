package bookshelf;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public abstract class FileDragNDrop extends TransferHandler {
	String[] file = { "pdf", "txt", "doc", "docx", "ppt", "pptx" };

	public int getSourceActions(JComponent c) {
		return COPY_OR_MOVE;
	}

	public boolean canImport(TransferSupport ts) {
		return ts.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

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

	public boolean isReadFile(String name) {
		String[] split = name.split("\\.");
		for (String file : file) {
			if (split[split.length - 1].equals(file))
				return true;
		}
		return false;
	}

	public abstract void importData(File file);
}