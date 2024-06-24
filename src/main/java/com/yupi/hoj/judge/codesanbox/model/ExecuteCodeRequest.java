package com.yupi.hoj.judge.codesanbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 执行代码请求
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeRequest {

    //输入用例
    private List<String> inputList;

    //编程语言
    private String language;

    //用户代码
    private String code;


}