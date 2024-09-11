package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import fun.wsss.uio.Utils.Time;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
