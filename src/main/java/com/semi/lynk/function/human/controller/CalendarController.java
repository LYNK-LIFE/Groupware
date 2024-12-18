package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/*")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController (CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("calendar")
    @ResponseBody
    public List<CalendarDTO> calendarList () {
        List<CalendarDTO> result = calendarService.calendarService();
        Map<String, Object> map = new HashMap<>();
        map.put("cal" , result);
        return result;
    }

}
