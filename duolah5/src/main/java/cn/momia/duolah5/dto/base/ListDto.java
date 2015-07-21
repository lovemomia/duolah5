package cn.momia.duolah5.dto.base;

import java.util.ArrayList;

/**
 * Created by ysm on 15-7-20.
 */
public class ListDto extends ArrayList<Dto>implements Dto {
    public static final ListDto EMPTY = new ListDto();
}

