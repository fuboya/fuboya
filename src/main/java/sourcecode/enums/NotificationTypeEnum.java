package sourcecode.enums;

public enum NotificationTypeEnum {
    REPLY_QUESTION(1,"回复了你的问题"),
    REPLY_COMMENT(1,"回复了你的评论");

    public static String nameOfType(int type) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values())
        {
            if(notificationTypeEnum.getType() == type)
            {
                return notificationTypeEnum.getName();
            }

        }
        return "";
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private int type;
    String name;

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}
