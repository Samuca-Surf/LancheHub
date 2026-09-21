package com.samuca.lanchehub.exception;

public class ProdutoIndisponivelException extends RuntimeException {
    public ProdutoIndisponivelException(String message) {
        super(message);
    }
}
