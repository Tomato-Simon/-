package group.oneonetwo.hotelintelligencesystem.modules.dept.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import group.oneonetwo.hotelintelligencesystem.exception.CommonException;
import group.oneonetwo.hotelintelligencesystem.exception.SavaException;
import group.oneonetwo.hotelintelligencesystem.modules.dept.dao.DeptMapper;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.po.DeptPO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.model.vo.DeptVO;
import group.oneonetwo.hotelintelligencesystem.modules.dept.service.IDeptService;
import group.oneonetwo.hotelintelligencesystem.modules.user.model.vo.UserVO;
import group.oneonetwo.hotelintelligencesystem.modules.user.service.impl.UserServiceImpl;
import group.oneonetwo.hotelintelligencesystem.tools.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author 文
 * @description 部门服务层实现类
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class DeptServiceImpl implements IDeptService {

    @Autowired
    DeptMapper deptMapper;

    @Autowired
    UserServiceImpl userService;

    @Override
    public DeptPO add(DeptVO deptVO) {
        if (deptVO == null) {
            throw new SavaException("插入用户失败：部门实体为空");
        }
        DeptPO deptPO = new DeptPO();
        BeanUtils.copyProperties(deptVO,deptPO);
        int insert = deptMapper.insert(deptPO);
        if (insert > 0) {
            return deptPO;
        }
        throw new SavaException("插入用户失败");
    }

    @Override
    public DeptVO selectOneByIdReturnVO(String id) {
        if (id == null) {
            throw new CommonException(501,"参数为空");
        }
        DeptPO deptPO = deptMapper.selectById(id);
        DeptVO deptVO = new DeptVO();
        if (deptPO != null) {
            BeanUtils.copyProperties(deptPO,deptVO);
        }
        return deptVO;
    }

    @Override
    public DeptPO save(DeptVO deptVO) {
        if (deptVO == null) {
            throw new CommonException(501,"dept实体为空");
        }
        DeptVO check = selectOneByIdReturnVO(deptVO.getId());
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + deptVO.getId() + "'的数据");
        }
        DeptPO deptPO = new DeptPO();
        BeanUtils.copyProperties(deptVO,deptPO);
        int save = deptMapper.updateById(deptPO);
        if (save > 0) {
            return deptMapper.selectById(deptPO.getId());
        }
        throw new SavaException("更改部门失败");
    }

    @Override
    public Integer deleteById(String id) {
        DeptVO check = selectOneByIdReturnVO(id);
        if (check == null) {
            throw new CommonException(4004,"找不到id为'" + id + "'的数据");
        }
        int i = deptMapper.deleteById(id);
        return i;
    }

    @Override
    public Reply batchAdd(List<DeptVO> deptVOS) {

        Iterator<DeptVO> iterator = deptVOS.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return Reply.success();
    }

    @Override
    public Page<DeptVO> getPage(DeptVO deptVO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        String authority = null;
        if (iterator.hasNext()) {
            authority = iterator.next().getAuthority();
        }
        if (authority == null) {
            throw new CommonException(401,"无权限");
        }
        switch (authority) {
            case "admin":break;
            case "hotel_admin":
                UserVO userVO = userService.selectOneByIdReturnVO(authentication.getName());
                if (userVO == null) {
                    throw new CommonException(401,"无权限");
                }
                deptVO.setpId(userVO.getDept());
                break;
            default:
                throw new CommonException(401,"无权限");
        }
        Page<DeptVO> page = new Page<>(deptVO.getPage().getPage(),deptVO.getPage().getSize());
        return deptMapper.getPage(page,deptVO);
    }


    @Override
    public DeptPO selectOneById(String id) {
        DeptPO deptPO = deptMapper.selectById(id);
        return deptPO;
    }

}
