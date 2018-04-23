package parser;

public class InvalidInputFileException extends Exception{

    private int row;
    private String text;

    InvalidInputFileException(String text, int row){
        this.row = row;
        this.text = text;
    }

    public String getMessage(){
        return "\u041f\u043e\u043c\u0438\u043b\u043a\u0430! \u0420\u044f\u0434\u043e\u043a: " + row + ". " + text;
    }
}
