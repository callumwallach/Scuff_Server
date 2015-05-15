package nz.co.scuff.server.error;

import javax.ws.rs.core.Response;

/**
 * Created by Callum on 15/05/2015.
 */
public class ScuffServerException extends Exception {

    private String reason;
    private Response.Status status;
    private ErrorContextCode errorCode;

    public ScuffServerException(String message, String reason, Response.Status status, ErrorContextCode errorCode) {
        super(message);
        this.reason = reason;
        this.status = status;
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public ErrorContextCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorContextCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ScuffServerException{" +
                "reason='" + reason + '\'' +
                ", status=" + status +
                ", errorCode=" + errorCode +
                "} " + super.toString();
    }
}
