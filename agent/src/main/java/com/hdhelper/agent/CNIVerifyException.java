package com.hdhelper.agent;

// Error types thrown when verifying the CNI.
// These errors are thrown when the CNI is in a invalid state to be started
public class CNIVerifyException extends RuntimeException {
    public CNIVerifyException() {}
    public CNIVerifyException(String msg) {
        super(msg);
    }
}
