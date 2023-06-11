package com.example.fetch;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataModelTest {
    DataModel testOne = new DataModel("Test 1", 1, 1);
    DataModel testTwo = new DataModel("Test 2", 0, 2);

    @Test
    public void DataModel(){
        assertEquals("Test 1", testOne.name);
        assertEquals("Test 2", testTwo.name);
        assertEquals(1, testOne.listId);
        assertEquals(0, testTwo.listId);
        assertEquals(1, testOne.id);
        assertEquals(2, testTwo.id);

    }

    @Test
    public void getName() {
        assertEquals("Test 1", testOne.getName());
        assertEquals("Test 2", testTwo.getName());
    }

    @Test
    public void getListId() {
        assertEquals(1, testOne.getId());
        assertEquals(2, testTwo.getId());
    }

    @Test
    public void getId() {
        assertEquals(1, testOne.getListId());
        assertEquals(0, testTwo.getListId());
    }
}