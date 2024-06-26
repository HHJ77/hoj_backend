package com.yupi.hoj.judge.codesanbox;


import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeRequest;
import com.yupi.hoj.judge.codesanbox.model.ExecuteCodeResponse;

/**
 * 定义代码沙箱接口
 *
 */
public interface CodeSandBox {


    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return ExecuteCodeResponse
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);

}
