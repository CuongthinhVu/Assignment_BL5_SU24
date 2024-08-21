package model;

public class Paging {
    private int totalItems;
    private int itemsPerPage;
    private int currentPage;
    private int totalPages;
    private int begin;
    private int end;
    
    public void calc() {
        totalPages = (totalItems + itemsPerPage - 1) / itemsPerPage;
        currentPage = currentPage >= totalPages ? totalPages - 1 : currentPage;
        currentPage = currentPage < 0 ? 0 : currentPage;
        begin = currentPage * itemsPerPage;
        end = begin + itemsPerPage;
        end = end > totalItems ? totalItems : end;
    }

    public Paging() {
    }

    public Paging(int totalItems, int itemsPerPage, int currentPage) {
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = currentPage;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
