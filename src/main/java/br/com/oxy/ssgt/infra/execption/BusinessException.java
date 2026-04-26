package br.com.oxy.ssgt.infra.execption;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }
}
