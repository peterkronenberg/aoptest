package com.torchai.service.skeleton.controller;


import com.torchai.service.common.cloud.api.BaseController;
import com.torchai.service.common.cloud.api.CloudConfig;
import com.torchai.service.common.response.ResponseBody;
import com.torchai.service.common.response.ResponseBodyPayload;
import com.torchai.service.common.response.ServiceResponse;
import com.torchai.service.common.util.PublicApi;
import com.torchai.service.skeleton.contract.SkeletonBucketContract;
import com.torchai.service.skeleton.contract.SkeletonFileContract;
import com.torchai.service.skeleton.service.SkeletonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller for Skeleton service
 */
@Api(tags = "Nexus API - Skeleton service")
@RestController
@RequestMapping("${nexus.base.url}")
@Slf4j

public class SkeletonController extends BaseController {

    private final SkeletonService skeletonService;

    public SkeletonController(SkeletonService skeletonService) {
        this.skeletonService = skeletonService;
    }

    @PublicApi
    @ApiOperation(value = "Display help about all the options")
    @GetMapping("/help")
    public ResponseEntity<?> help() {
        final StopWatch watch = new StopWatch();
        watch.start();

        ServiceResponse response = skeletonService.getHelp();

        if (response.isSuccess()) {
            // If it worked, then just return the HTML
            return new ResponseEntity<>((String) response.get("help"), HttpStatus.OK);
        } else {
            // If it failed, return the ViewModel
            return response.getResponse(null, "Error retrieving help.", new ResponseBodyPayload(), watch);
        }
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/sample")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> getSomething(@Valid @RequestBody @NonNull SkeletonFileContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        final ServiceResponse response = skeletonService.readFile(contract.getCloudFile(), cloudConfig);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/list")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> listFiles(@Valid @RequestBody @NonNull SkeletonBucketContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        final ServiceResponse response = skeletonService.listFiles(contract.getCloudBucket(), cloudConfig);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/folders")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> listFolders(@Valid @RequestBody @NonNull SkeletonBucketContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        final ServiceResponse response = skeletonService.listFolders(contract.getCloudBucket(), cloudConfig);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/upload")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> upload(@Valid @RequestBody @NonNull SkeletonFileContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        final ServiceResponse response = skeletonService.upload(contract.getCloudFile(), cloudConfig);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/delete")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> delete(@Valid @RequestBody @NonNull SkeletonFileContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        final ServiceResponse response = skeletonService.delete(contract.getCloudFile(), cloudConfig);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

    @PublicApi
    @ApiOperation(value = "Description of endpoint")
    @PostMapping("/read")
    public ResponseEntity<ResponseBody<SkeletonResponseBodyPayload>> readFile(@Valid @RequestBody @NonNull SkeletonFileContract contract) {
        final StopWatch watch = new StopWatch();
        watch.start();
        final CloudConfig cloudConfig = contract.getCloudConfig();

        ServiceResponse response = new ServiceResponse();
        log.info("*** contract: " + contract);
        final String file = skeletonService.myMethod(contract.getCloudFile(), cloudConfig, "foo");
//        skeletonService.dummy(file);
        response.setSuccess(true);

        return response.getResponse("Operation successful.", "Operation failed.", new SkeletonResponseBodyPayload(), watch);
    }

}
