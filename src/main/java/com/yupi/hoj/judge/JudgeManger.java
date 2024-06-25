package com.yupi.hoj.judge;

import com.yupi.hoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.hoj.judge.strategy.JavaJudgeStrategy;
import com.yupi.hoj.judge.strategy.JudgeContext;
import com.yupi.hoj.judge.strategy.JudgeStrategy;
import com.yupi.hoj.model.dto.questionsubmit.JudgeInfo;
import org.springframework.stereotype.Service;

@Service
public class JudgeManger {

    JudgeInfo doJudge(JudgeContext judgeContext){
        String language = judgeContext.getQuestionSubmit().getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();

        if ("java".equals(language)){
            // JavaJudgeStrategy javaJudgeStrategy = new JavaJudgeStrategy();
            // return javaJudgeStrategy.doJudge(judgeContext);
            judgeStrategy = new JavaJudgeStrategy();
        }

        return judgeStrategy.doJudge(judgeContext);

    }
}