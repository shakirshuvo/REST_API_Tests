package tweet;

import base.CommonAPI;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class TweetAPIClient extends CommonAPI {

    private String invalidApiKey = "tDmeHGfs2gitCmDDPzbnxvy0y";

    private final String CREATE_TWEET_ENDPOINT = "/statuses/update.json";
    private final String CREATE_FAVORITES_ENDPOINT = "/favorites/create.json";
    private final String CREATE_SAVED_SEARCHES_ENDPOINT = "/saved_searches/create.json";
    private final String READ_TWEET_ENDPOINT = "/statuses/show.json";
    private final String READ_MULTIPLE_TWEETS_ENDPOINT = "/statuses/lookup.json";
    private final String DELETE_TWEET_ENDPOINT = "/statuses/destroy.json";
    private final String DELETE_FAVORITE_ENDPOINT = "/favorites/destroy.json";
    private final String DELETE_SAVED_SEARCHES_ENDPOINT = "/saved_searches/destroy/:id.json";
    private final String GET_USER_TWEET_ENDPOINT = "/statuses/user_timeline.json";
    private final String GET_USER_SEARCH_ENDPOINT = "/users/search.json";
    private final String GET_STANDARD_SEARCH_ENDPOINT = "/search/tweets.json";
    private final String GET_STATUSES_RETWEETERS_ENDPOINT = "/statuses/retweeters/ids.json";
    private final String GET_FAVORITES_LIST_ENDPOINT = "/favorites/list.json";
    private final String GET_LISTS_LIST_ENDPOINT = "/lists/list.json";
    private final String GET_FOLLOWERS_ENDPOINT = "/followers/ids.json";
    private final String GET_RETWEETS_OF_ME_ENDPOINT = "/statuses/retweets_of_me.json";
    private final String GET_MENTIONS_TIMELINE_ENDPOINT = "/statuses/mentions_timeline.json";
    private final String GET_ACCOUNT_VERIFY_CREDENTIALS_ENDPOINT = "/account/verify_credentials.json";
    private final String GET_USERS_PROFILE_BANNER_ENDPOINT = "/users/profile_banner.json";


    /**
     * This method allows user to create a new tweet.
     *
     * @param tweet
     * @return
     */
    public ValidatableResponse createTweet(String tweet) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("status", tweet)
                .when().post(this.baseUrl + this.CREATE_TWEET_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to create a new tweet without a valid API key.
     *
     * @param tweet
     * @return
     */
    public ValidatableResponse createTweetWithAnInvalidAPIKey(String tweet) {
        return given().auth().oauth(this.invalidApiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("status", tweet)
                .when().post(this.baseUrl + this.CREATE_TWEET_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to create a new tweet without a valid Endpoint.
     *
     * @param tweet
     * @return
     */
    public ValidatableResponse createTweetWithWrongEndpoint(String tweet) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("status", tweet)
                .when().post(this.baseUrl + this.CREATE_TWEET_ENDPOINT + "8923")
                .then();
    }

    /**
     * This method allows user to read a single tweet at a time.
     *
     * @param tweetID
     * @return
     */
    public ValidatableResponse getUserTweet(Long tweetID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", tweetID)
                .when().get(this.baseUrl + this.READ_TWEET_ENDPOINT)
                .then();
    }

    /**
     * This method allows user to read multiple tweets at a time.
     *
     * @param tweetID1
     * @param tweetID2
     * @return
     */
    public ValidatableResponse getMultipleTweets(Long tweetID1, Long tweetID2) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", tweetID1 + ", " + tweetID2)
                .when().get(this.baseUrl + this.READ_MULTIPLE_TWEETS_ENDPOINT)
                .then();
    }

    /**
     * This method allows user to read multiple tweets at a time.
     *
     * @param tweetID1
     * @param tweetID2
     * @return
     */
    public ValidatableResponse getMultipleTweetsWithWrongEndpoint(Long tweetID1, Long tweetID2) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", tweetID1 + ", " + tweetID2)
                .when().get(this.baseUrl + this.READ_MULTIPLE_TWEETS_ENDPOINT + "1234")
                .then();
    }

    // Read Twitter pst and verify with it_str
    //Read Twitter post
    public ValidatableResponse getUserTweetUsing_Id_str(String tweetID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", tweetID)
                .when().get(this.baseUrl + this.READ_TWEET_ENDPOINT)
                .then();
    }

    public ValidatableResponse getUserFavoriteTweetUsingUserId(String userID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("user_id", userID)
                .when().get(this.baseUrl + this.GET_FAVORITES_LIST_ENDPOINT)
                .then();
    }

    // GET ALL Tweet Information
    public ValidatableResponse getUserTimeTweet() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_USER_TWEET_ENDPOINT)
                .then();
    }

    // Delete a tweet from users twitter
    public ValidatableResponse deleteTweet(Long tweetId) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("id", tweetId)
                .when().post(this.baseUrl + this.DELETE_TWEET_ENDPOINT)
                .then();
    }

    /**
     * This method searches users on Twitter.
     *
     * @param search
     * @return
     */
    public ValidatableResponse searchUsers(String search) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("q", search)
                .when().get(this.baseUrl + this.GET_USER_SEARCH_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to search users on Twitter using invalid parameter.
     *
     * @param search
     * @return
     */
    public ValidatableResponse searchUsersUsingInvalidParameter(String search) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", search)
                .when().get(this.baseUrl + this.GET_USER_SEARCH_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to search users on Twitter using invalid endpoint.
     *
     * @param search
     * @return
     */
    public ValidatableResponse searchUsersUsingInvalidEndpoint(String search) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret + "1234")
                .param("q", search)
                .when().get(this.baseUrl + this.GET_USER_SEARCH_ENDPOINT)
                .then();
    }

    /**
     * This returns a collection of relevant Tweets matching a specified query.
     *
     * @param search
     * @return
     */
    public ValidatableResponse searchTweets(String search) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("q", search)
                .when().get(this.baseUrl + this.GET_STANDARD_SEARCH_ENDPOINT)
                .then();
    }

    /**
     * This returns a collection of relevant Tweets matching a specified query.
     *
     * @param search
     * @return
     */
    public ValidatableResponse searchTweetsWithoutAuthentication(String search) {
        return given().auth().none()
                .param("q", search)
                .when().get(this.baseUrl + this.GET_STANDARD_SEARCH_ENDPOINT)
                .then();
    }

    /**
     * This method will show the IDs of those who have re-tweeted a particular tweet.
     *
     * @param id
     * @return
     */
    public ValidatableResponse getReTweetersID(Long id) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", id)
                .when().get(this.baseUrl + this.GET_STATUSES_RETWEETERS_ENDPOINT)
                .then();
    }

    public ValidatableResponse createFavorites(Long tweetID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", tweetID)
                .when().post(this.baseUrl + this.CREATE_FAVORITES_ENDPOINT)
                .then();
    }

    /**
     * This method deletes a tweet that was added to favorite.
     *
     * @param tweetId
     * @return
     */
    public ValidatableResponse deleteFavorite(Long tweetId) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .queryParam("id", tweetId)
                .when().post(this.baseUrl + this.DELETE_FAVORITE_ENDPOINT)
                .then();
    }

    /**
     * This method returns a cursored collection of user IDs for every user following the specified user
     * using "screen_name".
     *
     * @param userID
     * @return
     */
    public ValidatableResponse getFollowersIDsUsingScreenNameParameter(String userID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("screen_name", userID)
                .when().get(this.baseUrl + this.GET_FOLLOWERS_ENDPOINT)
                .then();
    }

    /**
     * This method returns a cursored collection of user IDs for every user following the specified user
     * using "id" parameter.
     *
     * @param userID
     * @return
     */
    public ValidatableResponse getFollowersIDsUsingInvalidParameter(String userID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("id", userID)
                .when().get(this.baseUrl + this.GET_FOLLOWERS_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to return a cursored collection of user IDs for every user following the specified user
     * using invalid endpoint.
     *
     * @param userID
     * @return
     */
    public ValidatableResponse getFollowersIDsUsingInvalidEndpoint(String userID) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("screen_name", userID)
                .when().get(this.baseUrl + this.GET_FOLLOWERS_ENDPOINT + "1234")
                .then();
    }

    /**
     * This method returns the most recent Tweets authored by the authenticating user that have been
     * retweeted by others.
     *
     * @return
     */
    public ValidatableResponse getReTweetsOfMe() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_RETWEETS_OF_ME_ENDPOINT)
                .then();
    }

    /**
     * This method attempts to return the most recent Tweets authored by the authenticating user that have been
     * retweeted by others without authenticating.
     *
     * @return
     */
    public ValidatableResponse getReTweetsOfMeWithoutAuthentication() {
        return given().auth().none()
                .when().get(this.baseUrl + this.GET_RETWEETS_OF_ME_ENDPOINT)
                .then();
    }

    /**
     * This method returns the 20 most recent mentions (Tweets containing a users's @screen_name) for the
     * authenticating user.
     *
     * @return
     */
    public ValidatableResponse getMentionsTimeline() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_MENTIONS_TIMELINE_ENDPOINT)
                .then();
    }

    /**
     * This method returns a list of all Lists of the authenticating user subscribes to, including their own.
     *
     * @return
     */
    public ValidatableResponse getMyAListOfMyList() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_LISTS_LIST_ENDPOINT)
                .then();
    }


    /**
     * This method returns an HTTP 200 OK response code and a representation of the requesting user if
     * authentication was successful; returns a 401 status code and an error message if not.
     * Use this method to test if supplied user credentials are valid.
     * @return
     */
    public ValidatableResponse verifyAccountCredentials() {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .when().get(this.baseUrl + this.GET_ACCOUNT_VERIFY_CREDENTIALS_ENDPOINT)
                .then();
    }

    /**
     * This method returns a map of the available size variations of the specified user's profile banner.
     * @param userScreenName
     * @return
     */
    public ValidatableResponse getUsersProfileBanner(String userScreenName) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("screen_name", userScreenName)
                .when().get(this.baseUrl + this.GET_USERS_PROFILE_BANNER_ENDPOINT)
                .then();
    }

    /**
     * This method creates a new saved search for the authenticated user. A user may only have 25 saved searches.
     * @param search
     * @return
     */
    public ValidatableResponse createSavedSearches(String search) {
        return given().auth().oauth(this.apiKey, this.apiSecretKey, this.accessToken, this.accessTokenSecret)
                .param("query", search)
                .when().post(this.baseUrl + this.CREATE_SAVED_SEARCHES_ENDPOINT)
                .then();
    }

}
