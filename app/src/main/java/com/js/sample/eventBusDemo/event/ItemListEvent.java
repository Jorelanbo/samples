package com.js.sample.eventBusDemo.event;

// Created by JS on 2020/6/8.

import com.js.sample.eventBusDemo.bean.Item;

import java.util.List;

public class ItemListEvent {

    private List<Item> items;

    public ItemListEvent(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
