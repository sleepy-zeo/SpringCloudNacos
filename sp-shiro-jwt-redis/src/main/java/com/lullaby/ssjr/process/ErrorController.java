package com.lullaby.ssjr.process;

import com.lullaby.ssjr.common.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    @ResponseBody
    public ResponseResult error404() {
        return new ResponseResult(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), null);
    }

    @RequestMapping("/405")
    @ResponseBody
    public ResponseResult error405() {
        return new ResponseResult(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), null);
    }

    @RequestMapping("/500")
    @ResponseBody
    public ResponseResult error500() {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    @RequestMapping("/illegal")
    @ResponseBody
    public ResponseResult expIllegalArgument() {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }

    @RequestMapping("/custom")
    @ResponseBody
    public ResponseResult custom() {
        return new ResponseResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), null);
    }
}
