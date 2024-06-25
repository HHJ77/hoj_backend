package com.yupi.hoj.judge.strategy;

import com.yupi.hoj.model.dto.questionsubmit.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);

}