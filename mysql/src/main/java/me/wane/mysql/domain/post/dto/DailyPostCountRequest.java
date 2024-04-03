package me.wane.mysql.domain.post.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record DailyPostCountRequest(
    Long memberId,
    @DateTimeFormat(iso = ISO.DATE)
    LocalDate firstDate,
    @DateTimeFormat(iso = ISO.DATE)
    LocalDate lastDate
) {

}
