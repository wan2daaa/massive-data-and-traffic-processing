package me.wane.mysql.domain.post.dto;

public record PostCommand(
    Long memberId,
    String contents
) {

}
