package com.wanli.entity;

import lombok.Data;

import java.util.List;
@Data
public class CartDto {
    private Business business;
    private List<Food> foodList;
}
