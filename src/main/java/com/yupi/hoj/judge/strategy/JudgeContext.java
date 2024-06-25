package com.yupi.hoj.judge.strategy;

import com.yupi.hoj.model.dto.question.JudgeCase;
import com.yupi.hoj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.hoj.model.entity.Question;
import com.yupi.hoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 判题信息
 */

@Data
public class JudgeContext {

    /**
     * 输入案例
     */
    List<String> inputList;

    /**
     * 期望输出
     */
    List<String> outputList;

    /**
     * 判断信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 判断案例
     */
    private List<JudgeCase> judgeCases;

    /**
     * 问题
     */
    private Question question;

    /**
     * 提交题目记录
     */
    private QuestionSubmit questionSubmit;

}