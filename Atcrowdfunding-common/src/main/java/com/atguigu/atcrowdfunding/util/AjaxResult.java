package com.atguigu.atcrowdfunding.util;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-04 13:20
 * @Description:
 */
public class AjaxResult {
    private boolean success;
    private String message;

    public void setPage(Page page) {
        this.page = page;
    }

    private Page page;

    public Page getPage() {
        return page;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
