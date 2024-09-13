package fun.wsss.uio.dto.user;

import lombok.Data;

/**
 * 用户DTO
 *
 * @author Wsssfun
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String roomDisplay;
}
