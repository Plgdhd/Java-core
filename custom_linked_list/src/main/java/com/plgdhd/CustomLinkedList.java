package com.plgdhd;

import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements CustomList<T> {

    public class Node<T> {

        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }

        public Node<T> getPrev() {
            return prev;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private long size;

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public long size() {
        return size;
    }

    @Override
    public void addFirst(T el) {
        Node<T> newHead = new Node<>(el);
        if (head == null) {
            head = newHead;
            tail = newHead;
        } else {
            newHead.setNext(head);
            head.setPrev(newHead);
            head = newHead;
        }
        size++;
    }

    @Override
    public void addLast(T el) {
        Node<T> newTail = new Node<>(el);
        if (tail == null) {
            head = newTail;
            tail = newTail;
        } else {
            newTail.setPrev(tail);
            tail.setNext(newTail);
            tail = newTail;
        }
        size++;
    }

    @Override
    public void add(int index, T el) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(el);
            return;
        }
        if (index == size) {
            addLast(el);
            return;
        }

        Node<T> buffer = head;
        for (int i = 0; i < index; i++) {
            buffer = buffer.getNext();
        }

        Node<T> newNode = new Node<>(el);
        newNode.setNext(buffer);
        newNode.setPrev(buffer.getPrev());
        buffer.getPrev().setNext(newNode);
        buffer.setPrev(newNode);
        size++;
    }

    @Override
    public T getFirst() {
        if (head == null)
            throw new NoSuchElementException("The first element doesn't exist");
        return head.getData();
    }

    @Override
    public T getLast() {
        if (tail == null)
            throw new NoSuchElementException("The last element doesn't exist");
        return tail.getData();
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> buffer = head;
        for (int i = 0; i < index; i++) {
            buffer = buffer.getNext();
        }
        return buffer.getData();
    }

    @Override
    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("The first element doesn't exist");
        }
        T savedData = head.getData();
        head = head.getNext();
        if (head != null) {
            head.setPrev(null);
        } else {
            tail = null;
        }
        size--;
        return savedData;
    }

    @Override
    public T removeLast() {
        if (tail == null) {
            throw new NoSuchElementException("The last element doesn't exist");
        }
        T savedData = tail.getData();
        tail = tail.getPrev();
        if (tail != null) {
            tail.setNext(null);
        } else {
            head = null;
        }
        size--;
        return savedData;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0)
            return removeFirst();
        if (index == size - 1)
            return removeLast();

        Node<T> buffer = head;
        for (int i = 0; i < index; i++) {
            buffer = buffer.getNext();
        }

        T savedData = buffer.getData();
        buffer.getPrev().setNext(buffer.getNext());
        buffer.getNext().setPrev(buffer.getPrev());
        size--;
        return savedData;
    }
}
