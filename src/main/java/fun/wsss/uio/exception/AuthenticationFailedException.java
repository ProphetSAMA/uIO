package fun.wsss.uio.exception;

/**
 * 认证失败异常
 *
 * @author Wsssfun
 */
public class AuthenticationFailedException extends BusinessException {
    public AuthenticationFailedException(String message) {
        super(401, message);
    }
}
