package cn.momia.mapi.dto.composite;

import cn.momia.duolah5.dto.base.Dto;

import java.util.ArrayList;

public class ListDto extends ArrayList<Dto> implements Dto {
    public static final ListDto EMPTY = new ListDto();
}
