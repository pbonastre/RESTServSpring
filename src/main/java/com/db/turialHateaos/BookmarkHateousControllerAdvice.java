package  com.db.turialHateaos;

import org.springframework.hateoas.VndErrors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
class BookmarkHateousControllerAdvice {

		@ResponseBody
		@ExceptionHandler(UserNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		VndErrors userNotFoundExceptionHandler(UserNotFoundException ex) {
			return new VndErrors("error", ex.getMessage());
		}
	}