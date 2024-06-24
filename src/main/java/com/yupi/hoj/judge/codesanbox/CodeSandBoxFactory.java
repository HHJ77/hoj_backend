package com.yupi.hoj.judge.codesanbox;


import com.yupi.hoj.judge.codesanbox.impl.ExampleCodeSandbox;
import com.yupi.hoj.judge.codesanbox.impl.RemoteCodeSandbox;
import com.yupi.hoj.judge.codesanbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂
 */
public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "example":
                return new ExampleCodeSandbox();
            default:
                return null;
        }
    }
}