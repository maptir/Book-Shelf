package bookshelf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The Singleton class that use for create and contain the type of book.
 * 
 * @author Archawin Tirugsapun,Triwith Mutitakul
 *
 */
public class TypeFactory {
	private static TypeFactory typeFactory = null;
	private List<String> typeList = new ArrayList<>();

	/**
	 * The constructor.
	 */
	protected TypeFactory() {
	}

	/**
	 * The method that use for get the Object of TypeFactory class.
	 * 
	 * @return TypeFactory
	 */
	public static TypeFactory getInstances() {
		if (typeFactory == null) {
			typeFactory = new TypeFactory();
		}
		return typeFactory;
	}

	/**
	 * The method that use for add new type of book.
	 * 
	 * @param type
	 */
	public void addType(String type) {
		if (!typeList.contains(type)) {
			typeList.add(type);
			typeList.sort(new Comparator<String>() {
				@Override
				public int compare(String t1, String t2) {
					return t1.compareTo(t2);
				}
			});
		}
	}

	/**
	 * The method that use for remove the type.
	 * 
	 * @param type
	 */
	public void removeType(String type) {
		if (typeList.contains(type)) {
			typeList.remove(type);
			BookFactory.getInstances().removeBookByType(type);
		}
	}

	/**
	 * The method that use for get the type list.
	 * 
	 * @return list of type.
	 */
	public List<String> getTypeList() {
		return typeList;
	}

}
