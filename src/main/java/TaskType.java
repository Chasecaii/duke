public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E"),
    AFTER("A");

    private final String code;

    TaskType(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}