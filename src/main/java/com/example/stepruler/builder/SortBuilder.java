package com.example.stepruler.builder;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.List;

public class SortBuilder {
    /**
     * 调用的时候使用SortBuilder.generateSort("name","xh/d");表示先以name升序，之后以xh降序
     * 无下划线表示升序，/d表示降序
     */
    public static Sort generateSort(String... fields) {
        List<Order> orders = new ArrayList<Order>();
        for(String f:fields) {
            orders.add(generateOrder(f));
        }
        return new Sort(orders);
    }

    private static Order generateOrder(String f) {
        Order order = null;
        String[] ff = f.split("/");
        if(ff.length>=2) {
            if(ff[1].equals("d")) {
                order = new Order(Direction.DESC, ff[0]);
            } else {
                order = new Order(Direction.ASC, ff[0]);
            }
            return order;
        }
        order = new Order(f);
        return order;
    }
}
