package bookshelf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The Singleton class that use for create and contain a book.
 * 
 * @author Archawin Tirugsapun,Triwith Mutitakul
 *
 */
public class BookFactory {
	private boolean isAdd = false;
	private static BookFactory factory = null;
	private List<Book> bookList = new ArrayList<Book>();
	private List<String> favorList = new ArrayList<>();

	/**
	 * The constructor.
	 */
	protected BookFactory() {
	}

	/**
	 * The method that use for get the object of BookFactory.
	 * 
	 * @return BookFactory
	 */
	public static BookFactory getInstances() {
		if (factory == null) {
			factory = new BookFactory();
		}
		return factory;
	}

	/**
	 * The method that use for create the new book and add it to the book list.
	 * 
	 * @param filename
	 * @param fileType
	 * @param fileLocation
	 * @param fileDescription
	 */
	public void add(String filename, String fileType, String fileLocation, String fileDescription) {
		isAdd = false;
		Predicate<Book> filByName = (s) -> (s.getName().toLowerCase().contains(filename.toLowerCase()));
		List<Book> temp = bookList.stream().filter(filByName).collect(Collectors.toList());
		if (temp.size() == 0) {
			isAdd = true;
			bookList.add(new Book(filename, fileType, fileLocation, fileDescription));
		}
		bookList.sort(new Comparator<Book>() {
			@Override
			public int compare(Book b1, Book b2) {
				return b1.getName().compareTo(b2.getName());
			}
		});
	}

	/**
	 * Get if can add file to a book return true else false.
	 * 
	 * @return 
	 */
	public boolean isAdd() {
		return isAdd;
	}


	/**
	 * The method that use for return the favor list.
	 * 
	 * @return favorite book list.
	 */
	public List<String> getFavorList() {
		return favorList;
	}

	/**
	 * The method that use for add the book to the favorite book list.
	 * 
	 * @param index
	 */
	public void addFavor(String index) {
		if (!favorList.contains(index)) {
			favorList.add(index);
		}
	}

	/**
	 * The method that use for remove the book from the favorite book list.
	 * 
	 * @param index
	 */
	public void removeFavor(String index) {
		if (favorList.contains(index)) {
			favorList.remove(index);
		}
	}

	/**
	 * The method that use for remove the book from the book list.
	 * 
	 * @param name
	 * @param des
	 */
	public void removeBook(String name, String des) {
		for (int x = 0; x < bookList.size(); x++) {
			if (bookList.get(x).getName().equalsIgnoreCase(name)
					&& bookList.get(x).getDescription().equalsIgnoreCase(des)) {
				bookList.remove(x);
				removeFavor(x + "");
			}
		}
	}

	/**
	 * The method that use for remove all of the book that have the same type as
	 * input parameter.
	 * 
	 * @param type
	 */
	public void removeBookByType(String type) {
		for (int x = bookList.size() - 1; x >= 0; x--) {
			if (bookList.get(x).getType().equals(type))
				bookList.remove(x);
		}
	}

	/**
	 * the method that use for return the book list.
	 * 
	 * @return
	 */
	public List<Book> getBookList() {
		return bookList;
	}

	/**
	 * The method that use for get the book by index.
	 * 
	 * @param index
	 * @return Book
	 */
	public Book getBook(int index) {
		return bookList.get(index);
	}

}