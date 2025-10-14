package com.plgdhd;

import com.plgdhd.CustomLinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CustomLinkedListTest {

  private CustomLinkedList<Integer> list;

  @BeforeEach
  void start() {
    list = new CustomLinkedList<>();
  }

  @Test
  @DisplayName("Empty list size should be 0")
  void testEmptySize() {
    assertEquals(0, list.size());
  }

  @Test
  @DisplayName("Exception when getting first and last from empty list")
  void testGetFromEmpty() {
    assertThrows(NoSuchElementException.class, () -> list.getFirst());
    assertThrows(NoSuchElementException.class, () -> list.getLast());
  }

  @Test
  @DisplayName("Exception when removing first and last from empty list")
  void testRemoveFromEmpty() {
    assertThrows(NoSuchElementException.class, () -> list.removeFirst());
    assertThrows(NoSuchElementException.class, () -> list.removeLast());
  }

  @Test
  @DisplayName("Increasing the size when first element was added")
  void testIncreaseFisrt() {
    list.addFirst(7);
    assertEquals(1, list.size());
  }

  @ParameterizedTest
  @ValueSource(ints = { 1, 2, 3 })
  @DisplayName("Adding elements into the list")
  void testAdditions(int count) {
    for (int i = 0; i < count; ++i) {
      list.addLast(i);
    }
    assertEquals(count, list.size());
  }

  @Test
  @DisplayName("Adding by index")
  void testAddatIndex() {
    list.addLast(17);
    list.addLast(14);
    list.add(1, 42);
    assertEquals(3, list.size());
    assertEquals(Integer.valueOf(17), list.get(0));
    assertEquals(Integer.valueOf(42), list.get(1));
    assertEquals(Integer.valueOf(14), list.get(2));
  }

  @Test
  @DisplayName("Wrong index")
  void testWrongIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 4));
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 3));
    list.addFirst(7);
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 1));
  }

  @Nested
  @DisplayName("Getting element operations")
  class GettingTest {

    @BeforeEach
    void setUp() {
      list.addLast(10);
      list.addLast(12);
      list.addLast(14);
      list.addLast(23);
      list.addLast(8);
    }

    @Test
    @DisplayName("Getting the first")
    void testGetFirst() {
      assertEquals(Integer.valueOf(10), list.getFirst());
    }

    @Test
    @DisplayName("Getting the last element")
    void testGetLast() {
      assertEquals(Integer.valueOf(8), list.getLast());
    }

    @ParameterizedTest
    @CsvSource({ "0, 10", "1, 12", "2, 14" })
    @DisplayName("Getting an element by index")
    void testGetByIndex(int index, Integer expected) {
      assertEquals(expected, list.get(index));
    }

    @Test
    @DisplayName("Getting by invalid index should throw an exception")
    void testGetInvalidIndex() {
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
    }
  }

  @Nested
  @DisplayName("Element Retrieval Operations")
  class ElementRetrievalTests {

    @BeforeEach
    void initializeList() {
      list.addLast(100);
      list.addLast(200);
      list.addLast(300);
      list.addLast(400);
      list.addLast(500);
    }

    @Test
    @DisplayName("Should retrieve the first element")
    void shouldRetrieveFirstElement() {
      assertEquals(Integer.valueOf(100), list.getFirst());
    }

    @Test
    @DisplayName("Should retrieve the last element")
    void shouldRetrieveLastElement() {
      assertEquals(Integer.valueOf(500), list.getLast());
    }

    @ParameterizedTest
    @CsvSource({ "0, 100", "1, 200", "2, 300" })
    @DisplayName("Should retrieve element by index")
    void shouldRetrieveElementByIndex(int index, Integer expected) {
      assertEquals(expected, list.get(index));
    }

    @Test
    @DisplayName("Should throw exception for invalid index")
    void shouldThrowExceptionForInvalidIndex() {
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
    }
  }

  @Nested
  @DisplayName("Element Deletion Operations")
  class ElementDeletionTests {

    @BeforeEach
    void initializeList() {
      list.addLast(50);
      list.addLast(60);
      list.addLast(70);
      list.addLast(80);
    }

    @Test
    @DisplayName("Should delete the first element")
    void shouldDeleteFirstElement() {
      assertEquals(Integer.valueOf(50), list.removeFirst());
      assertEquals(3, list.size());
      assertEquals(Integer.valueOf(60), list.getFirst());
    }

    @Test
    @DisplayName("Should delete the last element")
    void shouldDeleteLastElement() {
      assertEquals(Integer.valueOf(80), list.removeLast());
      assertEquals(3, list.size());
      assertEquals(Integer.valueOf(70), list.getLast());
    }

    @Test
    @DisplayName("Should delete element by index")
    void shouldDeleteElementByIndex() {
      assertEquals(Integer.valueOf(60), list.remove(1));
      assertEquals(3, list.size());
      assertEquals(Integer.valueOf(70), list.get(1));
    }

    @Test
    @DisplayName("Should throw exception for invalid index deletion")
    void shouldThrowExceptionForInvalidIndexDeletion() {
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
    }
  }
}
