package se.kth.adamfhhugoper.labb3b.model.Exceptions;

public class TitleNotUniqueException extends RuntimeException {

    public TitleNotUniqueException(String message) {
        super();
    }

    public TitleNotUniqueException(){
        super("Title not unique!");
    }
}
