package ETutor.controller;

import ETutor.services.deployment.DeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("")
    public String deployBPMN() {
        try {
            return deploymentService.deployNewBpmn();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Deployment failed";
    }
}
