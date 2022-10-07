package app.web.mohajeri.portfolio.bookstore.DTO;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
