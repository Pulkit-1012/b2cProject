package in.ongrid.b2cverification.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {//constructor excepting a message
        super(message);//passing that message to the super class
    }
}
