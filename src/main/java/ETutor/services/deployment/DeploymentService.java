package ETutor.services.deployment;

import ETutor.config.ApplicationProperties;
import ETutor.services.rules.tasks.EngineTaskService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
public class DeploymentService {
    private static final Logger logger = LoggerFactory.getLogger(EngineTaskService.class);
    private final ApplicationProperties applicationproperties;

    public DeploymentService(ApplicationProperties applicationProperties) {
        this.applicationproperties = applicationProperties;
        logger.info(logger.getName() + "- is started");
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
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
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
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
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
                JSONObject obj = new JSONObject(response.body().string());
                String id = obj.getString("id");
                logger.info("ID:+++++++" + id);
//                JSONObject processDefinition = obj.getJSONObject("deployedProcessDefinitions");
//                logger.info(processDefinition.toString());
//                String processKey = processDefinition.names().getJSONArray(0).getString(2);
//                logger.info("key:+++++++" + processKey);
                return obj.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null)
                response.close();
        }
        return "Deployment failed";
    }
}
