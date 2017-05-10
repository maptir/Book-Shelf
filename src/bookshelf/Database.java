package bookshelf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Database {
	private List<String> bookList = new ArrayList<String>();
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
				for (String list : temp) {
					bookList.add(list);
				}
			}
			file.delete();
			System.out.println(Arrays.toString(bookList.toArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add(String fileLocation) {
		bookList.add(fileLocation);
	}

	private void close() {
		try {
			output = new FileOutputStream(file);
			for (int x = 0; x < bookList.size(); x++) {
				byte[] byteTemp = (bookList.get(x) + ",").getBytes();
				output.write(byteTemp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Database d = new Database();
		System.out.println("File Input : ");
		String in = sc.nextLine();
		d.add(in);
		d.close();
	}

}
