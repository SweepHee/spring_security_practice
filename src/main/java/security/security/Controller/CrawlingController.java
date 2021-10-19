package security.security.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import security.security.Crawling.Upbeat;

@Controller
public class CrawlingController {


    @Autowired
    Upbeat upbeat;

    @GetMapping("/craw")
    public String index() {

        upbeat.craw();

        return "/craw";
    }

}
