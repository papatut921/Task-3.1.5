import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import model.User;

import java.util.Arrays;

public class restTemplateApiUse {
    public static void main(String[] args) {

        final String URL = "http://94.198.50.185:7081/api/users";
        String result = "";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>("", headers);
        ResponseEntity responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

        System.out.println("Response entity header: " + responseEntity.getHeaders() + "\n");
        String cookie  = String.valueOf(responseEntity.getHeaders().get("Set-Cookie")).replaceAll("^.|.$", "");
        System.out.println("Cookie: " + cookie + "\n");

        headers.set("Cookie", cookie);
        User user = new User(3L, "James", "Brown", (byte) 55);
        System.out.println(user);

        HttpEntity<User> entityUser = new HttpEntity<>(user, headers);
        responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entityUser, String.class);

        result += responseEntity.getBody();

        System.out.println("Code 1: " + result + "\n");

        user.setName("Thomas");
        user.setLastName("Shelby");
        System.out.println(user);

        entityUser = new HttpEntity<>(user, headers);
        responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entityUser, String.class);

        result += responseEntity.getBody();
        System.out.println("Code 2: " + result + "\n");

        responseEntity = restTemplate.exchange(URL + "/" + user.getId(), HttpMethod.DELETE, entityUser, String.class);
        System.out.println("Code 3: " + result + "\n");

        result += responseEntity.getBody();
        System.out.println("Result code: " + result);
    }
}
