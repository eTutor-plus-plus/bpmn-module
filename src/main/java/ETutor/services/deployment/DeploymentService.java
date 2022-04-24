package ETutor.services.deployment;

import ETutor.services.rules.user_Task.EngineTaskService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class DeploymentService {
    private static final Logger logger = LoggerFactory.getLogger(EngineTaskService.class);

    public DeploymentService() {
        logger.info(logger.getName() + "- is started");
    }

    public String deployNewBpmn() throws IOException {
        Response response;
        try {
            File file = new File("doc/bpmnTestDeploy/TestBpmn.bpmn");
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
//            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload", "TestBpmn.bpmn",
                            RequestBody.create(file, MediaType.parse("application/octet-stream")))
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/engine-rest/deployment/create")
                    .method("POST", body)
                    .addHeader("Content-Type", "multipart/form-data")
                    .build();
            response = client.newCall(request).execute();
            if (response.body() != null) {
                logger.info("---Deployed!---");
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Deployment failed";
    }
}
