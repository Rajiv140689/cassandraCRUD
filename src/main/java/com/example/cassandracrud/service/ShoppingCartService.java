package com.example.cassandracrud.service;

import com.datastax.oss.driver.api.core.cql.PagingState;
import com.example.cassandracrud.model.InputRequest;
import com.example.cassandracrud.model.ShoppingCart;
import com.example.cassandracrud.model.ShoppingCartResponse;
import com.example.cassandracrud.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public List<ShoppingCart> saveRecords(){
        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(new ShoppingCart("99", 40));
        shoppingCartList.add(new ShoppingCart("88", 90));

        return shoppingCartRepository.saveAll(shoppingCartList);
    }

    public List<ShoppingCart> getAllRecords(){
        return shoppingCartRepository.findAll();
    }

    public List<ShoppingCart> getRecordById(String id){
        return shoppingCartRepository.findBycustomIdTest(id);
    }

    private static final String SORT_FIELD = "item_count";
    private static final String DEFAULT_CURSOR_MARK = "-1";
    private static final Integer PageNumber = 0;

    public ShoppingCartResponse getPaginatedRecords(String pageSize, String pagingState){

//        Pageable pageable = CassandraPageRequest.of(PageRequest.of(PageNumber, Integer.parseInt(pageSize), Sort.by(Sort.Direction.DESC,
//                        SORT_FIELD)), DEFAULT_CURSOR_MARK.equalsIgnoreCase(
//                        pagingState) ? null : (ByteBuffer) PagingState.fromString(pagingState));

        CassandraPageRequest pageable = CassandraPageRequest.of(PageRequest.of(PageNumber,
        Integer.parseInt(pageSize)), DEFAULT_CURSOR_MARK.equalsIgnoreCase(
                pagingState) ? null : (ByteBuffer) PagingState.fromString(pagingState));

        Slice<ShoppingCart> sliceShoppingCart = shoppingCartRepository.findAll(pageable);

        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();

        shoppingCartResponse.setPageSize(sliceShoppingCart.getPageable().getPageSize());
        shoppingCartResponse.setPageSize(Integer.parseInt(pageSize));
        shoppingCartResponse.setShoppingCart(sliceShoppingCart.getContent());

        ShoppingCartResponse finalShoppingCartResponse = new ShoppingCartResponse();
        if(sliceShoppingCart.isLast()) {
            shoppingCartResponse.setPageable(null);
            return shoppingCartResponse;
        } else {
            shoppingCartResponse.setPageable((CassandraPageRequest)sliceShoppingCart.getPageable());
            finalShoppingCartResponse = internalCallwithpageState(shoppingCartResponse);
        }

        return finalShoppingCartResponse;
    }


    public ShoppingCartResponse internalCallwithpageState(ShoppingCartResponse shoppingCartResponse){
        String resourceURL = "http://localhost:8080/api/shopping_cart/pages/v2/";
        RestTemplate restTemplate = new RestTemplate();
        InputRequest inputRequest = new InputRequest();
        inputRequest.setPageSize("3");
        inputRequest.setPageablePagingState(shoppingCartResponse.getPageable().getPagingState());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InputRequest> httpEntity = new HttpEntity<>(inputRequest, headers);
        return restTemplate.postForEntity(resourceURL, httpEntity, ShoppingCartResponse.class).getBody();
    }


    public Slice<ShoppingCart> getPaginatedRecordsV2(String pageSize, ByteBuffer pageable){
        CassandraPageRequest pageableRequest = CassandraPageRequest.of(PageRequest.of(PageNumber,
                Integer.parseInt(pageSize)), DEFAULT_CURSOR_MARK.equalsIgnoreCase(
                pageable.toString()) ? null :
                pageable);

//        Slice<ShoppingCart> sliceShoppingCartV2 = shoppingCartRepository.findAll(pageableRequest);

//        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
//
//        shoppingCartResponse.setPageSize(sliceShoppingCartV2.getPageable().getPageSize());
//        shoppingCartResponse.setPageSize(Integer.parseInt(pageSize));
//        shoppingCartResponse.setShoppingCart(sliceShoppingCartV2.getContent());

        return shoppingCartRepository.findAll(pageableRequest);
    }

    public ShoppingCartResponse getPaginatedRecordsWithoutPageState(String PageNumber, String pageSize){

        Pageable pageable = PageRequest.of(Integer.parseInt(PageNumber),
                Integer.parseInt(pageSize));

        Slice<ShoppingCart> sliceShoppingCart = shoppingCartRepository.findAll(pageable);

        ShoppingCartResponse shoppingCartResponse = new ShoppingCartResponse();
        shoppingCartResponse.setPageSize(sliceShoppingCart.getPageable().getPageSize());
        shoppingCartResponse.setPageSize(Integer.parseInt(pageSize));
        shoppingCartResponse.setShoppingCart(sliceShoppingCart.getContent());

        if(sliceShoppingCart.isLast()) {
            shoppingCartResponse.setPageable(null);
            return shoppingCartResponse;
        } else {
            shoppingCartResponse.setPageable((CassandraPageRequest) sliceShoppingCart.getPageable());
        }

        return shoppingCartResponse;
    }
}
