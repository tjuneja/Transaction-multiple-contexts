package Spring;


import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional({OnSeviceCondition.class})
public @interface ConditionalOnService {


    String[] name() default {};

    SearchStrategy search() default SearchStrategy.ALL;
}
