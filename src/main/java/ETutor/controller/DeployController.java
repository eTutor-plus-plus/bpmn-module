package ETutor.controller;

import ETutor.services.deployment.DeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/deployment")
public class DeployController {
    private static final Logger logger = LoggerFactory.getLogger(DeployController.class);
    private final DeploymentService deploymentService;

    public DeployController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @PostMapping("/static")
    public String deployBPMN() {
        try {
            return deploymentService.deployNewBpmn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Deployment failed";
    }

    @PostMapping(path = "")
    public String deployBPMN(@RequestBody String bpmnXml) {
        try {
            String result = deploymentService.deployNewBpmnWithString(bpmnXml);
            if (!result.contains("ParseException"))
                logger.info("---Deployed!---");
            return result;
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return "Deployment failed";
    }

//    @DeleteMapping("")
//    public String destroyBPMN(@RequestParam String id) {
//        try {
//
//            return result;
//        } catch (IOException e) {
//            logger.warn(e.getMessage());
//        }
//        return "Deployment failed";
//    }
}
