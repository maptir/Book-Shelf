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
	private List<String> favoList = new ArrayList<>();
	private InputStream input;
	private InputStream inputForType;
	private OutputStream output;
	private OutputStream outputForType;
	private File file = new File("database.txt");
	private File typeFile = new File("TypeFavorDatabase.txt");
	private String line;
	private String[] temp;
	private BookFactory bookFactory = BookFactory.getInstances();
	private TypeFactory typeFactory = TypeFactory.getInstances();

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
				if (line.equals("")) {
					continue;
				}
				temp = line.split(",");
				// Fix spliter problem.
				if (temp.length > 4) {
					for (int x = 4; x < temp.length; x++) {
						temp[3] += "," + temp[x];
					}
				}
				bookFactory.add(temp[0], temp[1], temp[2], temp[3]);
			}
			line = breaderForType.readLine();
			temp = line.split(",");
			for (String type : temp) {
				typeFactory.addType(type);
			}
			line = breaderForType.readLine();
			temp = line.split(",");
			for (String favor : temp) {
				favoList.add(favor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getFavorList() {
		return favoList;
	}

	public void addFavor(String index) {
		if (!favoList.contains(index)) {
			favoList.add(index);
		}
	}

	public void removeFavor(String index) {
		if (favoList.contains(index)) {
			favoList.remove(index);
		}
	}

	/**
	 * The method that use for write the data to the database when the program
	 * is closed.
	 */
	public void close() {
		try {
			bookList = bookFactory.getBookList();
			typeList = typeFactory.getTypeList();
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
			outputForType.write("\n".getBytes());
			for (int x = 0; x < favoList.size(); x++) {
				byte[] byteTypeTemp = (favoList.get(x) + ",").getBytes();
				outputForType.write(byteTypeTemp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Database d = new Database();
		while (true) {
			System.out.println("Add ? : ");
			if (sc.nextLine().equals("q")) {
				break;
			}
			System.out.println("File name : ");
			String name = sc.nextLine();
			System.out.println("File Type : ");
			String type = sc.nextLine();
			System.out.println("File Location : ");
			String loca = sc.nextLine();
			System.out.println("File Description :");
			String des = sc.nextLine();
			BookFactory.getInstances().add(name, type, loca, des);
			d.close();
		}
	}
}
