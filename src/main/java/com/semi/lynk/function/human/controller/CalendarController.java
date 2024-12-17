package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.dto.CalendarDTO;
import com.semi.lynk.function.human.model.event.Event;
import com.semi.lynk.function.human.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/employee/*")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController (CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    @ResponseBody
    public List<Event> calendarList (@RequestBody CalendarDTO calendarDTO) {
        List<Event> result = calendarService.calendarService(calendarDTO);
        return result;
    }

}
