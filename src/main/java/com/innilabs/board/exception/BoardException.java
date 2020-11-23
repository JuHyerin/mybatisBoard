package com.innilabs.board.exception;

import lombok.Getter;

@Getter
public class BoardException extends Exception{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
    
    private int type;

    private String link;

    public BoardException(String message){
        super(message);
    }

    public BoardException(int type, String message){
        super(message);
        this.type = type;
    }

   public BoardException(String message, String link){
       super(message);
       this.link = link;
   }
}