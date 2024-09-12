package fun.wsss.uio.service;

import fun.wsss.uio.mapper.MessageMapper;
import fun.wsss.uio.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息Service
 *
 * @author Wsssfun
 */
@Service
public class MessageService {
    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    /**
     * 获取数据
     */
    public ResponseEntity<List<Message>> getMessage() {
        List<Message> messageList = messageMapper.selectList(null);
        return ResponseEntity.ok(messageList);
    }


    /**
     * 插入数据
     */
    public ResponseEntity<String> addMessage(Message message) {
        messageMapper.insert(message);
        return ResponseEntity.ok("成功");
    }

}
