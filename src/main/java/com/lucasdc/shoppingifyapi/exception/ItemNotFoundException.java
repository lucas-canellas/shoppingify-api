package com.lucasdc.shoppingifyapi.exception;

public class ItemNotFoundException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public ItemNotFoundException(String message) {
        super(message);
    }

    public ItemNotFoundException(Long itemId) {
        this(String.format("Não existe um cadastro de item com código %d", itemId));
    }


    


}
