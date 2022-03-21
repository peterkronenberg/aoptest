package com.torchai.service.skeleton.contract;

import com.torchai.service.common.cloud.api.CloudBucket;
import com.torchai.service.common.cloud.api.CloudConfig;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "methodName", description = "blah, blah, blah")
public class SkeletonBucketContract {

    @Valid
    private CloudBucket cloudBucket;
    
    @Valid
    private CloudConfig cloudConfig;
}
