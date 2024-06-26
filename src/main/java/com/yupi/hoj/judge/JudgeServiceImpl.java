package com.yupi.hoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.hoj.common.ErrorCode;
import com.yupi.hoj.exception.BusinessException;
import com.yupi.hoj.judge.codesanbox.CodeSandBox;
import com.yupi.hoj.judge.codesanbox.CodeSandBoxFactory;
import com.yupi.hoj.judge.codesanbox.CodeSandBoxProxy;
import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeRequest;
import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeResponse;
import com.yupi.hoj.judge.strategy.JudgeContext;
import com.yupi.hoj.model.dto.question.JudgeCase;
import com.yupi.hoj.judge.codesanbox.model.JudgeInfo;
import com.yupi.hoj.model.entity.Question;
import com.yupi.hoj.model.entity.QuestionSubmit;
import com.yupi.hoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.hoj.service.QuestionService;
import com.yupi.hoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JudgeServiceImpl implements JudgeService {


    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManger judgeManger;

    @Value("${codesandbox.type}")
    private String type;

    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit judge(long questionSubmitId) {

        //1.获取信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交记录不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        //2.判题状态
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目正在判题中");

        }
        QuestionSubmit questionSubmit1 = new QuestionSubmit();
        questionSubmit1.setId(questionSubmitId);
        questionSubmit1.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean b = questionSubmitService.updateById(questionSubmit1);
        if (!b) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "更新状态失败");
        }

        //3.调用代码沙箱执行代码
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);

        //准备判题数据
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCase, JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        //封装请求
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest
                .builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);

        //收集判题信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCases(judgeCases);
        judgeContext.setQuestionSubmit(questionSubmit);

        //对比答案
        JudgeInfo judgeInfo = judgeManger.doJudge(judgeContext);
        //更新状态
        questionSubmit1 = new QuestionSubmit();
        questionSubmit1.setId(questionSubmitId);
        questionSubmit1.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmit1.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean b1 = questionSubmitService.updateById(questionSubmit1);
        if (!b1) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "更新状态失败");
        }

        QuestionSubmit byId = questionSubmitService.getById(questionSubmitId);

        return byId;
    }
}