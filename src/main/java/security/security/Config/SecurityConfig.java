package security.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import security.security.Service.Impl.MemberServiceImpl;
import security.security.Service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberServiceImpl memberService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/", "/login", "/css/**").permitAll()
//                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("MEMBER")
//                    .anyRequest().permitAll()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/login")
//                .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/user/login/result")
//                .successHandler(new MyLoginSuccessHandler())
                .permitAll()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
                .deleteCookies("remember-me")
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling()
                .accessDeniedPage("/user/denied");
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));


//        http.formLogin()
//                .loginPage("/login") // 로그인페이지 url
////                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/") // 로그인성공 url
//                .usernameParameter("id") // 로그인 시 사용할 파라미터 이름정하기
//                .failureUrl("/") // 로그인 실패 url
//                .and() //
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 url
//                .logoutSuccessUrl("/"); // 로그아웃 성공 시 이동할 url

        // 접근권한 설정하기
//        http.authorizeRequests()
                // 아래 경로는 로그인안해도 접근가능하도록 설정
//                .mvcMatchers("/", "/login", "/admin", "/**").permitAll()
                // 아래 경로는 ADMIN이어야 접근 가능
//                .mvcMatchers("/admin**").hasRole("ADMIN")
                // 그 외의 경로는 로그인해야한다
//                .anyRequest().authenticated();

        // 인증되지 않은 사용자가 리소스에 접근했을때 수행되는 핸들러
//        http.exceptionHandling()
//                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    // 아래경로는 인증 무시한다
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // 대충 인증에 사용하는 객체를 memberService를 쓰겠고 비밀번호 암호화를 위해 passwordEncoder를 쓰겠다는 말
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }




}