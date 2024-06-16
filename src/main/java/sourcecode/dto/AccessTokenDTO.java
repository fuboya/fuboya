package sourcecode.dto;

import lombok.Data;
//网络传输
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}

