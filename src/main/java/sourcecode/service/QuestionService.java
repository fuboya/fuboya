package sourcecode.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sourcecode.dto.PaginationDTO;
import sourcecode.dto.QuestionDTO;
import sourcecode.exception.CustomizeErrorCode;
import sourcecode.exception.CustomizeException;
import sourcecode.mapper.QuestionExtMapper;
import sourcecode.mapper.QuestionMapper;
import sourcecode.mapper.UserMapper;
import sourcecode.model.Question;
import sourcecode.model.QuestionExample;
import sourcecode.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalcount =(int) questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalcount,page,size);

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        if(page<1){
            page=1;
        }

        Integer offset = size * (page-1);      //数据库中的偏移量  0 5； 5 5； 10 5；其中5为每页显示个数即size

        List<Question> questions = questionMapper.selectByExampleWithBLOBs(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalcount =(int) questionMapper.countByExample(questionExample);
        paginationDTO.setPagination(totalcount,page,size);

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        if(page<1){
            page=1;
        }

        Integer offset = size * (page-1);      //数据库中的偏移量  0 5； 5 5； 10 5；其中5为每页显示个数即size

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBs(example,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException("帖子不存在，请稍后再试");
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion,example);
            if(updated!=1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
