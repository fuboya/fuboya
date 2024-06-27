package sourcecode.cache;

import sourcecode.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript","php","cpp","c","typescript","objective-c","html","java","node","python"));
        tagDTOS.add(program);

        TagDTO framework = new TagDTO();
        framework.setCategoryName("开发框架");
        framework.setTags(Arrays.asList("laravel","express","django","flask","yii","koa","struts"));
        tagDTOS.add(framework);


        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache","ubuntu","centos","tomcat","unix","hadoop","windows-server"));
        tagDTOS.add(server);

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","sqlserver","sqlite","postgresql"));
        tagDTOS.add(db);

        TagDTO tool = new TagDTO();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git","github","atom","intellij-idea","xcode","emacs","visual-studio","visual-studio-code","maven","ide","svr"));
        tagDTOS.add(tool);

        return tagDTOS;
    }

}
