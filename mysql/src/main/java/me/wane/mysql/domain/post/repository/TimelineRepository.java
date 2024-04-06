package me.wane.mysql.domain.post.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.post.entity.Post;
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
public class TimelineRepository {

  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "Timeline";

  private static final RowMapper<TimeLine> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> TimeLine.builder()
      .id(resultSet.getLong("id"))
      .memberId(resultSet.getLong("memberId"))
      .postId(resultSet.getLong("postId"))
      .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
      .build();

  public List<TimeLine> findAllByMemberIdOrderByIdDesc(Long memberId, int size) {
    String sql = String.format("""
        SELECT *
        FROM %s
        WHERE memberId = :memberId
        ORDER BY id DESC
        LIMIT :size
        """, TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("memberId", memberId)
        .addValue("size", size);

    return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
  }

  public List<TimeLine> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId,
      int size) {
    String sql = String.format("""
        SELECT *
        FROM %s
        WHERE memberId = :memberId and id < :id
        ORDER BY id DESC
        LIMIT :size
        """, TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("memberId", memberId)
        .addValue("id", id)
        .addValue("size", size);

    return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
  }

  public TimeLine save(TimeLine timeLine) {
    if (timeLine.getId() == null) {
      return insert(timeLine);
    }

    throw new UnsupportedOperationException("TimeLine는 갱신을 지원하지 않습니다.");
  }


  private TimeLine insert(TimeLine timeLine) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(
        namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
        timeLine);
    long id = jdbcInsert.executeAndReturnKey(params).longValue();

    return TimeLine.builder()
        .id(id)
        .memberId(timeLine.getMemberId())
        .postId(timeLine.getPostId())
        .createdAt(timeLine.getCreatedAt())
        .build();
  }

  public void bulkInsert(List<TimeLine> timeLines) {
    String sql = String.format(
        "INSERT INTO %s (memberId, postId, createdAt) VALUES (:memberId, :postId , :createdAt)",
        TABLE);

    SqlParameterSource[] params = timeLines
        .stream()
        .map(BeanPropertySqlParameterSource::new)
        .toArray(SqlParameterSource[]::new);
    namedParameterJdbcTemplate.batchUpdate(sql, params);
  }


}
