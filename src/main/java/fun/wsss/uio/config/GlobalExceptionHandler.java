package fun.wsss.uio.config;

import fun.wsss.uio.dto.ApiResponse;
import fun.wsss.uio.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Wsssfun
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        logger.warn("业务异常: {}", e.getMessage());
        Integer code = e.getCode() != null ? e.getCode() : 400;
        return ResponseEntity.status(HttpStatus.valueOf(code > 500 ? 500 : code))
                .body(ApiResponse.error(code, e.getMessage()));
    }

    /**
     * 处理参数验证异常
     *
     * @param e 参数验证异常
     * @return 响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数验证失败");
        logger.warn("参数验证异常: {}", message);
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(400, message));
    }

    /**
     * 处理通用异常
     *
     * @param e 异常
     * @return 响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        logger.error("系统异常", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "系统错误，请稍后重试"));
    }
}
