package sourcecode.modules.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("admin/user")//定位
public class AppUserController {

            @GetMapping("/hello")//重定位
            public String greeting(@RequestParam(name = "name") String name , Model model)
            {
                model.addAttribute("name",name);
                return "greeting";
            }
}
