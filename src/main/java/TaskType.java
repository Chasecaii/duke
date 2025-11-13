// REMARK (code reuse):
// JUnit 5 test structure (annotations/assertions) adapted from JUnit 5 User Guide examples.
// Source: https://junit.org/junit5/docs/current/user-guide/

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