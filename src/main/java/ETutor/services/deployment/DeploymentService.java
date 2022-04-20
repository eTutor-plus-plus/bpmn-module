package ETutor.services.deployment;

import ETutor.services.user_Task.NameService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class DeploymentService {
    private static final Logger logger = LoggerFactory.getLogger(NameService.class);

    public DeploymentService() {
        logger.info(logger.getName() + "- is started");
    }

    public boolean deployNewBpmn() {
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload", "/C:/Users/Dominik/Postman Agent/files/Teacher2.bpmn",
                            RequestBody.create(new File("doc/bpmnTestDeploy/test_invalid.bpmn"), MediaType.parse("application/octet-stream")))
                    .build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/engine-rest/deployment/create")
                    .method("POST", body)
                    .addHeader("Content-Type", "multipart/form-data")
                    .build();
            response = client.newCall(request).execute();
            if (response.body() != null)
                logger.info("---Deployed!---" + Objects.requireNonNull(response.body()).string());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null) return response.isSuccessful();
        return false;
    }
}
