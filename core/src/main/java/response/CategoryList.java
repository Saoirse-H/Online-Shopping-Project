package response;

import java.io.Serializable;
import java.util.List;

public class CategoryList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> categories;

    public CategoryList() { }

    public CategoryList(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getCategories() {
        return this.categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category){
        categories.add(category);
    }
}
