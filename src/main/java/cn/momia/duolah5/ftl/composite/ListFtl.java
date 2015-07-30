package cn.momia.duolah5.ftl.composite;

import cn.momia.duolah5.ftl.base.Ftl;

import java.util.ArrayList;

public class ListFtl extends ArrayList<Ftl> implements Ftl {
    public static final ListFtl EMPTY = new ListFtl();
}
