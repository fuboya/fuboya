package sourcecode.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sourcecode.dto.NotificationDTO;
import sourcecode.dto.PaginationDTO;
import sourcecode.mapper.NotificationMapper;
import sourcecode.model.User;
import sourcecode.service.NotificationService;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="id") Long id)
    {

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);


        return "redirect:/question/"+ notificationDTO.getOuterid();
    }
}
