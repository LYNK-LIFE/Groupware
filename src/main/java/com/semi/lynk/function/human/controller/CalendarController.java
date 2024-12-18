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
    public List<CalendarDTO> calendarList() {
        return calendarService.calendarService();
    }

//    @GetMapping("calendar")
//    @ResponseBody
//    public List<CalendarDTO> calendarList () {
//        List<CalendarDTO> result = calendarService.calendarService();
//        Map<String, Object> map = new HashMap<>();
//        map.put("cal" , result);
//        return result;
//        // key를 cal로 전달하는데 js에서 cal 없이 값 꺼낼 수 있는 이유!
//        /* @ResponseBody는 Java의 객체 데이터를 HTTP 응답으로 변환해
//        클라이언트에 JSON 형태로 반환.
//        컨트롤러 코드에서 @ResponseBody가 설정된 List<CalendarDTO>는
//        JSON으로 직렬화(Serialize)되면서 구조가 바뀐다!
//        그러나 반환값의 최상위 구조는 List<CalendarDTO>가 되고,
//        map.put("cal", result);의 내용은 최종적으로 무시.
//        즉, map 객체를 사용하고 있지만, 실제 리턴 값이 result에 덮어씌워진 상태로 처리*/
//    }

}
