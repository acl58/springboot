package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lineplay on 2016-12-20.
 */
@Controller
@EnableAutoConfiguration
public class SampleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    @ResponseBody
    String test_get() {

        logger.debug("!!!!!!!!! TEST !!!!!!!!!!");

        CloverRequest cloverRequest = new CloverRequest();

        if(Math.random() != 1){
            cloverRequest = null;
        }

//        logger.debug(cloverRequest.getCode

        return "Hello Test!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test")
    @ResponseBody
    CloverResult test_post(@RequestBody CloverRequest cloverRequest) {

        logger.debug("cloverRequest data start");

        logger.debug("getPaidBalanceTotal :" + cloverRequest.getPaidBalanceTotal());
        logger.debug("getPaidChargeTotal :" + cloverRequest.getPaidChargeTotal());
        logger.debug("getPaidUseTotal :" + cloverRequest.getPaidUseTotal());
        logger.debug("getCode :" + cloverRequest.getCode());
        logger.debug("getDateOfMonth :" + cloverRequest.getDateOfMonth());

        logger.debug("cloverRequest data end");

        return new CloverResult("fail", "fail fail");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleController.class, args);
    }
}
