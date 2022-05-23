package com.example.cassandracrud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "shopping_cart")
public class ShoppingCart {
    @PrimaryKey
    @Column(value = "userid")
    private String userId;

    @Column(value = "item_count")
    private int itemCount;
}
