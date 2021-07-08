package consumer.controller;

import consumer.model.Consumer;
import consumer.repository.ConsumerRepository;
import consumer.exceptions.ConsumerNotFoundException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerRepository consumerRepository;

    @RequestMapping(value = "/all-consumers", method=RequestMethod.GET)
    public Iterable<Consumer> getUsers() {
        return consumerRepository.findAll();
    }

    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    public Consumer getUserByUserAndPass(@RequestParam("username") String username, @RequestParam("password") String password){
        return consumerRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @RequestMapping(value = "/username-exists", method = RequestMethod.GET)
    public Consumer getUsername(@RequestParam("username") String username) {
        return consumerRepository.findByUsername(username).orElse(null);
    }

    @RequestMapping(value = "/add-consumer", method = RequestMethod.POST)
    public Consumer addConsumer(@RequestBody Consumer consumer){
        consumerRepository.save(consumer);
        return consumer;
    }

    @RequestMapping(value = "get-consumer/{id}", method=RequestMethod.GET)
    public Consumer getConsumer(@PathVariable("id") int userId) throws Exception {
        Optional<Consumer> consumer = consumerRepository.findById(userId);
        if (!consumer.isPresent()) throw new ConsumerNotFoundException("Item with id "+userId+" not found.");
        return consumer.get();
    }

}
