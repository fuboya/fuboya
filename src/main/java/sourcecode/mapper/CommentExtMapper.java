package sourcecode.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import sourcecode.model.Comment;
import sourcecode.model.CommentExample;
import sourcecode.model.Question;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}