package response;

import java.io.Serializable;
import java.util.List;
import model.Item;

public class ItemList implements Serializable {
    
	private static final long serialVersionUID = 1L;
	private List<Item> items;

    public ItemList() { }

    public ItemList(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
}
