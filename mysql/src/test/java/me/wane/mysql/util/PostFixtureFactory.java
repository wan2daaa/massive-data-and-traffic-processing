package me.wane.mysql.util;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.ofType;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import me.wane.mysql.domain.post.entity.Post;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

public class PostFixtureFactory {

  public static EasyRandom get(Long memberId, LocalDate firstDate, LocalDate lastDate) {

    Predicate<Field> idPredicate = FieldPredicates.named("id")
        .and(ofType(Long.class))
        .and(inClass(Post.class));

    Predicate<Field> memberIdPredicate = FieldPredicates.named("memberId")
        .and(ofType(Long.class))
        .and(inClass(Post.class));

    EasyRandomParameters param = new EasyRandomParameters()
        .excludeField(idPredicate)
        .dateRange(firstDate, lastDate)
        .randomize(memberIdPredicate, () -> memberId);

    return new EasyRandom(param);
  }

}
