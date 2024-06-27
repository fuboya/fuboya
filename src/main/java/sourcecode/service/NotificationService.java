package sourcecode.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sourcecode.dto.NotificationDTO;
import sourcecode.dto.PaginationDTO;
import sourcecode.dto.QuestionDTO;
import sourcecode.enums.NotificationStatusEnum;
import sourcecode.enums.NotificationTypeEnum;
import sourcecode.mapper.NotificationMapper;
import sourcecode.mapper.UserMapper;
import sourcecode.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO<NotificationDTO> list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO=new PaginationDTO<NotificationDTO>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        Integer totalcount =(int) notificationMapper.countByExample(notificationExample);
        paginationDTO.setPagination(totalcount,page,size);

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        if(page<1){
            page=1;
        }

        Integer offset = size * (page-1);      //数据库中的偏移量  0 5； 5 5； 10 5；其中5为每页显示个数即size

        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExample(example);

        if(notifications.size()==0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS= new ArrayList<>();

        for (Notification notification : notifications){
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setType(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }

        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long countUnRead(Long id) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(example);
    }

    public NotificationDTO read(Long id, User user) {

        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if (notification == null) {
            throw new IllegalArgumentException("Notification not found");
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);

        return notificationDTO;
    }

}
