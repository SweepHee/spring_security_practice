package security.security.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import security.security.Crawling.KStartUpCrawling;
import security.security.Crawling.RcdaCrawling;
import security.security.Crawling.TestCrawling;
import security.security.Crawling.Upbeat;

@Controller
public class CrawlingController {


    @Autowired
    Upbeat upbeat;

    @Autowired
    TestCrawling testCrawling;

    @Autowired
    RcdaCrawling rcdaCrawling;

    @Autowired
    KStartUpCrawling kStartUpCrawling;

    @GetMapping("/craw")
    public String index() {

        kStartUpCrawling.craw();

        return "/craw";
    }

}
