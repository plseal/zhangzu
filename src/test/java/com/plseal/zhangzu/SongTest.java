package com.plseal.zhangzu;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.TimeUnit;
import static org.hamcrest.CoreMatchers.is;
//assertThatメソッドはorg.junit.Assert.assertThatではなくorg.hamcrest.MatcherAssert.assertThatを使用する
//JUnitのassertThatは非推奨
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhangzuApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SongTest extends BaseSeleniumTests {
    private static final Logger logger = LoggerFactory.getLogger(SongTest.class);
    Integer waitTime = 2;
    TestCommon testCommon = new TestCommon();
    ValidateAdminUser validateAdminUser = new ValidateAdminUser();
    private String title;
    @Test
    public void 正常系01_index() {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/index");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("宋账本"));
    }
    @Test
    public void 正常系02_insert() throws Exception {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/insert");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("新增"));

        WebElement z_name = this.driver.findElement(By.id("z_name"));
        assertNotNull(z_name);
        testCommon.setAttributeValue(driver,z_name,"テスト");

        WebElement z_amount = this.driver.findElement(By.id("z_amount"));
        assertNotNull(z_amount);
        testCommon.setAttributeValue(driver,z_amount,"1000");

        WebElement z_type = this.driver.findElement(By.id("z_type"));
        Select dropdown = new Select(z_type);
        assertNotNull(z_type);
        dropdown.selectByValue("日常用品");

        WebElement z_io_div = this.driver.findElement(By.id("z_io_div"));
        assertNotNull(z_io_div);
        dropdown = new Select(z_io_div);
        assertNotNull(z_type);
        dropdown.selectByValue("支出");

        WebElement z_remark = this.driver.findElement(By.id("z_remark"));
        assertNotNull(z_remark);
        testCommon.setAttributeValue(driver,z_remark,"z_remark");  

        WebElement z_m_amount = this.driver.findElement(By.id("z_m_amount"));
        assertNotNull(z_m_amount);
        testCommon.setAttributeValue(driver,z_m_amount,"100");  

        logger.info("＃＃＃「提交」ボタン　押下");
        WebElement ADD_BUTTON = driver.findElement(By.name("insert_button"));
        assertNotNull(ADD_BUTTON);
        ADD_BUTTON.click();
        testCommon.waitSomeSeconds(driver);
    }

    @Test
    public void 正常系03_update() throws Exception {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/index");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        logger.info("＃＃＃「编辑」ボタン　押下");
        WebElement MODIFY_BUTTON = driver.findElement(By.name("id"));
        assertNotNull(MODIFY_BUTTON);
        MODIFY_BUTTON.click();
        testCommon.waitSomeSeconds(driver);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("流水帐更新"));

        WebElement z_name = this.driver.findElement(By.id("z_name"));
        assertNotNull(z_name);
        testCommon.setAttributeValue(driver,z_name,"去旅馆交通费");

        WebElement z_amount = this.driver.findElement(By.id("z_amount"));
        assertNotNull(z_amount);
        testCommon.setAttributeValue(driver,z_amount,"10000");

        WebElement z_type = this.driver.findElement(By.id("z_type"));
        Select dropdown = new Select(z_type);
        assertNotNull(z_type);
        dropdown.selectByValue("交通费");

        WebElement z_io_div = this.driver.findElement(By.id("z_io_div"));
        assertNotNull(z_io_div);
        dropdown = new Select(z_io_div);
        assertNotNull(z_type);
        dropdown.selectByValue("收入");

        WebElement z_remark = this.driver.findElement(By.id("z_remark"));
        assertNotNull(z_remark);
        testCommon.setAttributeValue(driver,z_remark,"z_remark00");  

        WebElement z_m_amount = this.driver.findElement(By.id("z_m_amount"));
        assertNotNull(z_m_amount);
        testCommon.setAttributeValue(driver,z_m_amount,"1000");  

        logger.info("＃＃＃「更新」ボタン　押下");
        WebElement OK_BUTTON = driver.findElement(By.name("update_delete_button"));
        assertNotNull(OK_BUTTON);
        OK_BUTTON.click();
        testCommon.waitSomeSeconds(driver);
    }

    @Test
    public void 正常系04_delete() throws Exception {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/index");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        logger.info("＃＃＃「编辑」ボタン　押下");
        WebElement MODIFY_BUTTON = driver.findElement(By.name("id"));
        assertNotNull(MODIFY_BUTTON);
        MODIFY_BUTTON.click();
        testCommon.waitSomeSeconds(driver);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("流水帐更新"));

        logger.info("＃＃＃「删除」ボタン　押下");
        WebElement DEL_BUTTON = driver.findElement(By.xpath("//*[@name='update_delete_button'][@value='DELETE']"));
        assertNotNull(DEL_BUTTON);
        DEL_BUTTON.click();
        testCommon.waitSomeSeconds(driver);
    }

    @Test
    @Ignore("skip analysis_bar")
    public void 正常系05_analysis_bar() throws Exception {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/index");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        logger.info("＃＃＃「統計分析」ボタン　押下");
        WebElement ANALYSIS_BUTTON = driver.findElement(By.id("song_analysis_bar"));
        assertNotNull(ANALYSIS_BUTTON);
        ANALYSIS_BUTTON.click();
        testCommon.waitSomeSeconds(driver);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("统计分析(柱状图)"));
        
        // 元データ
        //'2022/01/02','超市',2000,'餐饮饮食','支出'
        //'2022/01/03','超市',3000,'餐饮饮食','支出'
        title = driver.getTitle();
        WebElement actual_data = driver.findElement(By.xpath("//*[@id=\"group-by-type-table\"]/tbody/tr[1]/td[2]"));
        
        assertThat(actual_data.getText(), is("-202000"));
        
        
    }

    @Test
    @Ignore("skip analysis_pie")
    public void 正常系06_analysis_pie() throws Exception {
        //指定したURLに遷移する
        driver.get("http://127.0.0.1:8080/song/index");

        // 最大5秒間、ページが完全に読み込まれるまで待つ
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        logger.info("＃＃＃「統計分析」ボタン　押下");
        WebElement ANALYSIS_BUTTON = driver.findElement(By.id("song_analysis_pie"));
        assertNotNull(ANALYSIS_BUTTON);
        ANALYSIS_BUTTON.click();
        testCommon.waitSomeSeconds(driver);

        // 検証
        title = driver.getTitle();
        assertThat(title, is("统计分析(饼图)"));
        

        title = driver.getTitle();
        WebElement actual_data = driver.findElement(By.xpath("/html/body/div/div/table/tbody/tr/td[2]"));
        
        assertThat(actual_data.getText(), is("-404000"));
        
        
    }
    

 

    


 

    
}