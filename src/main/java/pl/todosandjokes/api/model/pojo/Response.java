package pl.todosandjokes.api.model.pojo;

public class Response {
    String message;

    public Response() {}

    public Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
