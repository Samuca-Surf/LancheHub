    package com.samuca.lanchehub.handler;

    import com.samuca.lanchehub.exception.ProdutoIndisponivelException;
    import com.samuca.lanchehub.exception.RecursoNaoEncontrado;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    import java.util.HashMap;
    import java.util.Map;

    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

            Map<String, String> errors = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );

            return ResponseEntity.badRequest().body(errors);
        }

        @ExceptionHandler(ProdutoIndisponivelException.class)
        public ResponseEntity<String> handleRecursoExistente(ProdutoIndisponivelException ex) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }

        @ExceptionHandler(RecursoNaoEncontrado.class)
        public ResponseEntity<String> handleRecursoNaoEncontrado(RecursoNaoEncontrado ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
