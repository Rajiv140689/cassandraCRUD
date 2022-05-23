package com.example.cassandracrud.repository;

import com.example.cassandracrud.model.ShoppingCart;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends CassandraRepository<ShoppingCart, String> {
    @Query("Select * from shopping_cart where userid = ?0")
    @AllowFiltering
    List<ShoppingCart> findBycustomIdTest(String id);

    //Use existing method provided by CassandraRepository if not then need to specify the @Query param
//    Page<ShoppingCart> findAllPageable(Pageable pageable);

    @Query("Select * from shopping_cart where userid = ?0")
    @AllowFiltering
    Slice<ShoppingCart> findAlltest(String userid, Pageable pageable);


    Slice<ShoppingCart> findAll(Pageable pageable);
}
