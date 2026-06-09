package fun.wsss.uio.exception;

/**
 * 用户已存在异常
 *
 * @author Wsssfun
 */
public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(String message) {
        super(400, message);
    }
}
