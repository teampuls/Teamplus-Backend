package Backend.teampplus.common.error.exception;

import Backend.teampplus.common.error.ErrorCode;
import lombok.Getter;

/**
 * status : 403
 */
@Getter
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super(ErrorCode._BAD_REQUEST, ErrorCode._BAD_REQUEST.getMessage());
    }
    public ForbiddenException(String message) {
        super(ErrorCode._BAD_REQUEST, message);
    }
}
