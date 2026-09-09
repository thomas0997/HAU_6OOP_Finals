package Support;

public class NavigationException extends Exception {
    private String errorCode;
    public NavigationException(String errorCode, String message){super(message); this.errorCode = errorCode;}

    public String getErrorCode(){return errorCode;}
}


