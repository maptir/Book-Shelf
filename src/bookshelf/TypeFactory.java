package bookshelf;

import java.util.ArrayList;
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
