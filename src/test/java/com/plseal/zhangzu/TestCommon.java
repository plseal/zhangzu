package com.plseal.zhangzu;

import java.time.Duration;

// import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCommon {
    
    private static final Logger logger = LoggerFactory.getLogger(TestCommon.class);
    
    Integer waitTime = 2;

    public  void common_login_admin(WebDriver mydriver) throws Exception {
        
        mydriver.get("http://localhost:8080/index");
        waitSomeSeconds(mydriver);
  

    }
    public void waitSomeSeconds(WebDriver mydriver) throws Exception {
 
        //DateFormat df = DateFormat.getTimeInstance(DateFormat.FULL);
        //Date date = new Date();
        //logger.info(df.format(date));                                      
        logger.info("wait 1 seconds");         
        Thread.sleep(waitTime);                              
        mydriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        //date = new Date();
        //logger.info(df.format(date));   
        logger.info("currentURL:"+mydriver.getCurrentUrl());
    }
    
    public void setAttributeValue(WebDriver mydriver,WebElement elem, String value){
        JavascriptExecutor js = (JavascriptExecutor) mydriver;
        String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";
        js.executeScript(scriptSetAttrValue, elem, "value", value);
    }
}