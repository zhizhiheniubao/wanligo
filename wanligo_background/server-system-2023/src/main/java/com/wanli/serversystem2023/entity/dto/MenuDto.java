package com.wanli.serversystem2023.entity.dto;


import lombok.Data;

import java.util.List;

@Data
public class MenuDto {
    private Long id;
    private String title;
    private String icon;
    private String path;
    private String name;
    private String perms;
    private String component;
    private List<MenuDto> children;
}
