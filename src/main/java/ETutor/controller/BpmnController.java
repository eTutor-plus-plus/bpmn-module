package ETutor.controller;

import ETutor.Application;
import ETutor.services.BpmnService;
import ETutor.services.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/bpmn")
public class BpmnController {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private final BpmnService bpmnService;
//    private final TestService testService;

    public BpmnController(BpmnService service, TestService testService) {
        this.bpmnService = service;
//        this.testService = testService;
    }

//    @GetMapping("test")
//    public String testRuntime() {
//        return testService.validate();
//    }

    @GetMapping("")
    public boolean testfnc() {
        return bpmnService.validate();
    }

}
