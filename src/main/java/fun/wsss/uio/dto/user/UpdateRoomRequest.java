package fun.wsss.uio.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户房间更新请求 DTO
 *
 * @author Wsssfun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomRequest {
    @NotNull(message = "房间信息不能为空")
    private List<Integer> selectedRoom;
}
