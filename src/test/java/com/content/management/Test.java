package com.content.management;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {

    @org.junit.jupiter.api.Test
    public void test()
    {
        String jsonString ="{\n" +
                "       \"pageInfo\": {\n" +
                "             \"pageName\": \"abc\",\n" +
                "             \"pagePic\": \"http://example.com/content.jpg\"\n" +
                "        },\n" +
                "        \"posts\": [\n" +
                "             {\n" +
                "                  \"post_id\": \"123456789012_123456789012\",\n" +
                "                  \"actor_id\": \"1234567890\",\n" +
                "                  \"picOfPersonWhoPosted\": \"http://example.com/photo.jpg\",\n" +
                "                  \"nameOfPersonWhoPosted\": \"Jane Doe\",\n" +
                "                  \"message\": \"Sounds cool. Can't wait to see it!\",\n" +
                "                  \"likesCount\": \"2\",\n" +
                "                  \"comments\": [],\n" +
                "                  \"timeOfPost\": \"1234567890\"\n" +
                "             }\n" +
                "        ]\n" +
                "    }";

        JSONObject obj = new JSONObject(jsonString);
        String pageName = obj.getJSONObject("pageInfo").getString("pageName");

        JSONArray arr = obj.getJSONArray("posts"); // notice that `"posts": [...]`
        for (int i = 0; i < arr.length(); i++)
        {
            String post_id = arr.getJSONObject(i).getString("post_id");
            System.out.println("data "+ post_id);
        }
    }
}
