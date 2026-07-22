package com.example.demo.dto;
public record CartItemResponse(long productId,String name,String category,double price,int quantity,double subtotal) {}
