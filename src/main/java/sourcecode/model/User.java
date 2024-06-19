package sourcecode.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

//数据库传输
@Data
@TableName("user")//映射到数据库中的user表
public class User  {

    @TableId
    private Integer id;
    private String name;
    private String account_id;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String bio;
    private String avatarUrl;
}
