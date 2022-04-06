package ETutor.controller;

import ETutor.Application;
import ETutor.services.BpmnService;
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

    public BpmnController(BpmnService service) {
        this.bpmnService = service;
    }

    @GetMapping("")
    public String testRuntime() {
        return bpmnService.validate();
    }

}
