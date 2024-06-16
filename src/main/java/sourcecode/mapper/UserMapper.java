package sourcecode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sourcecode.model.User;

/*

BaseMapper 是 MyBatis-Plus 提供的一个通用 Mapper 接口，包含了很多基础的数据库操作方法，如：

insert(T entity)：插入一条记录。
deleteById(Serializable id)：根据 ID 删除一条记录。
updateById(T entity)：根据 ID 更新一条记录。
selectById(Serializable id)：根据 ID 查询一条记录。
selectList(Wrapper<T> queryWrapper)：根据条件查询多条记录。

通过扩展 BaseMapper<User>，UserMapper 接口自动拥有了这些方法，无需重新定义。

 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE token = #{token}")
    User findByToken(String token);
}
