package stock.controller;


import model.Item;
import response.CategoryList;
import response.ItemList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stock.exceptions.ItemNotFoundException;
import stock.exceptions.OutOfStockException;
import stock.repository.StockRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;



@RestController
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @RequestMapping(value = "/items/modify/{id}", method = RequestMethod.PUT)
    public Item changeItemDescription(@PathVariable Integer id, @RequestBody Item newItem){
        Optional<Item> item = stockRepository.findById(id);
        if(item.isPresent()){
            Item _item = item.get();
            _item.setDescription(newItem.getDescription());
            stockRepository.save(_item);
            return _item;
        }else{
            throw new ItemNotFoundException("Item with id " + id + " not found");
        }
    }

    @RequestMapping(value = "/items/create", method = RequestMethod.POST)
    public String createItem(@RequestBody Item newItem){
        newItem.setItemId(0);
        stockRepository.save(newItem);
        return "Item added";
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ItemList getItems(){
        ItemList items = new ItemList(stockRepository.findAll());
        return items;
    }

    @RequestMapping(value = "/items/{category}")
    public ItemList getItemsByCategory(@PathVariable String category){
        return new ItemList((List<Item>) stockRepository.findAllByCategory(category));
    }

    @RequestMapping(value= "/items/categories", method = RequestMethod.GET)
    public CategoryList getCategories(){
        HashSet<String> uniqueCategories = new HashSet<>();
        ItemList items = new ItemList(stockRepository.findAll());
        for (Item item: items.getItems()) {
           uniqueCategories.add(item.getCategory());
        }
        List<String> list = new ArrayList<String>();
        CategoryList categories = new CategoryList(list);
        for(String category : uniqueCategories){
            categories.addCategory(category);
        }
        return categories;
    }
    @RequestMapping(value="/item/{id}", method=RequestMethod.GET)
    public Item getItem(@PathVariable("id") int id) throws Exception {
        Optional<Item> item = stockRepository.findById(id);
        if (!item.isPresent()) throw new ItemNotFoundException("Item with id "+id+" not found.");
        return item.get();
    }

    @RequestMapping(value="/bought/{id}", method=RequestMethod.POST)
    public void boughtItem(@PathVariable int id) {
        Item item = stockRepository.findById(id).orElse(null);

        if(item == null) throw new ItemNotFoundException("Item with id "+id+" not found");
        if(item.getQuantity() == 0) throw new OutOfStockException("Item: "+item.getItemId()+" Out of Stock.");

        item.setQuantity(item.getQuantity()-1);
        stockRepository.save(item);
    }

    @RequestMapping(value="/return/{id}", method=RequestMethod.POST)
    public void returnItem(@PathVariable int id) {
        Item item = stockRepository.findById(id).orElse(null);

        if(item == null) throw new ItemNotFoundException("Item with id "+id+" not found");
        if(item.getQuantity() == 0) throw new OutOfStockException("Item: "+item.getItemId()+" Out of Stock.");

        item.setQuantity(item.getQuantity()+1);
        stockRepository.save(item);
    }

    @RequestMapping(value="/seller-stock/{id}", method=RequestMethod.GET)
    public ItemList getSellerItem(@PathVariable("id") int sellerId) {
        List<Item> sellerItems = stockRepository.findBySellerId(sellerId);
        return new ItemList(sellerItems);
    }

    @RequestMapping(value = "items/remove/{id}", method=RequestMethod.POST)
    public String removeItem(@PathVariable("id") Integer itemId) {
        System.out.println("Got to stock controller");
        stockRepository.deleteById(itemId);
        return "Item Removed";
    }

}
