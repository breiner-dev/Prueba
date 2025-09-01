package com.prueba.tecnica.domain.product;

public record Stock(int value) {
    public Stock { if (value < 0) throw new IllegalArgumentException("stock < 0"); }
}