package com.yupi.hoj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yupi.hoj.model.dto.question.JudgeCase;
import com.yupi.hoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.hoj.model.entity.Question;
import com.yupi.hoj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

public class JavaJudgeStrategy implements JudgeStrategy{
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {


        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCases = judgeContext.getJudgeCases();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        JudgeInfo judgeInfo2 = new JudgeInfo();
        judgeInfo2.setMessage(judgeInfoMessageEnum.getText());
        judgeInfo2.setMemory(memory);
        judgeInfo2.setTime(time);


        //(1):先判断输出用例和输入用例数量是否一致
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfo2.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfo2;
        }
        //(2):再判断每个输出用例和输入用例是否一致
        for (int i = 0; i < outputList.size(); i++) {
            if (!outputList.get(i).equals(judgeCases.get(i).getOutput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfo2.setMessage(judgeInfoMessageEnum.getText());
                return judgeInfo2;
            }
        }
        //(3)：判断题目限制

        String judgeConfig = question.getJudgeConfig();
        JudgeInfo judgeInfo1 = JSONUtil.toBean(judgeConfig, JudgeInfo.class);
        Long memory1 = judgeInfo1.getMemory();
        Long time1 = judgeInfo1.getTime();
        if (memory > memory1) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo2.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfo2;

        }
        if (time > time1) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo2.setMessage(judgeInfoMessageEnum.getText());
            return judgeInfo2;
        }

        return judgeInfo2;
    }
}