package com.example.cassandracrud.controller;

import com.example.cassandracrud.model.InputRequest;
import com.example.cassandracrud.model.ShoppingCart;
import com.example.cassandracrud.model.ShoppingCartResponse;
import com.example.cassandracrud.service.ShoppingCartService;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }


    @PostMapping(path = "/save")
    public ResponseEntity save(){

        List<ShoppingCart> shoppingCartListOutput = shoppingCartService.saveRecords();
        return new ResponseEntity<>(shoppingCartListOutput, HttpStatus.OK);
    }


    @GetMapping(path = "/getshoppingcartdata", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShoppingCart> getData() {
        return shoppingCartService.getAllRecords();
    }


    @GetMapping(path = "/getshoppingcartdataById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShoppingCart> getDataById(@PathVariable String id) {
        return shoppingCartService.getRecordById(id);
    }


    @GetMapping(path = "/shopping_cart/pages/", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCartResponse getPaginatedData(@RequestBody InputRequest inputRequest) {
        return shoppingCartService.getPaginatedRecords(inputRequest.getPageSize(),
                inputRequest.getPagingState());
    }


    @PostMapping(path = "/shopping_cart/pages/v2/", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCartResponse getPaginatedDataV2(@RequestBody InputRequest inputRequest) {
        Slice<ShoppingCart> sliceShoppingCartV2;
        sliceShoppingCartV2 = shoppingCartService.getPaginatedRecordsV2(inputRequest.getPageSize(),
                inputRequest.getPageablePagingState());

        while(!sliceShoppingCartV2.isLast()){
            sliceShoppingCartV2 = shoppingCartService.getPaginatedRecordsV2(inputRequest.getPageSize(),
                    ((CassandraPageRequest)sliceShoppingCartV2.getPageable()).getPagingState());
        }

        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();

        shoppingCartResponse.setPageSize(sliceShoppingCartV2.getPageable().getPageSize());
        shoppingCartResponse.setPageSize(Integer.parseInt(inputRequest.getPageSize()));
        shoppingCartResponse.setShoppingCart(sliceShoppingCartV2.getContent());
        shoppingCartResponse.setPageable(null);

        return shoppingCartResponse;
    }


    @GetMapping(path = "/shopping_cart_without_page_state/pages/{pageNumber}/{pageSize}", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCartResponse getPaginatedDataWithoutPageState(@PathVariable String pageNumber,
                                                 @PathVariable String pageSize) {
        return shoppingCartService.getPaginatedRecordsWithoutPageState(pageNumber, pageSize);
    }
}
