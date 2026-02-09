package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final AtomicLong seq = new AtomicLong();
    private final Map<Long, Item> store = new ConcurrentHashMap<>();

    @PostMapping
    public Item create(@RequestBody Item item) {
        long id = seq.incrementAndGet();
        Item saved = new Item(id, item.getName());
        store.put(id, saved);
        return saved;
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return store.get(id);
    }

    public static class Item {
        private Long id;
        private String name;

        public Item() {}
        public Item(Long id, String name) { this.id = id; this.name = name; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}
