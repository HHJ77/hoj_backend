package com.yupi.hoj.judge;

import com.yupi.hoj.model.entity.QuestionSubmit;
import com.yupi.hoj.model.vo.QuestionSubmitVO;
/**
 * 判题服务
 */
public interface JudgeService {


    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    public QuestionSubmit judge(long questionSubmitId);
}
