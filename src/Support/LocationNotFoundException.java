package Support;

public class LocationNotFoundException extends NavigationException{
    LocationNotFoundException(String message){
        super("ERROR!", message);
    }
}
