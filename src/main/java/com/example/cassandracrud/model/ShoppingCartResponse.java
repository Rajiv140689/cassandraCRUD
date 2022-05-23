package com.example.cassandracrud.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;

import java.util.List;

@Getter
@Setter
public class ShoppingCartResponse {
    private Integer TotalPages;
    private Integer pageSize;
    private List<ShoppingCart> shoppingCart;
    private CassandraPageRequest pageable;
}
