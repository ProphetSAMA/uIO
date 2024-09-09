package fun.wsss.uio.Controller;


import fun.wsss.uio.Entity.Message;
import fun.wsss.uio.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/message")
    public ResponseEntity<List<Message>> getMessage() {
        return messageService.getMessage();
    }

    @PostMapping("/message")
    public ResponseEntity<String> postMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }
}
