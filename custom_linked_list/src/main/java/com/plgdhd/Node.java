package com.plgdhd;

import lombok.Data;

@Data
public class Node<T> {
    
    private T data;
    private Node<T> next;
    private Node<T> prev;

    public Node(T data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
