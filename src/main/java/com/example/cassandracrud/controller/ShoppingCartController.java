package com.example.cassandracrud.controller;

import com.example.cassandracrud.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cassandracrud.repository.ShoppingCartRepository;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartController(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @PostMapping(path = "/save")
    public ResponseEntity save(){
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(new ShoppingCart("99", 40));
        shoppingCartList.add(new ShoppingCart("88", 90));

        List<ShoppingCart> shoppingCartListOutput = shoppingCartRepository.saveAll(shoppingCartList);
        return new ResponseEntity<>(shoppingCartListOutput, HttpStatus.OK);
    }

    @GetMapping(path = "/getshoppingcartdata", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShoppingCart> getData() {
        return shoppingCartRepository.findAll();
    }
}
