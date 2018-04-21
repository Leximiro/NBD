package parser;

public class InvalidInputFileException extends Exception{

    private int row;
    private String text;

    InvalidInputFileException(String text, int row){
        this.row = row;
        this.text = text;
    }

    public String getMessage(){
        return "Помилка! Рядок: " + row + ". " + text;
    }
}
