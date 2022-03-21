package com.torchai.service.skeleton.service;

import com.torchai.service.aspect.annotations.AspectLog;
import com.torchai.service.aspect.annotations.AspectNoLog;
import com.torchai.service.common.cloud.api.AbstractCloudService;
import com.torchai.service.common.cloud.api.CloudBucket;
import com.torchai.service.common.cloud.api.CloudConfig;
import com.torchai.service.common.cloud.api.CloudFile;
import com.torchai.service.common.cloud.api.CloudProvider;
import com.torchai.service.common.cloud.api.CloudProviderException;
import com.torchai.service.common.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/**
 * Service to blah blah blah
 */
@Service
@Slf4j
public class SkeletonService extends AbstractCloudService {

    public ServiceResponse getHelp() {
        final ServiceResponse response = new ServiceResponse();
        try (final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("help.md")) {
            if (resourceAsStream == null) {
                response.addMessage("Help file not found");
            } else {
                try (final Reader reader = new InputStreamReader(resourceAsStream)) {
                    final Node document = Parser.builder().build().parseReader(reader);
                    response.put("help", HtmlRenderer.builder().build().render(document));
                    response.setSuccess(true);
                } catch (final IOException e) {
                    log.error("Error reading help.md - {}", e.getMessage(), e);
                    response.addMessage("Error reading help.md - %s", e.getMessage());
                }
            }
        } catch (IOException e) {
            response.addMessage("Error reading Help file");
        }
        return response;
    }

    public ServiceResponse readFile(CloudFile cloudFile, CloudConfig cloudConfig) {
        ServiceResponse response = new ServiceResponse();
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudFile.getProvider());
        try (InputStream is = cloudProvider.getFileFromCloudAsInputStream(cloudFile, cloudConfig);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            response.setSuccess(true);
        } catch (IOException | CloudProviderException e) {
            e.printStackTrace();
            String msg = "Error processing file - " + e.getMessage();
            log.error(msg, e);
            response.addMessage(msg);
        }

        return response;
    }

    public ServiceResponse listFiles(CloudBucket cloudBucket, CloudConfig cloudConfig) {
        ServiceResponse response = new ServiceResponse();
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudBucket.getProvider());
        try {
            List<CloudFile> list = cloudProvider.listFiles(cloudBucket, cloudConfig, true);
            response.put("list", list);
            response.setSuccess(true);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            String msg = "Error processing file - " + e.getMessage();
            log.error(msg, e);
            response.addMessage(msg);
        }

        return response;
    }


    public ServiceResponse listFolders(CloudBucket cloudBucket, CloudConfig cloudConfig) {
        ServiceResponse response = new ServiceResponse();
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudBucket.getProvider());
        try {
            List<CloudBucket> list = cloudProvider.listFolders(cloudBucket, cloudConfig, true);
            response.put("list", list);
            response.setSuccess(true);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            String msg = "Error processing file - " + e.getMessage();
            log.error(msg, e);
            response.addMessage(msg);
        }

        return response;
    }


    public ServiceResponse upload(CloudFile cloudFile, CloudConfig cloudConfig) {
        ServiceResponse response = new ServiceResponse();
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudFile.getProvider());
        try {
            String initialString = "Upload file to Azure";
            InputStream is = new ByteArrayInputStream(initialString.getBytes());
            cloudProvider.upload(cloudFile, is, cloudConfig);
            response.setSuccess(true);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            String msg = "Error processing file - " + e.getMessage();
            log.error(msg, e);
            response.addMessage(msg);
        }

        return response;
    }

    public ServiceResponse delete(CloudFile cloudFile, CloudConfig cloudConfig) {
        ServiceResponse response = new ServiceResponse();
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudFile.getProvider());
        try {


            System.out.println("*** deleting: " + cloudFile);
            cloudProvider.delete(cloudFile, cloudConfig);
            response.setSuccess(true);
        } catch (CloudProviderException e) {
            e.printStackTrace();
            String msg = "Error processing file - " + e.getMessage();
            log.error(msg, e);
            response.addMessage(msg);
        }

        return response;
    }


    public String myMethod(CloudFile cloudFile, @AspectNoLog CloudConfig cloudConfig, String someString) {
        CloudProvider cloudProvider = cloudProviderFactory.getProvider(cloudFile.getProvider());

        StringBuilder sb = new StringBuilder();
        try (InputStream is = cloudProvider.getFileFromCloudAsInputStream(cloudFile, cloudConfig);
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {
//            CloudFile fileInfo = cloudProvider.fillMetaInfo(cloudFile, cloudConfig);

            String line;
            while ((line = bis.readLine()) != null) {
                sb.append(line + System.lineSeparator());
            }

        } catch (CloudProviderException | IOException e) {
            e.printStackTrace();
        }
        dummy(sb.toString());
        return sb.toString();
    }

    @AspectLog
    private void dummy(String str) {

    }
}
