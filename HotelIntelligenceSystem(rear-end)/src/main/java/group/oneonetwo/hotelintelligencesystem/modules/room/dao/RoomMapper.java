package group.oneonetwo.hotelintelligencesystem.modules.room.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.po.RoomPO;
import group.oneonetwo.hotelintelligencesystem.modules.room.model.vo.RoomVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sun.security.krb5.internal.tools.Klist;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper extends BaseMapper<RoomPO> {
    public List<RoomVO> getAllList(@Param("vo") RoomVO roomVO);
    public List<RoomVO> getList(@Param("vo") RoomVO roomVO);

}
