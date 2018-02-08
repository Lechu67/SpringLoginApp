package app1.model;

import java.util.List;

public class BoardSymbolsResponse {

    private List<BoardResponse> symbolsLocations;
    private char symbol;

    public BoardSymbolsResponse(List<BoardResponse> symbolsLocations, char symbol) {
        this.symbolsLocations = symbolsLocations;
        this.symbol = symbol;
    }

    public List<BoardResponse> getSymbolsLocations() {
        return symbolsLocations;
    }

    public void setSymbolsLocations(List<BoardResponse> symbolsLocations) {
        this.symbolsLocations = symbolsLocations;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
