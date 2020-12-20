package com.epam.web.command;

public class CommandResult {
    private static final String ERROR_PAGE = "WEB-INF/view/pages/error-page.jsp";

    private final String page;
    private final boolean isRedirect;

    private CommandResult(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    public static CommandResult error() {
        return new CommandResult(ERROR_PAGE, false);
    }

    public String getPage() {
        return page;
    }

    public boolean getIsRedirect() {
        return isRedirect;
    }

}
