package victor_dvoryadkin.space_photo_orion8v_bot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 * SpacePhotoOrion8VBot
 * @version v1.0
 * @author Victor Dvoryadkin
 * 24.03.2024
 */
public class Utils {

    public static String getUrl(String nasaUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(nasaUrl));
            NasaObject nasaObject = objectMapper.readValue(httpResponse.getEntity().getContent(), NasaObject.class);
            return nasaObject.url;
        } catch (IOException ex) {
            System.out.println("Произошла ошибка.");
        }
        return "";
    }

}
