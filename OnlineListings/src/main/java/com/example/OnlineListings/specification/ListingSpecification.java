package com.example.OnlineListings.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.OnlineListings.enums.Category;
import com.example.OnlineListings.model.Listing;

import jakarta.persistence.criteria.Predicate;

public class ListingSpecification {
    public static Specification<Listing> withFilters(String category, String keyword, Double minPrice, Double maxPrice, String owner) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (category != null) {
                predicates.add(cb.equal(root.get("category"), Category.valueOf(category)));
            }

            if (keyword != null && !keyword.isEmpty()) {
                String keywordLower = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("name")), keywordLower),
                    cb.like(cb.lower(root.get("description")), keywordLower),
                    cb.like(cb.function("str", String.class, root.get("price")), keywordLower),
                    cb.like(cb.lower(root.get("category").as(String.class)), keywordLower),
                    cb.like(cb.lower(root.get("owner").get("username")), keywordLower),
                    cb.like(cb.lower(root.get("city")), keywordLower)
                ));
            }

            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (owner != null) {
                predicates.add(cb.equal(root.get("owner").get("username"), owner));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
