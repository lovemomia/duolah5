package cn.momia.duolah5.ftl.composite;


import cn.momia.duolah5.ftl.base.Ftl;

public class PagedListFtl implements Ftl {
    public static final PagedListFtl EMPTY = new PagedListFtl();

    private long totalCount;
    private Integer nextIndex;
    private ListFtl list = new ListFtl();

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public ListFtl getList() {
        return list;
    }

    public void setList(ListFtl list) {
        this.list = list;
    }

    public void add(Ftl ftl) {
        list.add(ftl);
    }

    public void addAll(ListFtl dtos) {
        list.addAll(dtos);
    }
}
