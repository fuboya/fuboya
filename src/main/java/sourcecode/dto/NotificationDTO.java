package sourcecode.dto;

import lombok.Data;
import sourcecode.model.User;

@Data
public class NotificationDTO {
    private Long id;


    private Long gmtCreate;// 创建时间

    private Integer status;//是否已读

    private Long notifier;// 通知者

    private String notifierName;// 通知者

    private String outerTitle;// 通知来源

    private String type;

    private Long outerid;
}
