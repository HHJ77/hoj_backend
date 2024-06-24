package com.yupi.hoj.judge.codesanbox.impl;

import com.yupi.hoj.judge.codesanbox.CodeSandBox;
import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeRequest;
import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeResponse;
import com.yupi.hoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.hoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.hoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 代码沙箱示例
 */
public class ExampleCodeSandbox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> inputList = executeCodeRequest.getInputList();

        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;

    }
}