package itm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Three {
    private static final String TEST_URL = "https://jsonplaceholder.typicode.com";
    public static String getTodos(String userId) throws IOException {
        URL url = new URL(TEST_URL + "/users/" + userId + "/todos");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET Todos response code: " + responseCode);
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
            System.out.println("GET Todos: request not worked");
            return "";
        }
    }

    public static <T> void getTodosFiltered(String userId, String filterBy, T value) throws IOException {
        String json = getTodos(userId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        Stream<JsonNode> stream = StreamSupport.stream(jsonNode.spliterator(), false);
        stream
                .filter(todo -> todo.get(filterBy).asText().equals(String.valueOf(value)))
                .forEach(System.out::println);
    }

}
