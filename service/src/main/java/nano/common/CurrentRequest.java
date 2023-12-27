package nano.common;

import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class CurrentRequest {

    public static String getCurrentRequestAddress() {
        var r = getCurrentRequest();
        if (r == null) {
            return null;
        }
        var ip = r.getHeader("X-Real-IP");
        if (ip == null) {
            ip = r.getRemoteAddr();
        }
        return ip;
    }

    public static @Nullable HttpServletRequest getCurrentRequest() {
        if (RequestContextHolder.currentRequestAttributes() instanceof ServletRequestAttributes requestAttributes) {
            return requestAttributes.getRequest();
        }
        return null;
    }
}
