package sourcecode.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sourcecode.dto.PaginationDTO;
import sourcecode.model.User;
import sourcecode.service.NotificationService;
import sourcecode.service.QuestionService;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,@PathVariable(name="action") String action,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){//查询问题
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的发布");
            PaginationDTO paginationDTO = questionService.list(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);

        } else if ("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            Long unReadCount = notificationService.countUnRead(user.getId());
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unReadCount",unReadCount);
            model.addAttribute("pagination",paginationDTO);
        }


        return "profile";
    }
}
