package com.creseliana.controller.util;

import com.creseliana.service.exception.AccessException;
import com.creseliana.service.exception.ad.AdvertisementNotFoundException;
import com.creseliana.service.exception.category.UniqueCategoryException;
import com.creseliana.service.exception.chat.ChatNotFoundException;
import com.creseliana.service.exception.comment.CommentFormatException;
import com.creseliana.service.exception.message.MessageFormatException;
import com.creseliana.service.exception.rating.NoSuchRateException;
import com.creseliana.service.exception.rating.RatingExistsException;
import com.creseliana.service.exception.rating.UserRateException;
import com.creseliana.service.exception.user.EmailFormatException;
import com.creseliana.service.exception.user.PhoneNumberFormatException;
import com.creseliana.service.exception.user.UniqueValueException;
import com.creseliana.service.exception.user.UserNotFoundException;
import com.creseliana.service.exception.user.UsernameFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String MSG_DataAccessException = "Server cannot handle request";
    private static final String MSG_AccessException = "Access denied";
    private static final String MSG_UserNotFoundException = "User not found";
    private static final String MSG_UniqueCategoryException = "Category should be unique";
    private static final String MSG_ChatNotFoundException = "Chat not found";
    private static final String MSG_AdvertisementNotFoundException = "Ad not found";
    private static final String MSG_MethodArgumentNotValidException = "Arguments are not valid";
    private static final String MSG_UnexpectedException = "Unexpected exception has occurred";

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(MSG_DataAccessException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessException.class})
    public ResponseEntity<String> handleAccessException(AccessException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_AccessException, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_UserNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UsernameFormatException.class})
    public ResponseEntity<String> handleUsernameFormatException(UsernameFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UniqueValueException.class})
    public ResponseEntity<String> handleUniqueValueException(UniqueValueException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {EmailFormatException.class})
    public ResponseEntity<String> handleEmailFormatException(EmailFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {PhoneNumberFormatException.class})
    public ResponseEntity<String> handlePhoneNumberFormatException(PhoneNumberFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UniqueCategoryException.class})
    public ResponseEntity<String> handleUniqueCategoryException(UniqueCategoryException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_UniqueCategoryException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MessageFormatException.class})
    public ResponseEntity<String> handleMessageFormatException(MessageFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    public ResponseEntity<String> handleChatNotFoundException(ChatNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_ChatNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AdvertisementNotFoundException.class})
    public ResponseEntity<String> handleAdvertisementNotFoundException(AdvertisementNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_AdvertisementNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentFormatException.class})
    public ResponseEntity<String> handleCommentFormatException(CommentFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {NoSuchRateException.class})
    public ResponseEntity<String> handleNoSuchRateException(NoSuchRateException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UserRateException.class})
    public ResponseEntity<String> handleUserRateException(UserRateException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {RatingExistsException.class})
    public ResponseEntity<String> handleRatingExistsException(RatingExistsException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(MSG_MethodArgumentNotValidException, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleUnexpectedException(Exception e) {
        log.warn(e.getMessage(), e);
        return new ResponseEntity<>(MSG_UnexpectedException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
