package itm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Two {
    private static final String TEST_URL = "https://jsonplaceholder.typicode.com";
    public static String getPosts(String userId) throws IOException {
        URL url = new URL(TEST_URL + "/users/" + userId + "/posts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET Posts response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("GET Posts: request not worked");
            return "";
        }
    }

    public static String getMaxPost(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        Stream<JsonNode> stream = StreamSupport.stream(jsonNode.spliterator(), false);
        return String.valueOf(
                stream
                .max((a, b) -> a.get("id").asInt() - b.get("id").asInt())
                .get().get("id").asInt());
    }

    public static String getComments(String postId) throws IOException {
        URL url = new URL(TEST_URL + "/posts/" + postId + "/comments");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET Comments response code: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            System.out.println("GET Comments: request not worked");
            return "";
        }
    }

    public static void dumpComments(String userId) throws IOException {
        String maxPostId = getMaxPost(getPosts(userId));
        String fileName = "user-" + userId + "-post-" + maxPostId + "-comments.json";
        String text = getComments(maxPostId);
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] buffer = text.getBytes();
            fileOutputStream.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
