package org.swetlokognatsk.earking_out.app.web.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.InvalidTextNoteException;
import org.swetlokognatsk.earking_out.core.domain.model.session.exceptions.OutOfRangeTextNoteException;
import static org.springframework.http.ResponseEntity.*;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice(assignableTypes = ApiController.class)
public class ApiControllerExceptionsHandler {

    @ExceptionHandler(InvalidTextNoteException.class)
    public ResponseEntity<?> handleInvalidNote(final InvalidTextNoteException e) {
        // TODO InvalidTextNoteException
        return badRequest()
                .body("InvalidTextNoteException");
    }

    @ExceptionHandler(OutOfRangeTextNoteException.class)
    public ResponseEntity<?> handleOutOfRangeTextNote(final OutOfRangeTextNoteException e) {
        // TODO OutOfRangeTextNoteException
        return badRequest()
                .body("OutOfRangeTextNoteException");
    }

}
