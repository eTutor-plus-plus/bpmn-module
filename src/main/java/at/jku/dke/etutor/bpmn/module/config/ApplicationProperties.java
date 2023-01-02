package at.jku.dke.etutor.bpmn.module.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Used to retrieve the application properties
 */
@ConfigurationProperties(value = "properties")
public class ApplicationProperties {
    private Deployment deployment = new Deployment();
    private JsonReader jsonReader = new JsonReader();

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public JsonReader getJsonReader() {
        return jsonReader;
    }

    public void setJsonReader(JsonReader jsonReader) {
        this.jsonReader = jsonReader;
    }

    public static class Deployment {
        public boolean runtimeStarted;

        public String uploadURL;

        public String deployName;

        public boolean isRuntimeStarted() {
            return runtimeStarted;
        }

        public void setRuntimeStarted(boolean runtimeStarted) {
            this.runtimeStarted = runtimeStarted;
        }

        public String getUploadURL() {
            return uploadURL;
        }

        public void setUploadURL(String uploadURL) {
            this.uploadURL = uploadURL;
        }

        public String getDeployName() {
            return deployName;
        }

        public void setDeployName(String deployName) {
            this.deployName = deployName;
        }
    }

    public static class JsonReader {
        private String JsonTestPath;

        public String getJsonTestPath() {
            return JsonTestPath;
        }

        public void setJsonTestPath(String jsonTestPath) {
            JsonTestPath = jsonTestPath;
        }
    }
}
