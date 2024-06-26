package sourcecode.dto;

import lombok.Data;
import sourcecode.model.User;

@Data
public class CommentDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
