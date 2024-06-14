package sourcecode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    目录控制
 */
@Controller
public class indexController {

        @GetMapping("/")//定位主界面
        public String index()
        {
            return "index";
        }


}
