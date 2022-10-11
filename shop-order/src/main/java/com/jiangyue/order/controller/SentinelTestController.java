package com.jiangyue.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyongkang
 * @date Create in 2022/9/26 14:23
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelTestController {

    @SentinelResource(value = "message1", blockHandler = "blockMessage1", fallback = "fallMessage1")
    @GetMapping("message1")
    public String message1() throws InterruptedException {
        Thread.sleep(100);

        int nextInt = RandomUtils.nextInt();
        if(nextInt % 2 == 0){
            int a  = 1/0;
        }
        return "message1";
    }

    public String blockMessage1() throws InterruptedException {
        return "该接口被降级了";
    }
    public String fallMessage1() throws InterruptedException {
        return "该接口出异常了";
    }

    @SentinelResource("message2")
    @GetMapping("message2")
    public String message2(){
        return "message2";
    }

}
