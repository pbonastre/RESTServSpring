package com.db.turialHateaos;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/{userId}/bookmarks")
class BookmarkRestController {

	private final BookmarkRepository bookmarkRepository;

	private final AccountRepository accountRepository;

    BookmarkRestController(BookmarkRepository bookmarkRepository,
                           AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET ,produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
	Resources<BookmarkResource> readBookmarks(@PathVariable String userId) {

		this.validateUser(userId);

		List<BookmarkResource> bookmarkResourceList = bookmarkRepository
				.findByAccountUsername(userId).stream().map(BookmarkResource::new)
				.collect(Collectors.toList());

		return new Resources<>(bookmarkResourceList);
	}

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {

		this.validateUser(userId);

		return accountRepository.findByUsername(userId)
            .map(account -> {
                Bookmark bookmark = bookmarkRepository
                        .save(new Bookmark(account, input.uri, input.description));

                Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");

                return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
            })
            .orElse(ResponseEntity.noContent().build());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
	BookmarkResource readBookmark(@PathVariable String userId,
                                  @PathVariable Long bookmarkId) {
		this.validateUser(userId);
		return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
	}

	private void validateUser(String userId) {
		this.accountRepository
			.findByUsername(userId)
			.orElseThrow(() -> new UserNotFoundException(userId));
	}
}