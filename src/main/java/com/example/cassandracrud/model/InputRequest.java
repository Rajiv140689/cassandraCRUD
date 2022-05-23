package com.example.cassandracrud.model;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
public class InputRequest {
    private String pageSize;
    private String pagingState;
    private ByteBuffer pageablePagingState;
}
