package com.mitchtalmadge.shillorkill.domain.repository;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    Tweet findByStatusId(long statusId);

    /**
     * Finds the Tweets which have less than 10 total votes.
     *
     * @return An Object array containing: 1. The Tweet, and 2. The number of total votes for the Tweet.
     */
    @Query("SELECT t, (t.shills + t.kills + t.wrongs) as votes from Tweet t WHERE (t.shills + t.kills + t.wrongs) < 10 ORDER BY votes ASC")
    List<Object[]> findAllTweetsUnderTenVotes();

}
