package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Code {
    CODE_200("200"), // 成功

    CODE_401("401"), // 权限不足
    CODE_400("400"), // 参数错误
    CODE_500("500"), // 系统错误
    CODE_600("600"), // 其它业务错误

    DICT_TYPE_ICON("icon")

    ;//这些值都是枚举类型

    private String code;

}
