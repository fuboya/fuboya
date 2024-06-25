package sourcecode.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("帖子不在了，请稍后再试");
    @Override
    public String getMessage() {
        return message;
    }
    private String message;
    CustomizeErrorCode(String message) {
        this.message = message;
    }

}
