package david_kang.retail_platform.exception;

import david_kang.retail_platform.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

// Catch any exception and convert to API response.
// Exception thrown -> Is a handler for this exception type registered here? -> Yes -> Call handler automatically

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle ProductNotFoundException.
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleProductNotFound(
            ProductNotFoundException ex) {

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Product Not Found",
                        ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    // Handle SkuAlreadyExistsException.
    @ExceptionHandler(SkuAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleSkuAlreadyExists(
            SkuAlreadyExistsException ex) {

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Duplicate SKU",
                        ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    // Handle InsufficientInventoryException.
    @ExceptionHandler(
            InsufficientInventoryException.class)
    public ResponseEntity<ErrorResponse>
    handleInventoryException(
            InsufficientInventoryException ex) {

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Insufficient Inventory",
                        ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // Handle validation errors (for any controller method that includes @Valid annotation).
    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException ex) {

        FieldError fieldError =
                ex.getBindingResult()
                        .getFieldError();

        String message =
                fieldError != null
                        ? fieldError.getDefaultMessage()
                        : "Validation failed";

        // TODO: check for and return all validation errors rather than just the first one, if multiple exist.

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Validation Error",
                        message);

        return ResponseEntity
                .badRequest()
                .body(error);
    }

    // Catch-all handler (500 Internal Server Error).
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGenericException(
            Exception ex) {

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        "An unexpected error occurred");
        // Do not return ex.getMessage() in order to avoid leaking implementation details to API consumer.

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}