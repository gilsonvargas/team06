package cl.devpool.service;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author CyberCastle
 */
public class GenericResponse {

    protected String errorCode;
    protected String errorDescription;
    protected String errorTrace;

    public GenericResponse() {
        this.errorCode = "00";
        this.errorDescription = "Without Error";
        this.errorTrace = "";
    }

    public GenericResponse(String errorCode, String errorDescription, Exception error) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorTrace = this.ExceptionTraceToString(error);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorTrace() {
        return errorTrace;
    }

    public void setError(Exception error) {
        this.errorTrace = this.ExceptionTraceToString(error);
    }

    private String ExceptionTraceToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
