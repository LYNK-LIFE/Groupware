package com.semi.lynk.function.human.controller;

import com.semi.lynk.function.human.model.calendar.CalendarDTO;
import com.semi.lynk.function.human.model.dto.*;
import com.semi.lynk.function.human.service.CalendarService;
import com.semi.lynk.function.human.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/employee/*")
public class EmployeeController {

    private EmployeeService employeeService;
    private CalendarService calendarService;

    private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private final MessageSource messageSource;

    @Autowired
    public EmployeeController (EmployeeService employeeService
                               , CalendarService calendarService
                                , MessageSource messageSource) {
        this.employeeService = employeeService;
        this.calendarService = calendarService;
        this.messageSource = messageSource;
    }


    @GetMapping("lookup")
    public String employeeList (Model model) {

        List<EmployeeDTO> list = employeeService.employeeList();

        for (EmployeeDTO emp : list) {
            System.out.println(emp);
        }
        model.addAttribute("list", list);

        return "function/human/attendance";
    }

    @GetMapping (value = "lookUpList" , produces = "application/json; charset=UTF-8")
    @ResponseBody // 인사 조회 시 불려올 데이터
    public List<LookUpDTO> lookUpList () {

        List<LookUpDTO> result = employeeService.lookupData();
//        logger.info("인사 조회 시 불려올 데이터 :" + result);

        return result;
    }


    @GetMapping ("list")
    public String look () {
        return "function/human/attendance";
    }

    @GetMapping ("inform")
    public String humanInform () {
        return "function/human/lookup";
    }

//    @PostMapping("modify") // 인사 수정 메서드 (update)
//    public String modifyMethod (@ModelAttribute ModifyDTO modifyDTO
//                                , RedirectAttributes rtt, Locale locale) {
//        int result = employeeService.modifyService(modifyDTO);
//
//        if (result == 1){
//            rtt.addFlashAttribute("modifyMessage"
//                    ,messageSource.getMessage("modifySuccess",new Object[]{modifyDTO.getName()} , locale));
//            return "redirect:/employee/inform";
//        } else {
//            return "function/human/lookup";
//        }
//    }

    @PostMapping("modify")
    @ResponseBody // JSON 응답으로 변환
    public Map<String, Object> modifyMethod(@RequestBody ModifyDTO modifyDTO) {
        System.out.println("수신된 DTO: " + modifyDTO);
        int result = employeeService.modifyService(modifyDTO);
        Map<String, Object> response = new HashMap<>();

        if (result == 1) {
            response.put("status", "success");
            response.put("message", "직원이 성공적으로 수정되었습니다.");
        } else {
            response.put("status", "error");
            response.put("message", "직원 수정에 실패했습니다.");
        }
        return response;
    }


    // 인사 등록 창에 인사 등록 안 된 애들 조회해주는 거
    @GetMapping("join")
    public ModelAndView joinList (ModelAndView mv) {

        List<EmpAndDepDTO> list = employeeService.joinList();
//        for (EmpAndDepDTO emp : list) {
//            System.out.println(emp);
//        }

        mv.addObject("list" , list);
        mv.setViewName("function/human/registPage");
        return mv;
    }

    @GetMapping("regist")
    public String moveRegistPage () {
        return "forward:/employee/join";
    } // forward를 이용해서 join에 처리 위임
    // forward 안 쓰고 function/human/registPage 그대로 쓰면 값이 안 담겨 있고,
    // 값 담으려면 중복되는 값을 또 넣어줘야 하지만 forward로 끝.


    @PostMapping ("regist") // 사용자가 form태그의 등록 눌렀을 때 동작
    public String humanRegist (@ModelAttribute RegistHumDTO registHumDTO
                               ,EmpAndDepDTO empAndDepDTO
                               ,RedirectAttributes rtt
                                ,Locale locale) {

        System.out.println("Human DTO: " + registHumDTO);
        int result = employeeService.humanRegist(registHumDTO);

        if (result == 1) {
            rtt.addFlashAttribute("successMessage"
                    ,messageSource.getMessage("registSuccess",new Object[]{empAndDepDTO.getName()} , locale));
            return "redirect:/employee/regist";
            // redirect는 url이 동작 , view 페이지 입력이 아니라, 핸들러 메서드를 지정해야 함!!
            // 클릭 시  @GetMapping("regist")
            //    public String moveRegistPage () {
            //        return "function/human/registPage";
            //    }가 동작하며 registPage로 옴!
        } else {
            return "redirect:/employee/main";
        }
    }

    @GetMapping ("attendance")
    public String attendanceMethod () {
        return "function/human/attendance";
    }

    @GetMapping ("appStatus") // 페이지 반환하는 애
    public String appStatusPage () {
        return "function/human/myApplicationStatus";
    }

    // json이 default이므로 produces 안 써도 되지만, 가독성을 위해 쓰는 게 좋음
    @GetMapping(value = "appStatusList", produces = "application/json; charset=UTF-8")
    @ResponseBody // fetch 보내는 애
    public List<CalendarDTO> appStatusList () {
        List<CalendarDTO> appStatus = calendarService.myAppStatusService();
        return appStatus;
    }

}
