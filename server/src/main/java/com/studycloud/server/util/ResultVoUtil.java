package com.studycloud.server.util;


import com.studycloud.server.enums.ResultEnum;
import com.studycloud.server.result.ResultVo;

public class ResultVoUtil {

    public static ResultVo success(Object object){
        ResultVo resultVo = new ResultVo();
        resultVo.setData(object);
        resultVo.setCode(100);
        resultVo.setMsg("success!");
        return  resultVo;
    }
    public static ResultVo success(){
        ResultVo resultVo = new ResultVo();
        resultVo.setCode(100);
        resultVo.setMsg("success!");
        return  resultVo;
    }

    public static ResultVo error(ResultEnum loginFail) {
        ResultVo resultVo = new ResultVo();
        resultVo.setData(null);
        resultVo.setCode(loginFail.getCode());
        resultVo.setMsg(loginFail.getMessage());
        return  resultVo;
    }
}
