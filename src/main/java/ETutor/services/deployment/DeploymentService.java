package ETutor.services.deployment;

import ETutor.config.ApplicationProperties;
import ETutor.services.rules.tasks.EngineTaskService;
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
    private final ApplicationProperties applicationproperties;
    private final OkHttpClient client;

    public DeploymentService(ApplicationProperties applicationProperties) {
        this.applicationproperties = applicationProperties;
        logger.info(logger.getName() + "- is started");
        client = new OkHttpClient().newBuilder().build();
    }

    public String deployNewBpmn() throws IOException {
        Response response = null;
        File file = null;
        try {
            file = new File("doc/bpmnTestDeploy/TestBpmn.bpmn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file != null) {
//            MediaType mediaType = MediaType.parse("multipart/form-data");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload", applicationproperties.getDeployment().getDeployName(),
                            RequestBody.create(file, MediaType.parse("application/octet-stream")))
                    .build();
            Request request = new Request.Builder()
                    .url(applicationproperties.getDeployment().getUploadURL())
                    .method("POST", body)
                    .addHeader("Content-Type", "multipart/form-data")
                    .build();
            try {
                response = client.newCall(request).execute();
                if (response.body() != null) {
                    logger.info("---Deployed!---");
                    return Objects.requireNonNull(response.body()).string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (response != null)
                    response.close();
            }
        }
        return "Deployment failed";
    }

    public String deployNewBpmnWithString(String xml) throws IOException {
        logger.info("start Deployment");
        Response response = null;
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("upload", applicationproperties.getDeployment().getDeployName(),
                        RequestBody.create(xml, MediaType.parse("application/octet-stream")))
                .build();
        Request request = new Request.Builder()
                .url(applicationproperties.getDeployment().getUploadURL())
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        try {
            response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } finally {
            if (response != null)
                response.close();
        }
        return "Deployment failed";
    }

    public boolean deleteProcess(String definitionID) throws IOException {
        if (definitionID == null || definitionID.isBlank()) throw new IOException("no definitionID");
        logger.info("start delete Deployment with definitionID: " + definitionID);

        Response response = null;
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create("{}", JSON);
        Request request = new Request.Builder()
                .url("http://localhost:8080/engine-rest/process-definition/" + definitionID)
                .method("DELETE", body)
                .build();
        try {
            response = client.newCall(request).execute();
            logger.info("end delete Deployment");
            return response.code() == 200;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null)
                response.close();
        }
    }
}
