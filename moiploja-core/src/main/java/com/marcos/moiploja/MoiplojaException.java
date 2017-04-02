/**
 *
 */
package com.marcos.moiploja;

/**
 * @author Marcos
 */
public class MoiplojaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MoiplojaException() {
        super();
    }

    public MoiplojaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MoiplojaException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoiplojaException(String message) {
        super(message);
    }

    public MoiplojaException(Throwable cause) {
        super(cause);
    }

}
