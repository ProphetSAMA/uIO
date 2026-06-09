package fun.wsss.uio.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 认证过滤器
 * 从请求的 Authorization header 中提取 JWT token，验证并设置认证信息
 *
 * @author Wsssfun
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${app.jwt.secret:DefaultJwtSecretKeyFor256BitHmacShaAlgorithmPleaseChangeMe}")
    private String jwtSecret;

    @Autowired(required = false)
    private UserIdExtractor userIdExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 从请求中获取 Authorization header
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(BEARER_PREFIX.length());

                try {
                    // 验证并解析 JWT token
                    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
                    Claims claims = Jwts.parserBuilder()
                            .setSigningKey(key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

                    // 从 token 中提取用户名
                    String username = claims.getSubject();
                    if (username != null) {
                        // 尝试从 token 中获取 userId
                        Long userId = null;
                        Object userIdClaim = claims.get("userId");
                        if (userIdClaim != null) {
                            if (userIdClaim instanceof Long) {
                                userId = (Long) userIdClaim;
                            } else if (userIdClaim instanceof Integer) {
                                userId = ((Integer) userIdClaim).longValue();
                            } else if (userIdClaim instanceof String) {
                                try {
                                    userId = Long.parseLong((String) userIdClaim);
                                } catch (NumberFormatException e) {
                                    logger.debug("Failed to parse userId from token: {}", userIdClaim);
                                }
                            }
                        }

                        // 如果 token 中没有 userId，尝试从数据库查询
                        if (userId == null && userIdExtractor != null) {
                            userId = userIdExtractor.getUserIdByUsername(username);
                        }

                        // 创建 UsernamePasswordAuthenticationToken
                        Map<String, Object> details = new HashMap<>();
                        if (userId != null) {
                            details.put("userId", userId);
                        }

                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(username, token, new ArrayList<>());
                        authToken.setDetails(details);

                        // 设置 SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        logger.debug("JWT token validated for user: {}", username);
                    }
                } catch (JwtException | IllegalArgumentException e) {
                    logger.debug("JWT token validation failed: {}", e.getMessage());
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception e) {
            logger.error("Error processing JWT token", e);
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 用户 ID 提取器接口，用于从数据库中获取用户 ID
     */
    public interface UserIdExtractor {
        Long getUserIdByUsername(String username);
    }
}
