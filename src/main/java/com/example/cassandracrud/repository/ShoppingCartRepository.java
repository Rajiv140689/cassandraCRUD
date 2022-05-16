package com.example.cassandracrud.repository;

import com.example.cassandracrud.model.ShoppingCart;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface ShoppingCartRepository extends CassandraRepository<ShoppingCart, String> {

}
