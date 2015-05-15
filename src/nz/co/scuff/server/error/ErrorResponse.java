package nz.co.scuff.server.error;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Callum on 15/05/2015.
 */
@XmlRootElement
public class ErrorResponse {

    private String message;
    private String reason;
    private ErrorContextCode errorCode;

    public ErrorResponse(String message, String reason, ErrorContextCode errorCode) {
        this.message = message;
        this.reason = reason;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ErrorContextCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorContextCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", reason='" + reason + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}
