package sourcecode.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sourcecode.dto.PaginationDTO;
import sourcecode.service.QuestionService;

/*
    主页
 */
@Controller
public class indexController {

        @Autowired
        private QuestionService questionService;

        @GetMapping("/")//定位主界面
        //访问所有cookies，根据cooikes判断是否处于数据库中
        public String index(HttpServletRequest request,
                            Model model,
                            @RequestParam(name="page",defaultValue = "1") Integer page,
                            @RequestParam(name="size",defaultValue = "6") Integer size) {


            PaginationDTO pagination = questionService.list(page,size);
            model.addAttribute("pagination",pagination);
            return "index";
        }

}
