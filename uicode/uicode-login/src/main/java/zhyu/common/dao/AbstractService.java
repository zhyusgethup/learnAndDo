package zhyu.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractService {
    @Autowired
    protected SqlSessionTemplate template;
}
