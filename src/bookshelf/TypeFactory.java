package bookshelf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TypeFactory {
	private static TypeFactory typeFactory = null;
	private List<String> typeList = new ArrayList<>();

	protected TypeFactory() {
	}

	public static TypeFactory getInstances() {
		if (typeFactory == null) {
			typeFactory = new TypeFactory();
		}
		return typeFactory;
	}

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

	public void removeType(String type) {
		if (typeList.contains(type)) {
			typeList.remove(type);
		}
	}

	public List<String> getTypeList() {
		return typeList;
	}

}
