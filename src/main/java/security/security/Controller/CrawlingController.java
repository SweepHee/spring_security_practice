package security.security.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import security.security.Crawling.*;

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

    @Autowired
    SbscCrawling sbscCrawling;

    @GetMapping("/craw")
    public String index() {

        sbscCrawling.craw();

        return "/craw";
    }

}
