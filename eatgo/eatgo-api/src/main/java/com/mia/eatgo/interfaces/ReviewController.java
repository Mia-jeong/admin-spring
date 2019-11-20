package com.mia.eatgo.interfaces;


import com.mia.eatgo.application.ReviewService;
import com.mia.eatgo.domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @PostMapping("/restaurants/{restaurantId}/reviews")
    public ResponseEntity<?> create(@PathVariable("restaurantId") Long restaurantId,
                                    @Valid @RequestBody Review resource) throws URISyntaxException {

        Review saved = reviewService.addReview(restaurantId, resource);
        String url = "/restaurants/"+restaurantId+"/reviews/"+saved.getId();
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
