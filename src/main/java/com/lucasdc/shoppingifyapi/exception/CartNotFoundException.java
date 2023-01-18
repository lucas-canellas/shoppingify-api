package com.lucasdc.shoppingifyapi.exception;

public class CartNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public CartNotFoundException(String message) {
        super(message);
    }
    
    public CartNotFoundException(Long cartId) {
        this(String.format("Não existe um cadastro de Cart com código %d", cartId));
    }

}
