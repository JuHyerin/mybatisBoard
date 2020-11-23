package com.innilabs.board.handler;

import java.sql.SQLException;

import com.innilabs.board.exception.BoardException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@ControllerAdvice
@Controller
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BoardException.class)
    public String handlerBoardException(BoardException e, Model model){
        /* if(e.getType()==1){
            //예외 종류에 따라 메세지 변경
        } 
        log.error("일치하는 게시물 없음",e);
        return "<h1>"+e.getMessage()+"</h1>";
        */
        log.error(e.getMessage(), e);
        model.addAttribute("error", e);
        return "error";
    }

  
  /*   @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e, Model model){
        log.error("exception 발생", e);
        //return "<h1>서버 상태 이상</h1>";
        //model.addAttribute("error", e);
        return "error";
    } 
 */
    


}