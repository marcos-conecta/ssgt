package br.com.oxy.ssgt.infra.execption;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
