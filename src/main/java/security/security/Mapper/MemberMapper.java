package security.security.Mapper;

import org.apache.ibatis.annotations.Mapper;
import security.security.Vo.MemberVo;

import java.util.List;

@Mapper
public interface MemberMapper {

    public MemberVo findById(String id);

    public List<MemberVo> getList();

}
