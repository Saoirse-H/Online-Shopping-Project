package seller.controller;

import seller.model.Seller;
import seller.repository.SellerRepository;
import seller.exceptions.SellerNotFoundException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SellerController {
    
    @Autowired
    private SellerRepository sellerRepository;

    @RequestMapping(value="/all-sellers", method=RequestMethod.GET)
    public Iterable<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public Seller getUserByUserAndPass(@RequestParam("username") String username, @RequestParam("password") String password){
        return sellerRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @RequestMapping(value = "/username-exists", method = RequestMethod.GET)
    public Seller getUsername(@RequestParam("username") String username) {
        return sellerRepository.findByUsername(username).orElse(null);
    }

    @RequestMapping(value = "/add-seller", method = RequestMethod.POST)
    public Seller addSeller(@RequestBody Seller seller){
        sellerRepository.save(seller);
        return seller;
    }

    @RequestMapping(value = "get-seller/{id}", method=RequestMethod.GET)
    public Seller getSeller(@PathVariable("id") int userId) throws Exception {
        Optional<Seller> seller = sellerRepository.findById(userId);
        if (!seller.isPresent()) throw new SellerNotFoundException("Item with id "+userId+" not found.");
        return seller.get();
    }
}
