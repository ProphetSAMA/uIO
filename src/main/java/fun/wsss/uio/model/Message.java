package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import fun.wsss.uio.utils.Time;
import lombok.Getter;
import lombok.Setter;

/**
 * 留言实体类
 * @author Wsssfun
 */
@Setter
@Getter
@TableName("message")
public class Message {
    private String content;
    private String author;
    private String time;


    public Message(String content, String author, String time) {
        this.content = content;
        this.author = author;
        this.time = Time.getTime();
    }

}
