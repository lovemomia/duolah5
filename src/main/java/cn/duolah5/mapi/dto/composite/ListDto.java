package cn.duolah5.mapi.dto.composite;

import cn.duolah5.dto.base.Dto;

import java.util.ArrayList;

public class ListDto extends ArrayList<Dto> implements Dto {
    public static final ListDto EMPTY = new ListDto();
}
