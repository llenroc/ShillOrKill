package com.mitchtalmadge.shillorkill.domain.service;

import com.mitchtalmadge.shillorkill.domain.model.Tweet;
import com.mitchtalmadge.shillorkill.domain.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    /**
     * Finds a Tweet that is suitable for voting upon.
     *
     * @return The Tweet that should be voted on, or null if no Tweets exist in the database.
     */
    public Tweet getTweetForVoting() {
        List<Object[]> tweetsUnderTenVotes = tweetRepository.findAllTweetsUnderTenVotes();
        if(tweetsUnderTenVotes.size() == 0)
            return null;

        return (Tweet) tweetsUnderTenVotes.get(0)[0];
    }

    /**
     * Casts a vote for the given Tweet.
     * Only one of shill/kill/wrong should be true.
     *
     * @param tweetId The ID of the Tweet entity to vote for.
     * @param shill   True if the vote is for shill.
     * @param kill    True if the vote is for kill.
     * @param wrong   True if the vote is for wrong coin.
     */
    public void castVote(long tweetId, boolean shill, boolean kill, boolean wrong) {
        // Find tweet.
        Tweet tweet = tweetRepository.findOne(tweetId);

        // Update tweet.
        if (shill)
            tweet.voteShill();
        else if (kill)
            tweet.voteKill();
        else if (wrong)
            tweet.voteWrong();

        // Save tweet.
        tweetRepository.save(tweet);
    }
}
