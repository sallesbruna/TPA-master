package dao;

public class Result<T> {

    private T payload;
    private String message;

    public Result(T payload, String message){
        this.payload = payload;
        this.message = message;
    }

    public Result(T payload){
        this.payload = payload;
    }


    public boolean hasMessage(){
        return message != null;
    }

    public String getMessage(){
        return message;
    }

    public T getPayload(){
        return payload;
    }
}
