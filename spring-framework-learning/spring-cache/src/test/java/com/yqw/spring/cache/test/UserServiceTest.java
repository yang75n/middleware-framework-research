package com.yqw.spring.cache.test;

import com.yqw.spring.cache.User;
import com.yqw.spring.cache.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * https://www.ibm.com/developerworks/cn/opensource/os-cn-spring-cache/index.html
 *
 * @Cacheable、@CachePut、@CacheEvict 注释介绍:
 * 通过例子，我们可以看到 spring cache 主要使用两个注释标签，即 @Cacheable、@CachePut 和 @CacheEvict，我们总结一下其作用和配置方法。
 * <p>
 * 表 1. @Cacheable 作用和配置方法
 * @Cacheable 的作用    主要针对方法配置，能够根据方法的请求参数对其结果进行缓存
 * @Cacheable 主要的参数
 * value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：
 * @Cacheable(value=”mycache”) 或者
 * @Cacheable(value={”cache1”,”cache2”} key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：
 * @Cacheable(value=”testcache”,key=”#userName”) condition    缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	例如：
 * @Cacheable(value=”testcache”,condition=”#userName.length()>2”) 表 2. @CachePut 作用和配置方法
 * 表 2. @CachePut 的作用    主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用
 * @CachePut 主要的参数
 * value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：
 * @Cacheable(value=”mycache”) 或者
 * @Cacheable(value={”cache1”,”cache2”} key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：
 * @Cacheable(value=”testcache”,key=”#userName”) condition    缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存	例如：
 * @Cacheable(value=”testcache”,condition=”#userName.length()>2”) 表 3. @CacheEvict 作用和配置方法
 * 表 3.@CachEvict 的作用    主要针对方法配置，能够根据一定的条件对缓存进行清空
 * @CacheEvict 主要的参数
 * value	缓存的名称，在 spring 配置文件中定义，必须指定至少一个	例如：
 * @CachEvict(value=”mycache”) 或者
 * @CachEvict(value={”cache1”,”cache2”} key	缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合	例如：
 * @CachEvict(value=”testcache”,key=”#userName”) condition    缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才清空缓存	例如：
 * @CachEvict(value=”testcache”, condition=”#userName.length()>2”)
 * allEntries	是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存	例如：
 * @CachEvict(value=”testcache”,allEntries=true) beforeInvocation    是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存	例如：
 * @CachEvict(value=”testcache”，beforeInvocation=true) Created by iQiwen on 2019/5/21.
 * <p>
 * <p>
 * <p>
 * 基本原理
 * 和 spring 的事务管理类似，spring cache 的关键原理就是 spring AOP，通过 spring AOP，其实现了在方法调用前、调用后获取方法的入参和返回值，进而实现了缓存的逻辑。
 * <p>
 */
public class UserServiceTest {
    /**
     * 1. 测试spring-cache的@Cacheable注解，不用写代码让既有代码支持缓存
     */
    @Test
    public void testSpringCache() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-cache.xml");// 加载 spring 配置文件

        UserService s = (UserService) context.getBean("userService");
        // 第一次查询，应该走数据库
        System.out.println("first query...");
        s.getUserByName("qiwen");
        // 第二次查询，应该不查数据库，直接返回缓存的值
        System.out.println("second query...");
        s.getUserByName("qiwen");
        System.out.println("third query...");
        s.getUserByName("qiwen");
    }


    /**
     * 2. 测试spring-cache的@CacheEvict注解，清空缓存，以保证缓存数据的可靠性。
     */
    @Test
    public void testCacheByUpdate() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-cache.xml");// 加载 spring 配置文件

        UserService s = (UserService) context.getBean("userService");
        // 第一次查询，应该走数据库
        System.out.println("first query...");
        s.getUserByName("qiwen");
        // 第二次查询，应该不查数据库，直接返回缓存的值
        System.out.println("second query...");
        s.getUserByName("qiwen");
        System.out.println();

        System.out.println("start testing clear cache...");    // 更新某个记录的缓存，首先构造两个账号记录，然后记录到缓存中
        User account1 = s.getUserByName("somebody1");
        User account2 = s.getUserByName("somebody2");
        // 开始更新其中一个
        account1.setId(1212);
        s.updateUser(account1);
        s.getUserByName("somebody1");// 因为被更新了，所以会查询数据库
        s.getUserByName("somebody2");// 没有更新过，应该走缓存
        s.getUserByName("somebody1");// 再次查询，应该走缓存
        // 更新所有缓存
        s.reload();
        s.getUserByName("somebody1");// 应该会查询数据库
        s.getUserByName("somebody2");// 应该会查询数据库
        s.getUserByName("somebody1");// 应该走缓存
        s.getUserByName("somebody2");// 应该走缓存

    }


    /**
     * 2.1按照条件操作缓存
     * <p>
     * 前面介绍的缓存方法，没有任何条件，即所有对 userService 对象的 getUserByName 方法的调用都会起动缓存效果，不管参数是什么值，如果有一个需求，就是只有账号名称的长度小于等于 4 的情况下，才做缓存，大于 4 的不使用缓存，那怎么实现呢？
     * <p>
     * Spring cache 提供了一个很好的方法，那就是基于 SpEL 表达式的 condition 定义，这个 condition 是 @Cacheable 注释的一个属性，
     */
    @Test
    public void testCacheWithCondition() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-cache.xml");// 加载 spring 配置文件

        UserService s = (UserService) context.getBean("userService");
        // 第一次查询，应该走数据库
        System.out.println("first query...");
        s.getAccountByNameWithCondition("somebody");// 长度大于 4，不会被缓存
        s.getAccountByNameWithCondition("sbd");// 长度小于 4，会被缓存
        System.out.println("second query...");

        s.getAccountByNameWithCondition("somebody");// 还是查询数据库
        s.getAccountByNameWithCondition("sbd");// 会从缓存返回
    }

    /**
     * 3. 测试spring-cache的@Cacheable注解key的生成，如果有多个参数，如何进行 key 的组合。
     */
    @Test
    public void testCacheWithComsopseKey() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-cache.xml");// 加载 spring 配置文件

        UserService s = (UserService) context.getBean("userService");
        s.getAccount("somebody", "123456");// 应该查询数据库
        s.getAccount("somebody", "123456");// 应该走缓存
        s.getAccount("somebody", "123456");// 应该走缓存
        s.getAccount("somebody", "654321");// 应该查询数据库
        s.getAccount("somebody", "654321");// 应该走缓存
    }

    /**
     * 4. 测试spring-cache的@CachePut注解，可以保证更新方法被执行，且结果一定会被缓存。
     */
    @Test
    public void testCacheOfAnnoWithUpdateCache() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-cache.xml");// 加载 spring 配置文件

        UserService s = (UserService) context.getBean("userService");

        User account = s.getUserByName("someone");
        account.setId(123);
        s.updateAccountAndCache(account);
        account.setId(321);
        s.updateAccountAndCache(account);
        account = s.getUserByName("someone");
        System.out.println(account.getId());
    }


}
