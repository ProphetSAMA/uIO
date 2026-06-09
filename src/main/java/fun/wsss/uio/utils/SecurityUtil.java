package fun.wsss.uio.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 安全工具类
 * 用于获取当前登录用户信息
 *
 * @author Wsssfun
 */
@Component
public class SecurityUtil {

    /**
     * 获取当前认证用户的用户名
     *
     * @return 用户名，如果未认证则返回 null
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * 获取当前认证用户的 ID（从 JWT token 中提取）
     *
     * @return 用户 ID，如果未认证或 ID 不存在则返回 null
     */
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getDetails() instanceof Map) {
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            Object userId = details.get("userId");
            if (userId instanceof Long) {
                return (Long) userId;
            } else if (userId instanceof Number) {
                return ((Number) userId).longValue();
            }
        }
        return null;
    }

    /**
     * 检查当前用户是否已认证
     *
     * @return 如果已认证则返回 true
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
