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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessException.class})
    public ResponseEntity<String> handleAccessException(AccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    //todo manage exception handling

    @ExceptionHandler(value = {UsernameFormatException.class})
    public ResponseEntity<String> handleUsernameFormatException(UsernameFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UniqueValueException.class})
    public ResponseEntity<String> handleUniqueValueException(UniqueValueException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EmailFormatException.class})
    public ResponseEntity<String> handleEmailFormatException(EmailFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PhoneNumberFormatException.class})
    public ResponseEntity<String> handlePhoneNumberFormatException(PhoneNumberFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UniqueCategoryException.class})
    public ResponseEntity<String> handleUniqueCategoryException(UniqueCategoryException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MessageFormatException.class})
    public ResponseEntity<String> handleMessageFormatException(MessageFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ChatNotFoundException.class})
    public ResponseEntity<String> handleChatNotFoundException(ChatNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AdvertisementNotFoundException.class})
    public ResponseEntity<String> handleAdvertisementNotFoundException(AdvertisementNotFoundException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentFormatException.class})
    public ResponseEntity<String> handleCommentFormatException(CommentFormatException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NoSuchRateException.class})
    public ResponseEntity<String> handleNoSuchRateException(NoSuchRateException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserRateException.class})
    public ResponseEntity<String> handleUserRateException(UserRateException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RatingExistsException.class})
    public ResponseEntity<String> handleRatingExistsException(RatingExistsException e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> handleUnexpectedException(Exception e) {
        log.debug(e.getMessage(), e);
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }
}
