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
 * @author Triwith Mutitakul
 *
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
				bookList.add(new Book(temp[0], temp[1], temp[2], temp[3]));
			}
			while ((line = breaderForType.readLine()) != null) {
				temp = line.split(",");
				for(String type:temp){
					if(!typeList.contains(type)){
						typeList.add(type);
					}
				}
			}
			// Test Database
			for (Book book : bookList) {
				System.out.println(book.getName() + " -" + book.getDescription() + " -" + book.getType() + " -"
						+ book.getLocation());
			}
			for(String type:typeList){
				System.out.print(type+",");
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
	private void add(String filename, String fileDescription, String fileType, String fileLocation) {
		bookList.add(new Book(filename, fileDescription, fileType, fileLocation));
	}
	/**
	 * The method that use for add new type of category.
	 * @param type
	 */
	private void addType(String type){
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
				byte[] byteTemp = (bookList.get(x).getName() + "," + bookList.get(x).getDescription() + ","
						+ bookList.get(x).getType() + "," + bookList.get(x).getLocation() + "\n").getBytes();
				output.write(byteTemp);
			}
			for(int x = 0;x<typeList.size();x++){
				byte[] byteTypeTemp = (typeList.get(x)+",").getBytes();
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
		d.add(name, des, type, loca);
		d.addType(type);
		d.close();
	}
}
