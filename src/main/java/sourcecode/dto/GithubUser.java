package sourcecode.dto;

import lombok.Data;

@Data//lombok注解
public class GithubUser {
    private String name;
    private Long id;
    private String bio;//描述
    private String avatar_url;
}
