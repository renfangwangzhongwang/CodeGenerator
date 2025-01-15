import com.easyjava.RunDemoApplication;
import com.easyjava.entity.po.UserInfo;
import com.easyjava.entity.query.UserInfoQuery;
import com.easyjava.mapper.UserInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

@SpringBootTest(classes = RunDemoApplication.class)
public class MapperTest {
    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Test
    public void selectList(){
        UserInfoQuery query = new UserInfoQuery();
        query.setPersonIntroductionFuzzy("妓院");

        List<UserInfo> datalist = userInfoMapper.selectList(query);
        for(UserInfo userInfo:datalist){
            System.out.println(userInfo);
        }

        Long count = userInfoMapper.selectCount(query);
        System.out.println(count);
    }

    @Test
    public void insert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("6666@qq.com");
        userInfo.setUserId(123);
        this.userInfoMapper.insert(userInfo);
    }

    @Test
    public void insertOrUpdate(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("676665557@qq.com");
        userInfo.setUserId(15);
        userInfo.setSchool("whdxx");
        this.userInfoMapper.insertOrUpdate(userInfo);
    }

    @Test
    public void insertBatch(){
        List<UserInfo> userInfoList = new ArrayList();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(15);
        userInfoList.add(userInfo);
        this.userInfoMapper.insertBatch(userInfoList);
    }

    @Test
    public void insertOrUpdateBatch(){
        List<UserInfo> userInfoList = new ArrayList();
        UserInfo userInfo0 = new UserInfo();
        userInfo0.setEmail("551557@qq.com");
        userInfo0.setUserId(20);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setEmail("4625655557@qq.com");
        userInfo1.setSchool("x22xx");
        userInfo1.setUserId(21);
        userInfoList.add(userInfo1);
        userInfoList.add(userInfo0);
        this.userInfoMapper.insertOrUpdateBatch(userInfoList);
    }

    @Test
    public void selectByKey(){
        UserInfo userInfo0 = this.userInfoMapper.selectByUserId(12);
        UserInfo userInfo1 = this.userInfoMapper.selectByEmail("000@qq.com");
        UserInfo userInfo2 = this.userInfoMapper.selectByNickName("lwq");
        System.out.println(userInfo0);
        System.out.println(userInfo1);
        System.out.println(userInfo2);
    }

    @Test
    public void updateByKey(){
        UserInfo userInfo0 = new UserInfo();
        userInfo0.setEmail("551665557@qq.com");
        userInfo0.setSchool("0");
        userInfo0.setUserId(100);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setEmail("4625655557@qq.com");
        userInfo1.setSchool("1");
        userInfo1.setUserId(101);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setEmail("4625557@qq.com");
        userInfo2.setSchool("2");
        userInfo2.setUserId(102);
        Long u0 = this.userInfoMapper.updateByUserId(userInfo0,13);
        Long u1 = this.userInfoMapper.updateByEmail(userInfo1,"666@qq.com");
        Long u2 = this.userInfoMapper.updateByNickName(userInfo2,"lwq");
        System.out.println(u0);
        System.out.println(u1);
        System.out.println(u2);
    }

    @Test
    public void deleteByKey(){
        Long userInfo0 = this.userInfoMapper.deleteByUserId(12);
        Long userInfo1 = this.userInfoMapper.deleteByEmail("111");
        Long userInfo2 = this.userInfoMapper.deleteByNickName("tsh");
        System.out.println(userInfo0);
        System.out.println(userInfo1);
        System.out.println(userInfo2);
    }

}
