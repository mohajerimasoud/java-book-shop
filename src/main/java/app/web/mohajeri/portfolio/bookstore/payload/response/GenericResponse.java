package app.web.mohajeri.portfolio.bookstore.payload.response;

import lombok.Data;

@Data
public class GenericResponse<T> {
    private T payload;
    private int responseCode;

}
