package bookshelf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Database {
	private List<Book> bookList = new ArrayList<Book>();
	private InputStream input;
	private OutputStream output;
	private File file = new File("database.txt");
	private String line;
	private String[] temp;

	public Database() {
		readFromDatabase();
	}

	private void readFromDatabase() {
		try {
			input = new FileInputStream(file);
			BufferedReader breader = new BufferedReader(new InputStreamReader(input));
			while ((line = breader.readLine()) != null) {
				temp = line.split(",");
				bookList.add(new Book(temp[0], temp[1], temp[2], temp[3]));
			}
			file.delete();
			for (Book book : bookList) {
				System.out.println(book.getName() +" -"+ book.getDescription() +" -"+ book.getType() +" -"+ book.getLocation());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(String filename, String fileDescription, String fileType, String fileLocation) {
		bookList.add(new Book(filename, fileDescription, fileType, fileLocation));
	}

	private void close() {
		try {
			output = new FileOutputStream(file);
			for (int x = 0; x < bookList.size(); x++) {
				byte[] byteTemp = (bookList.get(x).getName() + "," + bookList.get(x).getDescription() + ","
						+ bookList.get(x).getType() + "," + bookList.get(x).getLocation() + "\n").getBytes();
				output.write(byteTemp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Database d = new Database();
		System.out.print("File name : ");
		String name = sc.nextLine();
		System.out.print("File Des : ");
		String des = sc.nextLine();
		System.out.print("File Type : ");
		String type = sc.nextLine();
		System.out.print("File Location : ");
		String loca = sc.nextLine();

		d.add(name, des, type, loca);
		d.close();
	}
}
