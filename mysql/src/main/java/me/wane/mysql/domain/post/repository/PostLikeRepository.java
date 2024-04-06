package me.wane.mysql.domain.post.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.entity.PostLike;
import me.wane.mysql.domain.post.entity.TimeLine;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostLikeRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "PostLike";

  private static final RowMapper<PostLike> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> PostLike.builder()
      .id(resultSet.getLong("id"))
      .memberId(resultSet.getLong("memberId"))
      .postId(resultSet.getLong("postId"))
      .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
      .build();

  public Long getCount(Long postId) {
    String sql = String.format("""
        SELECT count(id)
        FROM %s
        WHERE postId = :postId
        """, TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("postId", postId);
    return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
  }


  public PostLike save(PostLike postLike) {
    if (postLike.getId() == null) {
      return insert(postLike);
    }

    throw new UnsupportedOperationException("TimeLine는 갱신을 지원하지 않습니다.");
  }


  private PostLike insert(PostLike postLike) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
        namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
        postLike);
    long id = jdbcInsert.executeAndReturnKey(params).longValue();

    return PostLike.builder()
        .id(id)
        .memberId(postLike.getMemberId())
        .postId(postLike.getPostId())
        .createdAt(postLike.getCreatedAt())
        .build();
  }



}
