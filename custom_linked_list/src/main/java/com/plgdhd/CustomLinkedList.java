package com.plgdhd;

import java.util.NoSuchElementException;

import lombok.Data;

@Data
public class CustomLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private long size;

    public long size() {
        return size;
    }

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

    public T getFirst() {
        if (head == null)
            throw new NoSuchElementException("The first element doesn't exist");
        return head.getData();
    }

    public T getLast() {
        if (tail == null)
            throw new NoSuchElementException("The last element doesn't exist");
        return tail.getData();
    }

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

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

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
