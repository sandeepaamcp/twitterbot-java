import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class TwitterBot {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        // gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        Twitter unauthenticatedTwitter = new TwitterFactory().getInstance();
        Paging paging = new Paging(1, 100);
        Status tweetResult=null;
        int x = 0;
        try {
            //add the target user id to see if the twitter user has
            //posted a public tweet of a number. Don't need to add the initial @ symbol in the id
            //NOTE: For testing, you can use your own twitter account.
            List<Status> statuses = unauthenticatedTwitter.getUserTimeline("TARGET_USER_ID_WITHOUT_@",paging);

            for (Status status : statuses) {
                System.out.println(status.getText());
                try{
                    x=Integer.parseInt(status.getText());
                    tweetResult = status;
                    break;
                }
                catch(NumberFormatException e){
                    continue;
                }
            }
            if(tweetResult!=null){
                // reply to that tweet
                System.out.println("got the number "+x);
                StatusUpdate statusUpdate = new StatusUpdate(".@"
                        + tweetResult.getUser().getScreenName() + " " + getPrimes(x) );
                statusUpdate.inReplyToStatusId(tweetResult.getId());
                Status status = twitter.updateStatus(statusUpdate);
            }


        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    //private methods for prime numbers generation
    private static String getPrimes(int n){
        ArrayList<String> list = new ArrayList<>();
        for(int loop=2; loop<=n; loop++)
        {
            if(isPrime(loop))
                list.add(Integer.toString(loop));
        }
        return String.join(",",list);
    }

    private static boolean isPrime(int num)
    {
        boolean flag=true;
        for(int i=2; i<=(num/2);i++)
        {
            if(num%i==0)
            {
                flag=false;
                break;
            }
        }
        return flag;
    }
}
