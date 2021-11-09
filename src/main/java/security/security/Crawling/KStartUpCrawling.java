package security.security.Crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KStartUpCrawling implements Crawling {

    /*
    * 쿼리스트링 searchPostSn, searchPrefixCode 만 가져오면 됨
    * 게시글 리스트에서 javascript:itemSelect함수에서 가져오면 된다
    * https://www.k-startup.go.kr/common/announcement/announcementDetail.do?searchPostSn=142665&searchPrefixCode=BOARD_701_001
    * 페이징이 아니고 더보기 버튼. 버튼 로딩하는 로직 필요함
    * */

    private String url = "https://www.k-startup.go.kr/common/announcement/announcementList.do?mid=30004&bid=701&searchAppAt=A";

    @Override
    public void craw() {

        File driverFile = new File("/Users/seungheejeon/Desktop/workspace/2021_09/security/src/main/resources/chromedriver");
        String driverFilePath = driverFile.getAbsolutePath();

        if (!driverFile.exists() && driverFile.isFile()) {
            throw new RuntimeException("Not found");
        }

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                .usingAnyFreePort()
                .build();

        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebDriver driver = new ChromeDriver(service);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            for (int i=page; i>0; i--) {
                System.out.println("페이지::" + i);
                driver.get(url + i);

                for(int j=0; j<14; j++) {
                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"liArea"+ j +"\"]/h4/a"));

                    String title = titleXpath.getText();
                    String url = titleXpath.getAttribute("href");
                    Pattern p = Pattern.compile("(['\"])[^'\"]*\\1");
                    Matcher m = p.matcher(url);
                    System.out.println(title + ":: title");
                    while (m.find()) {
                        System.out.println(m.group());
                    }


                }


                Thread.sleep(500);
            }



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
            service.stop();
        }

    }


}
