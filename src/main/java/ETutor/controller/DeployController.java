package ETutor.controller;

import ETutor.services.deployment.DeploymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
            if (result.contains("ParseException")) {
                return result;
            } else {
                JSONObject obj = new JSONObject(result);
                return obj.toString();
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return "Deployment failed";
    }

    @DeleteMapping("")
    public boolean destroyBPMN(@RequestParam String definitionID) {
        try {
            return deploymentService.deleteProcess(definitionID);
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return false;
    }
}
