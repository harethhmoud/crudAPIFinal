package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Size;
import com.example.demo.model.Category;
import com.example.demo.model.Categories;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.CategoryRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController //restful web services
@RequestMapping("/api")
public class ItemController{
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //response entity is the http response, incl status code, headers, and body.
    @GetMapping("/items") //handles get requests to api/items
    public ResponseEntity<List<Item>> getAllItems(@RequestParam(required = false) String title){
        try{
            List<Item> items = new ArrayList<Item>();
            if(title == null){
                itemRepository.findAll().forEach(items::add);
            }else{
                itemRepository.findByTitle(title).forEach(items::add);
            }
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("items/{id}") // structure of url (e.g. items/123) Spring sees this url and passes the 123 to the
    // pathvariable
    public ResponseEntity<Item> getItemById(@PathVariable("id") Long id){//pathvariable basically
        //passes the id from the url to the method
        Optional<Item> itemData = itemRepository.findById(id);

        if(itemData.isPresent()){
            return new ResponseEntity<>(itemData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/items/title")
    public ResponseEntity<List<Item>> findByTitle(@RequestParam("title") String title){
        try{
            List<Item> items = itemRepository.findByTitle(title);
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //add by findbycategory
    // and subcategory
    @GetMapping("/items/size")
    public ResponseEntity<List<Item>> findBySize(@RequestParam("size") Size size){
        try{
            List<Item> items = itemRepository.findBySize(size);
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/price")
    public ResponseEntity<List<Item>> findByPrice(@RequestParam("price") float price){
        try{
            List<Item> items = itemRepository.findByPrice(price);
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        try {
            //System.out.println("wagwan");

            Category category = item.getCategory();
            if (category == null || category.getName() == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
            if (existingCategory.isPresent()) {
                item.setCategory(existingCategory.get());
            } else {
                Category savedCategory = categoryRepository.save(category);
                item.setCategory(savedCategory);
            }

            Item _item = itemRepository.save(item);
            //System.out.println("Saved Item: " + _item);
            return new ResponseEntity<>(_item, HttpStatus.CREATED);
        } catch (Exception e) {
            //e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") Long id, @RequestBody Item item){
        Optional<Item> itemData = itemRepository.findById(id);

        if(itemData.isPresent()){
            Item _item = itemData.get(); //different to parameter item
            _item.setTitle(item.getTitle());
            _item.setDescription(item.getDescription());
            _item.setSize(item.getSize());
            _item.setPrice(item.getPrice());
            return new ResponseEntity<>(itemRepository.save(_item),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id){
        try{
            itemRepository.deleteById(id); // Corrected method name
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/items")
    public ResponseEntity<HttpStatus> deleteAllItems(){
        try{
            itemRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/items/category/{name}")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable String name) {
        try {
            Categories category = Categories.valueOf(name.toUpperCase());
            List<Item> items = itemRepository.findByCategory_Name(category);
            if (items.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}














