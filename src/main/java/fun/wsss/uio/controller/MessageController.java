package fun.wsss.uio.controller;


import fun.wsss.uio.model.Message;
import fun.wsss.uio.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息Controller
 *
 * @author Wsssfun
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 获取留言
     *
     * @return 留言列表
     */
    @GetMapping("/message")
    public ResponseEntity<List<Message>> getMessage() {
        return messageService.getMessage();
    }

    /**
     * 新增留言
     *
     * @param message 留言
     * @return 保存结果
     */
    @PostMapping("/message")
    public ResponseEntity<String> postMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }
}
