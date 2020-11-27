package tourGuide.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ExternalApiService {
    /**
     * Logger log4j2
     */
    private static final Logger logger = LogManager.getLogger("ExternalApiService");

    /**
     * HttpRequestService
     */
    public HTTPRequestService httpRequestService;

    /**
     * Configuration File
     */
    private String configurationFilePath;

    public ExternalApiService(HTTPRequestService httpRequestService, String configurationFilePath) {
        this.httpRequestService = httpRequestService;
        this.configurationFilePath = configurationFilePath;
    }

    public String getApiServerUrl(String param){
        String serverUrl = "";
        try (InputStream inputStream = new FileInputStream(configurationFilePath)){
            Properties properties = new Properties();
            properties.load(inputStream);
            serverUrl = properties.getProperty(param);
        } catch (FileNotFoundException e) {
            logger.error("File not found", e);
        } catch (IOException e) {
            logger.error("Error while read file", e);
        }
        return serverUrl;
    }
}
