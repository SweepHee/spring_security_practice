package security.security.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import security.security.Service.BoardService;
import security.security.Service.MemberService;
import security.security.Vo.BoardVo;
import security.security.Vo.MemberVo;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    MemberService memberService;

    @Autowired
    BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("text", "hi");

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String pass = passwordEncoder.encode("1234");
//        System.out.println(pass);

        return "index";
    }

    @GetMapping("/login")
    public String login() {

//        MemberVo memberVo = memberService.findById("test");
//        System.out.println(memberVo.toString());

        List<BoardVo> boardVo = boardService.getList();
        System.out.println(boardVo.toString());


        return "login";
    }


    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }



}
