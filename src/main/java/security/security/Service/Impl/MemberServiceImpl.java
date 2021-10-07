package security.security.Service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import security.security.Mapper.MemberMapper;
import security.security.Service.MemberService;
import security.security.Vo.MemberVo;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    @Autowired
    MemberMapper memberMapper;

    @Override
    public MemberVo findById(String id) {
        return memberMapper.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        System.out.println("hello!");

        MemberVo memberVo = memberMapper.findById(id);
        if (memberVo == null) {
            throw new UsernameNotFoundException(id);
        }

        return User.builder()
                .username(memberVo.getId())
                .password(memberVo.getPassword())
                .roles("ADMIN")
                .build();
    }


    @Override
    public List<MemberVo> getList() {
        return memberMapper.getList();
    }
}