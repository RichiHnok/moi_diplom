package com.richi.web_part.dto.personalCabinet;

import com.richi.common.enums.TaskSampleParamType;

public record TaskParamInfoDto(
    TaskSampleParamType paramType
    , String paramName
    , String paramValue
) {
    
}
