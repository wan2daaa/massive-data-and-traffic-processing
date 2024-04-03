package me.wane.mysql.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.wane.mysql.domain.member.entity.Member;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class MemberRepository {


  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  private static final String TABLE = "Member";

  public Optional<Member> findById(Long id) {
    /**
     * select *
     * from Member
     * where id = :id
     */
    String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);

    MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("id", id);

    BeanPropertyRowMapper<Member> rowMappers = BeanPropertyRowMapper.newInstance(Member.class); // 이걸 사용하려면 setter를 다 열어줘야함


    RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
        .id(resultSet.getLong("id"))
        .email(resultSet.getString("email"))
        .nickname(resultSet.getString("nickname"))
        .birthday(resultSet.getObject("birthday", LocalDate.class))
        .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
        .build();

     return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper));
     // Optional.ofNullable(T value) 는 value 가 null 이 아니면 Optional.of(value) 를 반환하고, null 이면 Optional.empty() 를 반환한다.

  }

  public List<Member> findAllByIdIn(List<Long> ids) {
    /**
     * select *
     * from Member
     * where id in (:ids)
     */

    if (ids.isEmpty()) {
      return List.of();
    }

    String sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE);

    MapSqlParameterSource params = new MapSqlParameterSource().addValue("ids", ids);

    RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
        .id(resultSet.getLong("id"))
        .email(resultSet.getString("email"))
        .nickname(resultSet.getString("nickname"))
        .birthday(resultSet.getObject("birthday", LocalDate.class))
        .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
        .build();

    return namedParameterJdbcTemplate.query(sql, params, rowMapper);

  }

  public Member save(Member member) {
    /**
     * member id 를 보고 갱신 또는 삽입을 정함
     * 반환값은 member 에 id를 담아서 반환
     */
    if (member.getId() == null) {
      return insert(member);
    }
    return update(member);
  }

  private Member insert(Member member) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(
        namedParameterJdbcTemplate.getJdbcTemplate())
        .withTableName(TABLE)
        .usingGeneratedKeyColumns("id");
    //BeanPropertySqlParameterSource는 객체를 받아서 jpql 에서 :컬럼 에 해당하는 부분에 객체의 프로퍼티 매핑되어 값이 삽입되는 듯
    SqlParameterSource params = new BeanPropertySqlParameterSource(member);
    Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

    return Member.builder()
        .id(id)
        .email(member.getEmail())
        .nickname(member.getNickname())
        .birthday(member.getBirthday())
        .createdAt(member.getCreatedAt())
        .build();
  }


  private Member update(Member member) {
    String sql = "UPDATE %s set email = :email, nickname = :nickname, birthday = :birthday where id = :id".formatted(TABLE);
    BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(member);
    int updated = namedParameterJdbcTemplate.update(sql, params);

    if (updated == 0) {
      throw new IllegalArgumentException("존재하지 않는 회원입니다.");
    }

    return member;
  }
}
