package bookshelf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookFactory {
	private static BookFactory factory = null;
	private List<Book> bookList = new ArrayList<Book>();

	protected BookFactory() {
	}

	public static BookFactory getInstances() {
		if (factory == null) {
			factory = new BookFactory();
		}
		return factory;
	}

	public void add(String filename, String fileType, String fileLocation, String fileDescription) {
		Predicate<Book> filByName = (s) -> (s.getName().toLowerCase().contains(filename.toLowerCase()));
		List<Book> temp = bookList.stream().filter(filByName).collect(Collectors.toList());
		if (temp.size() == 0) {
			bookList.add(new Book(filename, fileType, fileLocation, fileDescription));
		}
	}

	public void removeBook(String name, String des) {
		for (int x = 0; x < bookList.size(); x++) {
			if (bookList.get(x).getName().equalsIgnoreCase(name)
					&& bookList.get(x).getDescription().equalsIgnoreCase(des)) {
				bookList.remove(x);
			}
		}
	}

	public void removeBookByType(String type) {
		for (int x = 0; x < bookList.size(); x++) {
			if (bookList.get(x).getType().equals(type)) {
				bookList.remove(x);
			}
		}
	}

	public List<Book> getBookList() {
		return bookList;
	}

}
