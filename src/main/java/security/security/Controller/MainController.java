package security.security.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import security.security.Global.GlobalFunction;
import security.security.Service.BoardService;
import security.security.Service.MemberService;
import security.security.Vo.BoardVo;
import security.security.Vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GlobalFunction globalFunction;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {

        int page = request.getParameter("page") != null
                ? Integer.parseInt(request.getParameter("page"))
                : 1;
        int limit = 5;
        int offset = (page-1) * limit;

        List<MemberVo> memberVoList = memberService.getPaginationList(offset, limit);
        int totalCount = memberService.getTotalCount();

        int lastPage = (int) Math.ceil(totalCount/limit);

        String pageExcludeQueryString = globalFunction.splitQueryString(request, "page");


        model.addAttribute("lists", memberVoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("page", page);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("queryString", pageExcludeQueryString);

        return "index";
    }

    @GetMapping("/async")
    public String async(HttpServletRequest request, Model model) {

        int page = request.getParameter("page") != null
                ? Integer.parseInt(request.getParameter("page"))
                : 1;

        int limit = 5;
        int offset = (page-1) * limit;
        List<MemberVo> memberVoList = memberService.getPaginationList(offset, limit);

        String pageExcludeQueryString = globalFunction.splitQueryString(request, "page");

        int totalCount = memberService.getTotalCount();

        int lastPage = (int) Math.ceil(totalCount/limit);

        model.addAttribute("lists", memberVoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("page", page);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("paginationAsync", false);
        model.addAttribute("queryString", pageExcludeQueryString);

        return "async";
    }


    @GetMapping("/async/list")
    public String asyncGetList(HttpServletRequest request, Model model) {

        int page = Integer.parseInt(request.getParameter("page"));
        int limit = 5;
        int offset = (page-1) * limit;
        List<MemberVo> memberVoList = memberService.getPaginationList(offset, limit);

        model.addAttribute("lists", memberVoList);

        return "component/list";
    }


    @GetMapping("/async/pagination")
    public String pagination(HttpServletRequest request, Model model) {

        int limit = 5;
        int totalCount = memberService.getTotalCount();
        int lastPage = (int) Math.ceil(totalCount/limit);

        model.addAttribute("page", request.getParameter("page"));
        model.addAttribute("keyword", request.getParameter("keyword"));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("range", 3);

        return "component/pagination";

    }


    @GetMapping("/login")
    public String login() {

//        MemberVo memberVo = memberService.findById("test");
//        System.out.println(memberVo.toString());

//        List<BoardVo> boardVo = boardService.getList();
//        System.out.println(boardVo.toString());

        System.out.println(passwordEncoder.encode("1234"));


        return "login";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }



}
