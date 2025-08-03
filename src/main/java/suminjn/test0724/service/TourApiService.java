package suminjn.test0724.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import suminjn.test0724.entity.Content;
import suminjn.test0724.repository.ContentRepository;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ContentRepository contentRepository;

    @Value("${tour-api-service-key}")
    private String SERVICE_KEY;

    public void syncTourContents() {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://apis.data.go.kr/B551011/KorService2/areaBasedList2")
                .queryParam("serviceKey", SERVICE_KEY) // 디코딩된 키 사용!
                .queryParam("MobileApp", "test")
                .queryParam("MobileOS", "ETC")
                .queryParam("_type", "json")
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .build(true)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        JsonNode items = response.getBody().path("response").path("body").path("items").path("item");

        for (JsonNode item : items) {
            Content content = new Content();
            content.setContentId(item.path("contentid").asLong());
            content.setTitle(item.path("title").asText());
            content.setZipcode(parseInt(item.path("zipcode").asText()));
            content.setTel(item.path("tel").asText());
            content.setContentTypeId(item.path("contenttypeid").asText());
            content.setMapX(parseDouble(item.path("mapx").asText()));
            content.setMapY(parseDouble(item.path("mapy").asText()));
            content.setAddr(item.path("addr1").asText());
            content.setFirstimage(item.path("firstimage").asText());
            content.setFirstimage2(item.path("firstimage2").asText());
            content.setAreacode(parseInt(item.path("areacode").asText()));
            content.setSigungucode(parseInt(item.path("sigungucode").asText()));
            content.setCreatedAt(parseDateTime(item.path("createdtime").asText()));
            content.setUpdatedAt(parseDateTime(item.path("modifiedtime").asText()));

            contentRepository.save(content);
        }
    }

    private Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return null;
        }
    }

    private Double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDateTime parseDateTime(String s) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            return LocalDateTime.parse(s, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
