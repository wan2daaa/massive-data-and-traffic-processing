package me.wane.mysql.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.entity.MemberNicknameHistory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class MemberNicknameHistoryRepository {


  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "MemberNicknameHistory";

  private static final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory.builder()
      .id(resultSet.getLong("id"))
      .memberId(resultSet.getLong("memberId"))
      .nickname(resultSet.getString("nickname"))
      .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
      .build();

  public Optional<MemberNicknameHistory> findById(Long id) {

    String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("id", id);



    return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper));
    // Optional.ofNullable(T value) 는 value 가 null 이 아니면 Optional.of(value) 를 반환하고, null 이면 Optional.empty() 를 반환한다.
  }

  public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
    String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
    MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId);
    return namedParameterJdbcTemplate.query(sql, params, rowMapper);

  }

  public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
    /**
     * memberNicknameHistory id 를 보고 갱신 또는 삽입을 정함
     * 반환값은 memberNicknameHistory 에 id를 담아서 반환
     */
    if (memberNicknameHistory.getId() == null) {
      return insert(memberNicknameHistory);
    }
    throw new UnsupportedOperationException("MemberNicknameHistory 는 갱신을 지원하지 않습니다.");
  }

  private MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(
        namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");

    SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
    Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

    return MemberNicknameHistory.builder()
        .id(id)
        .memberId(memberNicknameHistory.getMemberId())
        .nickname(memberNicknameHistory.getNickname())
        .createdAt(memberNicknameHistory.getCreatedAt())
        .build();
  }

}
