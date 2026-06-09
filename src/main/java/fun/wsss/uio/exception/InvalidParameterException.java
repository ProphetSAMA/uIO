package fun.wsss.uio.exception;

/**
 * 无效参数异常
 *
 * @author Wsssfun
 */
public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(String message) {
        super(400, message);
    }
}
