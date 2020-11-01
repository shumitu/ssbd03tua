package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ReviewDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.entities.Review;

import javax.ejb.Singleton;


public class ReviewConverter extends AbstractConverter<Review, ReviewDTO> {
    @Override
    public Review convert(ReviewDTO dto) {
        return new Review();
    }

    @Override
    public ReviewDTO convert(Review entity) {
        return new ReviewDTO(entity.getId(), entity.getReviewer().getEmployeeNumber(), entity.getCategory().getName());
    }
}
