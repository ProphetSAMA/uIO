package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.Message;
import fun.wsss.uio.Mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public ResponseEntity<List<Message>> getMessage() {
        List<Message> messageList = messageMapper.selectList(null);
        return ResponseEntity.ok(messageList);
    }


    public ResponseEntity<String> addMessage(Message message) {
        messageMapper.insert(message);
        return ResponseEntity.ok("成功");
    }

}
