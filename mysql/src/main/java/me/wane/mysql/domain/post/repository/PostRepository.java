package me.wane.mysql.domain.post.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.dto.DailyPostCount;
import me.wane.mysql.domain.post.dto.DailyPostCountRequest;
import me.wane.mysql.domain.post.entity.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "Post";
  private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet resultSet, int rowNum)
      -> new DailyPostCount(
      resultSet.getLong("memberId"),
      resultSet.getObject("createdDate", LocalDate.class)
      , resultSet.getLong("count"));


  public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest request) {
    String sql = String.format("""
        SELECT createdDate, memberId, count(id) as count
              FROM %s
              WHERE memberId = :memberId AND createdDate BETWEEN :firstDate AND :lastDate
              GROUP BY createdDate ,memberId
        """, TABLE);
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
        request);
    return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
  }

  public Post save(Post post) {
    if (post.getId() == null) {
      return insert(post);
    }

    throw new UnsupportedOperationException("Post는 갱신되지 않습니다.");
  }

  public void bulkInsert(List<Post> posts) {
    String sql = String.format("INSERT INTO %s (memberId, contents, createdDate, createdAt) VALUES (:memberId, :contents, :createdDate , :createdAt)", TABLE);

    SqlParameterSource[] params = posts
        .stream()
        .map(BeanPropertySqlParameterSource::new)
        .toArray(SqlParameterSource[]::new);
    namedParameterJdbcTemplate.batchUpdate(sql, params);
  }

  private Post insert(Post post) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
        namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
        post);
    long id = jdbcInsert.executeAndReturnKey(params).longValue();

    return Post.builder()
        .id(id)
        .memberId(post.getMemberId())
        .contents(post.getContents())
        .createdDate(post.getCreatedDate())
        .createdAt(post.getCreatedAt())
        .build();
  }

}
