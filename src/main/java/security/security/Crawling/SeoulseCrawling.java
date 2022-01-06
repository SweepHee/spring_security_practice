package security.security.Crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import security.security.Mapper.ContentsMapper;
import security.security.Vo.ContentsVo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SeoulseCrawling implements Crawling {

    @Autowired
    ContentsMapper contentsMapper;

    /*
     * 서울사회적기업협의회
     * http://www.seoulse.kr/
     *  */

    private String url = "http://www.seoulse.kr/bbs/board.php?bo_table=notice&page=";
    private int page = 1;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void craw() throws InterruptedException {

        File driverFile = new File("/Users/seungheejeon/Desktop/workspace/2021_09/security/src/main/resources/chromedriver_96");


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
        WebDriver driver2 = new ChromeDriver(service);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebDriverWait wait2 = new WebDriverWait(driver, 10);

        List<ContentsVo> contentsVos = new ArrayList<>();


        for (int i=page; i>0; i--) {
            System.out.println("페이지222::" + i);
            driver.get(url + i);

            for(int j=1; j<16; j++) {

                try {

                    //*[@id="list-body"]/li[9]/div[2]/a/span

                    /* li not selector */
//                    WebElement titleXpath = driver.findElement(By.xpath("//*[@id=\"list-body\"]/li[not(contains(@class, 'bg-light'))]["+j+"]/div[2]/a/span"));
                    WebElement bodyUrlXpath = driver.findElement(By.xpath("//*[@id=\"list-body\"]/li[not(contains(@class, 'bg-light'))]["+j+"]/div[2]/a"));

                    String title = bodyUrlXpath.getText();
                    String url = bodyUrlXpath.getAttribute("href");

                    Pattern p = Pattern.compile("\\[(.*?)\\]");
                    Matcher m = p.matcher(title);
                    ArrayList<String> pattern = new ArrayList<String>();

                    while (m.find()) {
                        pattern.add(m.group());
                    }

                    driver2.get(url + i);
                    WebElement targettypeXpath = driver2.findElement(By.xpath("//*[@id=\"page\"]/div[1]/section/div/div/section/article/div[1]/div[1]/div/span[2]"));

                    System.out.println("여기5");
                    String targettype = targettypeXpath.getText();

                    System.out.println("여기6");
                    System.out.println("타겟타입::" + targettype);

                    Thread.sleep(1500);
//                    driver2.quit();

//                    ContentsVo vo = new ContentsVo();
//                    vo.setTargetname("서울청년포털");
//                    vo.setTargetnamecode("임의코드");
//                    vo.setTargettype(targettype);
//                    vo.setTargettypecode(targettype);
//                    vo.setTargetcost("-");
//                    vo.setLoccode("02");
//                    vo.setTitle(title);
//                    vo.setBodyurl(url);
//                    vo.setEndTime("");

//                    HashMap<String, String> params = new HashMap<>();
//                    params.put("bodyurl", url);
//                    boolean isUrl = contentsMapper.isUrl(params);
//                    System.out.println("이미 수집된 URL입니다::" + isUrl);
//                    if (!isUrl) {
//                        contentsVos.add(vo);
//                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }

            }

            Thread.sleep(500);
        }

        /* 빈 리스트가 아니면 크레이트 */
//        if (!contentsVos.isEmpty()) {
//            contentsMapper.create(contentsVos);
//        }



        driver.quit();
        service.stop();

    }


}
