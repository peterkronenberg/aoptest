package com.torchai.service.skeleton.contract;

import com.torchai.service.common.cloud.api.CloudConfig;
import com.torchai.service.common.cloud.api.CloudFile;
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
public class SkeletonFileContract {

    @Valid
    private CloudFile cloudFile;

    @Valid
    private CloudConfig cloudConfig;
}
