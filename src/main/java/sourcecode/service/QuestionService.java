package sourcecode.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sourcecode.dto.PaginationDTO;
import sourcecode.dto.QuestionDTO;
import sourcecode.mapper.QuestionMapper;
import sourcecode.mapper.UserMapper;
import sourcecode.model.Question;
import sourcecode.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer totalcount = questionMapper.count();
        paginationDTO.setPagination(totalcount,page,size);

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        if(page<1){
            page=1;
        }

        Integer offset = size * (page-1);      //数据库中的偏移量  0 5； 5 5； 10 5；其中5为每页显示个数即size

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
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
        Integer totalcount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalcount,page,size);

        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        if(page<1){
            page=1;
        }

        Integer offset = size * (page-1);      //数据库中的偏移量  0 5； 5 5； 10 5；其中5为每页显示个数即size

        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
