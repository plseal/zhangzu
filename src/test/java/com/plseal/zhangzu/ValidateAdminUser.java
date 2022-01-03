package com.plseal.zhangzu;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertNotNull;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class ValidateAdminUser  {

    private static final Logger logger = LoggerFactory.getLogger(ValidateAdminUser.class);

    TestCommon testCommon = new TestCommon();

    @Test
    public void validate_admin_user_add(WebDriver mydriver) throws Exception {
        logger.info("＃＃＃＃＃＃管理者追加検証 start");

        mydriver.navigate().to("http://localhost:8090/admin_user_modify");
        testCommon.waitSomeSeconds(mydriver);

        
        logger.info("＃＃＃「管理者追加」ボタン　押下");
        WebElement USER_ADD_BUTTON = mydriver.findElement(By.id("USER_ADD_BUTTON"));
        assertNotNull(USER_ADD_BUTTON);
        USER_ADD_BUTTON.click();
        testCommon.waitSomeSeconds(mydriver);

        logger.info("＃＃＃「追加」ボタン　押下");
        WebElement ADD_OK_BUTTON = mydriver.findElement(By.id("ADD_OK_BUTTON"));
        ADD_OK_BUTTON.click();
        testCommon.waitSomeSeconds(mydriver);
        WebElement ADMIN_USER_ADD_ERROR_003_02 = mydriver.findElement(By.id("ADMIN_USER_ADD_ERROR_003_02"));
        assertNotNull(ADMIN_USER_ADD_ERROR_003_02);
        logger.info("admin_user_modify.html ADMIN_USER_ADD_ERROR_003_02 --> OK");
        logger.info("＃＃＃新パスワードを入力してください。 --> OK");

        WebElement new_password1 = mydriver.findElement(By.id("new_password1"));
        testCommon.setAttributeValue(mydriver,new_password1,"9990000");
        ADD_OK_BUTTON = mydriver.findElement(By.id("ADD_OK_BUTTON"));
        ADD_OK_BUTTON.click();
        testCommon.waitSomeSeconds(mydriver);
        WebElement ADMIN_USER_ADD_ERROR_004_02 = mydriver.findElement(By.id("ADMIN_USER_ADD_ERROR_004_02"));
        assertNotNull(ADMIN_USER_ADD_ERROR_004_02);
        logger.info("admin_user_modify.html ADMIN_USER_ADD_ERROR_004_02 --> OK");
        logger.info("＃＃＃新パスワード(確認)を入力してください。 --> OK");

        
        new_password1 = mydriver.findElement(By.id("new_password1"));
        WebElement new_password2 = mydriver.findElement(By.id("new_password2"));
        testCommon.setAttributeValue(mydriver,new_password1,"9990000");
        testCommon.setAttributeValue(mydriver,new_password2,"9990000");
        ADD_OK_BUTTON = mydriver.findElement(By.id("ADD_OK_BUTTON"));
        ADD_OK_BUTTON.click();
        logger.info("＃＃＃「追加」ボタン　押下");
        testCommon.waitSomeSeconds(mydriver);

        logger.info("＃＃＃＃＃＃管理者追加検証 end");

    }

    
    public void validate_admin_user_modify(WebDriver mydriver) throws Exception {
        logger.info("＃＃＃＃＃＃管理者修正検証 start");
        
        mydriver.navigate().to("http://localhost:8090/admin_user_modify");
        testCommon.waitSomeSeconds(mydriver);

        //変更ボタン
        WebElement MODIFY_BUTTON9990000 = mydriver.findElement(By.id("MODIFY_BUTTON9990000"));
        assertNotNull(MODIFY_BUTTON9990000);
        MODIFY_BUTTON9990000.click();
        testCommon.waitSomeSeconds(mydriver);
        
        // //ADMIN_USER_MODIFY_ERROR_003
        // WebElement MODIFY_OK_BUTTON = mydriver.findElement(By.id("MODIFY_OK_BUTTON"));
        // MODIFY_OK_BUTTON.click();
        // testCommon.waitSomeSeconds(mydriver);
        // WebElement ADMIN_USER_MODIFY_ERROR_003 = mydriver.findElement(By.id("ADMIN_USER_MODIFY_ERROR_003"));
        // assertNotNull(ADMIN_USER_MODIFY_ERROR_003);
        // logger.info("admin_user_modify.html ADMIN_USER_MODIFY_ERROR_003 --> OK");
        // logger.info("＃＃＃新パスワードを入力してください。---> OK");

        // //ADMIN_USER_MODIFY_ERROR_004
        // logger.info("admin_user_modify.html ADMIN_USER_MODIFY_ERROR_004 --> OK");
        // logger.info("＃＃＃新パスワード(確認)を入力してください。---> OK");

        //ADMIN_USER_MODIFY_ERROR_005
        WebElement new_password1 = mydriver.findElement(By.id("new_password1"));
        WebElement new_password2 = mydriver.findElement(By.id("new_password2"));
        WebElement mail_address = mydriver.findElement(By.id("mail_address"));
        WebElement teams_mail_address1 = mydriver.findElement(By.id("teams_mail_address1"));
        WebElement teams_mail_address2 = mydriver.findElement(By.id("teams_mail_address2"));
        testCommon.setAttributeValue(mydriver,new_password1,"9990001");
        testCommon.setAttributeValue(mydriver,new_password2,"9990000");
        testCommon.setAttributeValue(mydriver,mail_address,"gisenface@gmail.com");
        testCommon.setAttributeValue(mydriver,teams_mail_address1,"762581e4.nttdatajpprod.onmicrosoft.com@jp.teams.ms");
        testCommon.setAttributeValue(mydriver,teams_mail_address2,"762581e4.nttdatajpprod.onmicrosoft.com@jp.teams.ms");
        WebElement MODIFY_OK_BUTTON = mydriver.findElement(By.id("MODIFY_OK_BUTTON"));
        MODIFY_OK_BUTTON.click();
        testCommon.waitSomeSeconds(mydriver);
        WebElement ADMIN_USER_MODIFY_ERROR_005 = mydriver.findElement(By.id("ADMIN_USER_MODIFY_ERROR_005"));
        assertNotNull(ADMIN_USER_MODIFY_ERROR_005);
        logger.info("admin_user_modify.html ADMIN_USER_MODIFY_ERROR_005 --> OK");
        logger.info("＃＃＃新パスワードと新パスワード(確認)が一致しません。---> OK");

        //ADMIN_USER_MODIFY_SUCCESS_006
        //old_password = mydriver.findElement(By.id("old_password"));
        new_password1 = mydriver.findElement(By.id("new_password1"));
        new_password2 = mydriver.findElement(By.id("new_password2"));
        //setAttributeValue(old_password,"0010001");
        testCommon.setAttributeValue(mydriver,new_password1,"9990000");
        testCommon.setAttributeValue(mydriver,new_password2,"9990000");
        MODIFY_OK_BUTTON = mydriver.findElement(By.id("MODIFY_OK_BUTTON"));
        MODIFY_OK_BUTTON.click();
        testCommon.waitSomeSeconds(mydriver);
        WebElement ADMIN_USER_MODIFY_SUCCESS_006 = mydriver.findElement(By.id("ADMIN_USER_MODIFY_SUCCESS_006"));
        assertNotNull(ADMIN_USER_MODIFY_SUCCESS_006);
        logger.info("admin_user_modify.html ADMIN_USER_MODIFY_SUCCESS_006 --> OK");


        logger.info("＃＃＃＃＃＃管理者修正検証 end");

    }

    public void validate_admin_user_delete(WebDriver mydriver) throws Exception {
        logger.info("＃＃＃＃＃＃管理者削除検証 start");
        
        mydriver.navigate().to("http://localhost:8090/admin_user_modify");
        testCommon.waitSomeSeconds(mydriver);

        //削除ボタン
        WebElement DELETE_BUTTON9990000 = mydriver.findElement(By.id("DELETE_BUTTON9990000"));
        assertNotNull(DELETE_BUTTON9990000);
        
        boolean flag = false;
        Alert alert = null;
        try {
            DELETE_BUTTON9990000.click();
            
            new WebDriverWait(mydriver, 10).until(ExpectedConditions
                    .alertIsPresent());
            alert = mydriver.switchTo().alert();
            flag = true;
            // alert.accept();
        } catch (NoAlertPresentException NofindAlert) {
            NofindAlert.printStackTrace();
            // throw NofindAlert;
        }

        if (flag) {
            alert.accept();
        }
        testCommon.waitSomeSeconds(mydriver);

        logger.info("＃＃＃＃＃＃管理者削除検証 end");

    }
}
