package com.online.automobile.util;

public class Action {
    private String home;
    private String add;
    private String editView;
    private String authorized;
    private String report;

    public Action(String home, String add, String editView, String authorized, String report) {
        this.home = home;
        this.add = add;
        this.editView = editView;
        this.authorized = authorized;
        this.report = report;
    }

    public Action() {
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getEditView() {
        return editView;
    }

    public void setEditView(String editView) {
        this.editView = editView;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
