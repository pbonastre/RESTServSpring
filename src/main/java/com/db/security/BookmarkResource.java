package com.db.security;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import com.db.turialHateaos.Bookmark;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * @author Greg Turnquist
 */
// tag::code[]
class BookmarkResource extends ResourceSupport {

	private final Bookmark bookmark;

	public BookmarkResource(Bookmark bookmark) {
		String username = bookmark.getAccount().getUsername();
		this.bookmark = bookmark;
		this.add(new Link(bookmark.uri, "bookmark-uri"));
		this.add(linkTo(BookmarkRestController.class, username).withRel("bookmarks"));
		this.add(linkTo(
				methodOn(BookmarkRestController.class, username).readBookmark(null,
						bookmark.getId())).withSelfRel());
	}

	public Bookmark getBookmark() {
		return bookmark;
	}
}