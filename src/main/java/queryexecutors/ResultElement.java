package queryexecutors;

public class ResultElement {
    public String field;
    public String value;

    public ResultElement(String field, String value) {
        this.field = field;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.field, this.value);
    }
}
