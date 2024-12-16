package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.event.Event;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/*")
public class CalendarController {

    private final List<Event> eventList = new ArrayList<>();

    // 모든 이벤트 반환 (GET)
    @GetMapping
    public List<Event> getEvents() {
        return eventList;
    }

    // 새로운 이벤트 추가 (POST)
    @PostMapping ("events")
    public Event addEvent(@RequestBody Event event) {
        event.setId(UUID.randomUUID().toString()); // UUID로 ID 설정
        eventList.add(event);
        return event;
    }
}
