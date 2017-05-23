package PartOfProgram;

import java.io.IOException;

import org.apache.pdfbox.tools.PDFToImage;

public class PdfToImage {
	byte[] bytes;

	public static void main(String[] args) {
		String[] argss = new String[7];
		argss[0] = "-startPage";
		argss[1] = "1";
		argss[2] = "-endPage";
		argss[3] = "1";
		argss[4] = "-outputPrefix";
		argss[5] = "MyJpgFile";
		argss[6] = "css.pdf";
		try {
			PDFToImage.main(argss);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
