package fun.wsss.uio.exception;

/**
 * 用户未找到异常
 *
 * @author Wsssfun
 */
public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(404, message);
    }
}
