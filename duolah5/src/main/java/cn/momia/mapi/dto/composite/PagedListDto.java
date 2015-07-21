package cn.momia.mapi.dto.composite;


import cn.momia.duolah5.dto.base.Dto;

public class PagedListDto implements Dto {
    public static final PagedListDto EMPTY = new PagedListDto();

    private long totalCount;
    private Integer nextIndex;
    private ListDto list = new ListDto();

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

    public ListDto getList() {
        return list;
    }

    public void setList(ListDto list) {
        this.list = list;
    }

    public void add(Dto dto) {
        list.add(dto);
    }

    public void addAll(ListDto dtos) {
        list.addAll(dtos);
    }
}
