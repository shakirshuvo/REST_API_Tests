package tweeter;


import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

public class TweetAPIClientTest {

    private TweetAPIClient tweetAPIClient;

    @BeforeClass
    public void setUpTweetAPI() {
        this.tweetAPIClient = new TweetAPIClient();
    }

    /**
     * This test allows user to create a new tweet.
     */
    @Test
    public void testUserCanTweetSuccessfully() {
        String tweet = "Hey, can I run with you?" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
    }

    /**
     * This test verifies that user will not be able to create a new tweet with an invalid API key.
     */
    @Test
    public void testUserCannotCreateTweetWithAnInvalidAPIKey() {
        String tweet = "Who left the keys up on the table!" + UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createTweetWithAnInvalidAPIKey(tweet);
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(401, actualStatusCode);
    }

    /**
     * This test verifies that user will not be able to create a new tweet with an invalid Endpoint.
     */
    @Test
    public void TestCreateTweetWithWrongEndpoint() {
        String tweet = "You shall not pass!" + UUID.randomUUID().toString();
        ValidatableResponse response = tweetAPIClient.createTweetWithWrongEndpoint(tweet);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(404, actualCode);
    }

    /**
     * This test verifies that user cannot tweet the same tweet twice in a row.
     */
    @Test
    public void testUserCanNotTweetTheSameTweetTwiceInARow() {
        // 1. user send a tweet
        String tweet = "My horse, is a shackled old man";
        ValidatableResponse response = this.tweetAPIClient.createTweet(tweet);
        response.statusCode(200);
        System.out.println(response.extract().body().asString());
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
        // User send the same tweet again
        response = this.tweetAPIClient.createTweet(tweet);
        // Verify that the tweet was unsuccessful
        response.statusCode(403);
        //System.out.println(response.extract().body().asString());
        String expectedMessage = "Status is a duplicate.";
        String actualMessage = response.extract().body().path("errors[0].message");
        Assert.assertEquals(actualMessage, expectedMessage);
        Assert.assertNotSame("200", 403);
    }

    /**
     * This test verifies that user can delete a tweet.
     */
    @Test
    public void testDelete() {
        String tweet = "We are learning RestAPI Automation and Tweet check";
        ValidatableResponse response = this.tweetAPIClient.deleteTweet(1305764882550157319l);
        // Verify that the tweet was successfully deleted
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
    }

    /**
     * This test verifies that user can read a single tweet.
     */
    @Test
    public void testReadTweet() {
        String tweet = "We are all disturbed";
        ValidatableResponse response = this.tweetAPIClient.getUserTweet(1306141569196539911l);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(tweet, actualTweet);
    }

    /**
     * This test reads a tweet using String type ID.
     */
    @Test
    public void testGetUserTweetUsing_Id_str() {
        String tweet = "Some people try to be different and some people try to make a difference";
        ValidatableResponse response = this.tweetAPIClient.getUserTweetUsing_Id_str("1307711467387596801");
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        System.out.println(actualTweet);
        Assert.assertEquals(tweet, actualTweet);
    }

    /**
     * This negative test verifies that user can not read a single tweet because of wrong tweet ID. There's not tweet
     * corresponding to ID: 1906141569196539911
     */
    @Test
    public void testUserCannotReadSingleTweet() {
        String tweet = "We are all disturbed";
        ValidatableResponse response = this.tweetAPIClient.getUserTweet(1906141569196539911l);
        String actualTweet = response.extract().body().path("text");
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(404, actualCode);
    }

    /**
     * This test verifies that multiple tweets can be read at the same time.
     */
    @Test
    public void TestGetMultipleTweets() {
        ValidatableResponse response = tweetAPIClient.getMultipleTweets(1308946974146736128l, 1308929223537692675l);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(200, actualCode);
    }

    /**
     * This test verifies that multiple tweets can be read at the same time due to wrong endpoint.
     */
    @Test
    public void TestUserCannotGetMultipleTweets() {
        ValidatableResponse response = tweetAPIClient.getMultipleTweetsWithWrongEndpoint(1308946974146736128l, 1308929223537692675l);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(404, actualCode);
    }

    /**
     * This test validates that from reply to an original tweet, we can retrieve the tweet id of the original tweet.
     */
    @Test
    public void testReadReplyToTweet() {
        String originalTweetID = "1307030903722586113";
        ValidatableResponse response = this.tweetAPIClient.getUserTweet(1307910904118480897l);
        response.statusCode(200);
        String actualOriginalTweetID = response.extract().body().path("in_reply_to_status_id_str");
        System.out.println(actualOriginalTweetID);
        Assert.assertEquals(originalTweetID, actualOriginalTweetID);
    }

    /**
     * This test verifies that user can successfully search users on Twitter.
     */
    @Test
    public void testSearchUsers() {
        String search = "Sheikh Ahmed Deedat";
        ValidatableResponse response = this.tweetAPIClient.searchUsers(search);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(200, actualCode);
    }

    /**
     * This test verifies that user cannot successfully search users on Twitter due to invalid search.
     */
    @Test
    public void testSearchUsersWithInvalidSearch() {
        String search = "Bangladesh (/ˌbæŋləˈdɛʃ/,[14] Bengali: বাংলাদেশ, pronounced [ˈbaŋlaˌdeʃ] (About this soundlisten)), officially the People's Republic of Bangladesh, is a country in South Asia. It is the eighth-most populous country in the world, with a population exceeding 162 million people.[15] In terms of landmass, Bangladesh ranks 92nd, spanning 148,460 square kilometres (57,320 sq mi), making it one of the most densely-populated countries in the world. Bangladesh shares land borders with India to the west, north, and east, Myanmar to the southeast, and the Bay of Bengal to the south. It is narrowly separated from Nepal and Bhutan by the Siliguri Corridor, and from China by Sikkim, in the north, respectively. Dhaka, the capital and largest city, is the nation's economic, political and cultural hub. Chittagong, the largest sea port, is the second-largest city.";
        ValidatableResponse response = this.tweetAPIClient.searchUsers(search);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(400, actualCode);
    }

    /**
     * This test verifies that user cannot successfully search users on Twitter due to invalid parameter.
     */
    @Test
    public void testSearchUsersUsingInvalidParameter() {
        String search = "Sheikh Ahmed Deedat";
        ValidatableResponse response = this.tweetAPIClient.searchUsersUsingInvalidParameter(search);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(400, actualCode);
    }

    /**
     * This test verifies that user cannot successfully search users on Twitter due to invalid endpoint.
     */
    @Test
    public void testSearchUsersUsingInvalidEndpoint() {
        String search = "Sheikh Ahmed Deedat";
        ValidatableResponse response = this.tweetAPIClient.searchUsersUsingInvalidEndpoint(search);
        int actualCode = response.extract().statusCode();
        String test = response.extract().statusLine();
        System.out.println(test);
        Assert.assertEquals(401, actualCode);
    }

    /**
     * This test verifies that user cannot successfully search users on Twitter due to invalid endpoint
     * and verified by status line.
     */
    @Test
    public void testSearchUsersUsingInvalidEndpointUsingStatusLine() {
        String search = "Sheikh Ahmed Deedat";
        ValidatableResponse response = this.tweetAPIClient.searchUsersUsingInvalidEndpoint(search);
        String actualCode = response.extract().statusLine();
        Assert.assertEquals("HTTP/1.1 401 Authorization Required", actualCode);
    }

    /**
     * This test verifies that user can successfully search on Twitter.
     */
    @Test
    public void testSearchTweets() {
        String search = "coronavirus";
        ValidatableResponse response = this.tweetAPIClient.searchTweets(search);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(200, actualCode);
    }

    /**
     * This test verifies that user cannot successfully search on Twitter without authentication.
     */
    @Test
    public void testSearchTweetsWithoutAuthentication() {
        String search = "coronavirus";
        ValidatableResponse response = this.tweetAPIClient.searchTweetsWithoutAuthentication(search);
        String actualCode = response.extract().statusLine();
        Assert.assertEquals("HTTP/1.1 400 Bad Request", actualCode);
    }

    /**
     * This test verifies that we're able to get the IDs of the re-twitters of a particular tweet.
     */
    @Test
    public void testGetReTweetersID() {
        ValidatableResponse response = this.tweetAPIClient.getReTweetersID(1068663459896688645l);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(200, actualCode);
    }

    /**
     * This test verifies that the user is not able to get the IDs of the re-twitters of a particular tweet due to an
     * nonexistent tweet. I.e., the tweed ID 1906141569196539911 does not exists or valid.
     */
    @Test
    public void testGetReTweetersWithInvalidID() {
        ValidatableResponse response = this.tweetAPIClient.getReTweetersID(1906141569196539911l);
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(404, actualCode);
    }

    /**
     * This test returns the 20 most recent Tweets liked by the authenticating or specified user.
     */
    @Test
    public void testGetUserFavoriteTweetUsingUserId() {
        String tweet = "At times, we condemn good people because we disagree with them over a few issues. We refuse to see the good they’re doing. Disagreements do not automatically make a person extreme, bad or evil. Learn to distinguish between the one who respectfully disagrees & the one who is evil!\n";
        ValidatableResponse response = this.tweetAPIClient.getUserFavoriteTweetUsingUserId("Shakir51093668");
        int actualCode = response.extract().statusCode();
        Assert.assertEquals(200, actualCode);
    }

    /**
     * This test verifies that the user can favorite a tweet.
     */
    @Test(priority = 1)
    public void testUserCanFavoriteATweet() {
        String expectedTweet = "Since becoming your representative in Congress, I have fought, introduced, and pushed for policies that will benefi… https://t.co/Nyc8bEUfO3";
        ValidatableResponse response = this.tweetAPIClient.createFavorites(1275199873525129216l);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        System.out.println(actualTweet);
        Assert.assertEquals(expectedTweet, actualTweet);
    }

    /**
     * This test verifies that the user can delete favorite a tweet.
     */
    @Test(priority = 2)
    public void testDeleteFavorite() {
        String expectedTweet = "Since becoming your representative in Congress, I have fought, introduced, and pushed for policies that will benefi… https://t.co/Nyc8bEUfO3";
        ValidatableResponse response = this.tweetAPIClient.deleteFavorite(1275199873525129216l);
        response.statusCode(200);
        String actualTweet = response.extract().body().path("text");
        Assert.assertEquals(expectedTweet, actualTweet);
    }

    /**
     * This test verifies that user cannot favorite a tweet that has already been added to favorite. The user should
     * receive a 403 HTML status code error.
     */
    @Test
    public void testUserCannotFavoriteATweetTwice() {
        ValidatableResponse response = this.tweetAPIClient.createFavorites(1081416374243733504l);
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(403, actualStatusCode);

    }

    /**
     * This test verifies that the user cannot delete a favorite that's not on favorite.
     */
    @Test
    public void testDeleteFavoriteTwice() {
        ValidatableResponse response = this.tweetAPIClient.deleteFavorite(1309228568874872832l);
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(404, actualStatusCode);
    }

    /**
     * This test verifies that we can get a list of followers of a particular user using "screen_name" parameter.
     */
    @Test
    public void testGetFollowersIDsUsingScreenNameParameter() {
        ValidatableResponse response = this.tweetAPIClient.getFollowersIDsUsingScreenNameParameter("boonaamohammed");
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(200, actualStatusCode);
    }

    /**
     * This test verifies that we can get a list of followers of a particular user using "id" parameter.
     */
    @Test
    public void testGetFollowersIDsWithInvalidUserName() {
        ValidatableResponse response = this.tweetAPIClient.getFollowersIDsUsingInvalidParameter("boonaamohammed");
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(200, actualStatusCode);
    }

    /**
     * This test verifies that we can get a list of followers of a particular user using "id" parameter.
     */
    @Test
    public void testGetFollowersIDsUsingInvalidEndpoint() {
        ValidatableResponse response = this.tweetAPIClient.getFollowersIDsUsingInvalidEndpoint("boonaamohammed");
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(401, actualStatusCode);
    }

    /**
     * This method tests that user can return the most recent Tweets authored by the authenticating user that have
     * been retweeted by others.
     */
    @Test
    public void testGetReTweetsOfMe() {
        ValidatableResponse response = this.tweetAPIClient.getReTweetsOfMe();
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(200, actualStatusCode);
    }

    /**
     * This method tests that user cannot return the most recent Tweets authored by the un-authenticating user that have
     * been retweeted by others.
     */
    @Test
    public void testGetReTweetsOfMeWithoutAuthentication() {
        ValidatableResponse response = this.tweetAPIClient.getReTweetsOfMeWithoutAuthentication();
        int actualStatusCode = response.extract().statusCode();
        Assert.assertEquals(400, actualStatusCode);
    }

    /**
     * This test verifies that the user can returns the 20 most recent mentions
     * (Tweets containing a users's @screen_name) for the authenticating user.
     */
    @Test
    public void testGetMentionsTimeline() {
        ValidatableResponse response = this.tweetAPIClient.getMentionsTimeline();
        String actualStatusCode = response.extract().statusLine();
        Assert.assertEquals("HTTP/1.1 200 OK", actualStatusCode);
    }

    /**
     * This test verifies that it returns a list of all Lists of the authenticating user subscribes to,
     * including their own.
     */
    @Test
    public void testGetMyAListOfMyList() {
        ValidatableResponse response = this.tweetAPIClient.getMyAListOfMyList();
        String actualStatusCode = response.extract().statusLine();
        Assert.assertEquals("HTTP/1.1 200 OK", actualStatusCode);
    }

    /**
     * This test verifies that user is authenticated.
     *
     * @return
     */
    @Test
    public void tesVerifyAccountCredentials() {
        String nameOfUser = "Shakir";
        ValidatableResponse response = this.tweetAPIClient.verifyAccountCredentials();
        response.statusCode(200);
        String actualNameOfUser = response.extract().body().path("name");
        Assert.assertEquals(nameOfUser, actualNameOfUser);
    }

    /**
     * This test verifies that user is able to returns a map of the available size variations of the
     * specified user's profile banner.
     */
    @Test
    public void testGetUsersProfileBanner() {
        ValidatableResponse response = this.tweetAPIClient.getUsersProfileBanner("KhabibArmy");
        String actualStatusCode = response.extract().statusLine();
        Assert.assertEquals("HTTP/1.1 200 OK", actualStatusCode);
    }

    /**
     * This test verifies that user is able to create a new saved search for the authenticated user.
     * A user may only have 25 saved searches.
     */
    @Test
    public void testCreateSavedSearches() {
        String search = "Dirilis Ertugrul"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createSavedSearches(search);
        response.statusCode(200);
        String actualSearchName = response.extract().body().path("name");
        Assert.assertEquals(search, actualSearchName);
    }

    /**
     * This method verifies that user cannot save same search twice.
     */
    @Test
    public void testUserCanNotSaveSameSearchTwice() {
        // User send a search
        String search = "Canada"+ UUID.randomUUID().toString();
        ValidatableResponse response = this.tweetAPIClient.createSavedSearches(search);
        response.statusCode(200);
        System.out.println(response.extract().body().asString());
        String actualSearch = response.extract().body().path("name");
        Assert.assertEquals(search, actualSearch);
        // User send the same search again
        response = this.tweetAPIClient.createSavedSearches(search);
        // Verify that the search was unsuccessful
        response.statusCode(403);
        //System.out.println(response.extract().body().asString());
        String expectedMessage = "There was an error creating your search.";
        String actualMessage = response.extract().body().path("error");
        Assert.assertEquals(actualMessage, expectedMessage);
        Assert.assertNotSame("200", 403);
    }

}
