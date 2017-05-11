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

/**
 * The Class that use for manage the database of this program.
 * 
 * @author Triwith Mutitakul
 */
public class Database {
	private List<Book> bookList = new ArrayList<Book>();
	private List<String> typeList = new ArrayList<String>();
	private InputStream input;
	private InputStream inputForType;
	private OutputStream output;
	private OutputStream outputForType;
	private File file = new File("database.txt");
	private File typeFile = new File("TypeDatabase.txt");
	private String line;
	private String[] temp;

	public Database() {
		readFromDatabase();
	}

	/**
	 * The method that use for read data from the database.
	 */
	private void readFromDatabase() {
		try {
			input = new FileInputStream(file);
			inputForType = new FileInputStream(typeFile);
			BufferedReader breader = new BufferedReader(new InputStreamReader(input));
			BufferedReader breaderForType = new BufferedReader(new InputStreamReader(inputForType));
			while ((line = breader.readLine()) != null) {
				temp = line.split(",");
				//Fix , problem 
				if(temp.length>4){
					for(int x = 4;x<temp.length;x++){
						temp[3]+=","+temp[x];
					}
				}
				bookList.add(new Book(temp[0], temp[1], temp[2], temp[3]));
			}
			while ((line = breaderForType.readLine()) != null) {
				temp = line.split(",");
				for (String type : temp) {
					if (!typeList.contains(type)) {
						typeList.add(type);
					}
				}
			}
			// Test Database
			for (Book book : bookList) {
				System.out.println(book.getName() + " -" + book.getType() + " -" + book.getLocation() + " -"
						+ book.getDescription());
			}
<<<<<<< HEAD
			// Test typeDatabase
=======
>>>>>>> 7d76b7df04e7ddfcf658e57ef7282e8a89cae6f3
			for (String type : typeList) {
				System.out.print(type + ",");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The method that use for add new book to the ArrayList of books.
	 * 
	 * @param filename
	 * @param fileDescription
	 * @param fileType
	 * @param fileLocation
	 */
	private void add(String filename, String fileType, String fileLocation, String fileDescription) {
		bookList.add(new Book(filename, fileType, fileLocation, fileDescription));
	}

	/**
	 * The method that use for add new type of category.
	 * 
	 * @param type
	 */
	private void addType(String type) {
		typeList.add(type);
	}

	/**
	 * The method that use for write the data to the database when the program
	 * is closed.
	 */
	private void close() {
		try {
			output = new FileOutputStream(file);
			outputForType = new FileOutputStream(typeFile);
			for (int x = 0; x < bookList.size(); x++) {
				byte[] byteTemp = (bookList.get(x).getName() + "," + bookList.get(x).getType() + ","
						+ bookList.get(x).getLocation() + "," + bookList.get(x).getDescription() + "\n").getBytes();
				output.write(byteTemp);
			}
			for (int x = 0; x < typeList.size(); x++) {
				byte[] byteTypeTemp = (typeList.get(x) + ",").getBytes();
				outputForType.write(byteTypeTemp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Test database
	 * 
	 * @param args
	 */
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
		d.add(name, type, loca, des);
		d.addType(type);
		d.close();
	}
}
