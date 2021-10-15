package security.security.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import security.security.Global.GlobalFunction;
import security.security.Service.BoardService;
import security.security.Service.MemberService;
import security.security.Vo.BoardVo;
import security.security.Vo.MemberVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
        System.out.println("test@@@@");
        List<MemberVo> memberVoRegexList = memberService.getListRegexp("bbit|og|ca");
        System.out.println(memberVoRegexList);


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

    @GetMapping("/create")
    public String create() {
        return "create";
    }





    @PostMapping("/image/upload")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile multi, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {

            //String uploadpath = request.getServletContext().getRealPath(path);
            String uploadpath = "/upload/img/";
            String originFilename = multi.getOriginalFilename();
            String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
            long size = multi.getSize();

            String saveFileName = "";

            Calendar calendar = Calendar.getInstance();
            saveFileName += calendar.get(Calendar.YEAR);
            saveFileName += calendar.get(Calendar.MONTH);
            saveFileName += calendar.get(Calendar.DATE);
            saveFileName += calendar.get(Calendar.HOUR);
            saveFileName += calendar.get(Calendar.MINUTE);
            saveFileName += calendar.get(Calendar.SECOND);
            saveFileName += calendar.get(Calendar.MILLISECOND);
            saveFileName += extName;


            System.out.println("uploadpath : " + uploadpath);
            System.out.println("originFilename : " + originFilename);
            System.out.println("extensionName : " + extName);
            System.out.println("size : " + size);
            System.out.println("saveFileName : " + saveFileName);

            ArrayList<File> imgStor = new ArrayList<>();
            ArrayList<String> imgName = new ArrayList<>();
            ArrayList<String> imgUrl = new ArrayList<>();

//            ImageLink link = new ImageLink();
            if(!multi.isEmpty())
            {
                imgName.add(saveFileName);
                File file = new File(uploadpath, saveFileName);
                imgStor.add(file);
                if(!file.exists()) // 해당 경로가 없을 경우
                    file.mkdirs();  // 폴더 생성
                multi.transferTo(file);
//                this.url = "https://www.willchair.co.kr/img/img_stor/"+saveFileName;
//                link.setFileName(saveFileName);
//                link.setUrl(url);
            }
            return "";
//            return link;
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return null;
    }



}
