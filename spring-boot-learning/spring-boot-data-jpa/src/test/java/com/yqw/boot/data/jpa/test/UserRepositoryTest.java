package com.yqw.boot.data.jpa.test;


import com.yqw.boot.data.jpa.Application;
import com.yqw.boot.data.jpa.domain.User;
import com.yqw.boot.data.jpa.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryByName userRepositoryByName;

    @Autowired
    private UserRepositoryQueryAnnotation userRepositoryQueryAnnotation;

    @Autowired
    private UserRepositoryCrudRepository userRepositoryCrudRepository;

    @Autowired
    private UserRepositoryPagingAndSorting userRepositoryPagingAndSorting;

    @Autowired
    private UserRepositorySpecification userRepositorySpecification;

    @Test
    public void testSave() {
        User User = new User();
        User.setAddress("上海市");
        User.setAge(24);
        User.setName("王五");
        userRepository.save(User);
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByName() {
        List<User> list = userRepositoryByName.findByName("张三");
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByNameAndAge() {
        List<User> list = userRepositoryByName.findByNameAndAge("张三", 20);
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * Repository--方法名称命名测试
     */
    @Test
    public void testFindByNameLike() {
        List<User> list = userRepositoryByName.findByNameLike("张%");
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    public void testQueryByNameUseHQL() {
        List<User> list = userRepositoryQueryAnnotation.queryByNameUseHQL("张三");
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    public void testQueryByNameUseSQL() {
        List<User> list = userRepositoryQueryAnnotation.queryByNameUseSQL("张三");
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * Repository--@Query测试
     */
    @Test
    @Transactional //@Transactional与@Test 一起使用时 事务是自动回滚的。
    @Rollback(false) //取消自动回滚
    public void testUpdateUserNameById() {
        userRepositoryQueryAnnotation.updateUserNameById("张三三", 1);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositorySave() {
        User user = new User();
        user.setAddress("天津");
        user.setAge(32);
        user.setName("张三丰");
        userRepositoryCrudRepository.save(user);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryUpdate() {
        User user = new User();
        user.setId(4);
        user.setAddress("南京");
        user.setAge(40);
        user.setName("张三丰");
        userRepositoryCrudRepository.save(user);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryFindOne() {
        // User User =userRepositoryCrudRepository.findOne(4);
        //新的api方法
        User User = userRepositoryCrudRepository.findById(4).get();
        System.out.println(User);
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryFindAll() {
        List<User> list = (List<User>) userRepositoryCrudRepository.findAll();
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * CrudRepository测试
     */
    @Test
    public void testCrudRepositoryDeleteById() {
        // userRepositoryCrudRepository.delete(4);
        //新的api方法
        userRepositoryCrudRepository.deleteById(4);
    }

    /**
     * PagingAndSortingRepository   排序测试
     */
    @Test
    public void testPagingAndSortingRepositorySort() {
        //Order 定义排序规则
        Order order = new Order(Direction.DESC, "id");
        //Sort对象封装了排序规则
        Sort sort = new Sort(order);
        List<User> list = (List<User>) userRepositoryPagingAndSorting.findAll(sort);
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * PagingAndSortingRepository   分页测试
     */
    @Test
    public void testPagingAndSortingRepositoryPaging() {
        //Pageable:封装了分页的参数，当前页，每页显示的条数。注意：他的当前页是从0开始。
        //PageRequest(page,size) page:当前页。size:每页显示的条数
        Pageable pageable = new PageRequest(1, 2);
        Page<User> page = userRepositoryPagingAndSorting.findAll(pageable);
        System.out.println("总条数：" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        List<User> list = page.getContent();
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * PagingAndSortingRepository   排序+分页
     */
    @Test
    public void testPagingAndSortingRepositorySortAndPaging() {

        Sort sort = new Sort(new Order(Direction.DESC, "id"));

        Pageable pageable = new PageRequest(1, 2, sort);

        Page<User> page = userRepositoryPagingAndSorting.findAll(pageable);
        System.out.println("总条数：" + page.getTotalElements());
        System.out.println("总页数" + page.getTotalPages());
        List<User> list = page.getContent();
        for (User User : list) {
            System.out.println(User);
        }
    }

    /**
     * JapRepository   排序测试
     */
    @Test
    public void testJpaRepositorySort() {
        //Order 定义排序规则
        Order order = new Order(Direction.DESC, "id");
        //Sort对象封装了排序规则
        Sort sort = new Sort(order);
        List<User> list = this.userRepository.findAll(sort);
        for (User users : list) {
            System.out.println(users);
        }
    }


    /**
     * JpaSpecificationExecutor   单条件测试
     */
    @Test
    public void testJpaSpecificationExecutor1() {

        /**
         * Specification<Users>:用于封装查询条件
         */
        Specification<User> spec = new Specification<User>() {

            //Predicate:封装了 单个的查询条件

            /**
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // where name = '张三三'
                /**
                 * 参数一：查询的条件属性
                 * 参数二：条件的值
                 */
                Predicate pre = cb.equal(root.get("name"), "张三三");
                return pre;
            }
        };
        List<User> list = this.userRepositorySpecification.findAll(spec);
        for (User users : list) {
            System.out.println(users);
        }
    }


    /**
     * JpaSpecificationExecutor   多条件测试
     */
    @Test
    public void testJpaSpecificationExecutor2() {

        /**
         * Specification<Users>:用于封装查询条件
         */
        Specification<User> spec = new Specification<User>() {

            //Predicate:封装了 单个的查询条件

            /**
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // where name = '张三三' and age = 20
                List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("name"), "张三三"));
                list.add(cb.equal(root.get("age"), 20));
                Predicate[] arr = new Predicate[list.size()];
                return cb.and(list.toArray(arr));
            }
        };
        List<User> list = this.userRepositorySpecification.findAll(spec);
        for (User users : list) {
            System.out.println(users);
        }
    }

    /**
     * JpaSpecificationExecutor   多条件测试第二种写法
     */
    @Test
    public void testJpaSpecificationExecutor3() {

        /**
         * Specification<Users>:用于封装查询条件
         */
        Specification<User> spec = new Specification<User>() {

            //Predicate:封装了 单个的查询条件

            /**
             * Root<Users> root:查询对象的属性的封装。
             * CriteriaQuery<?> query：封装了我们要执行的查询中的各个部分的信息，select  from order by
             * CriteriaBuilder cb:查询条件的构造器。定义不同的查询条件
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // where name = '张三三' and age = 20
                /*List<Predicate> list = new ArrayList<>();
                list.add(cb.equal(root.get("name"),"张三三"));
				list.add(cb.equal(root.get("age"),20));
				Predicate[] arr = new Predicate[list.size()];*/
                //(name = '张三' and age = 20) or id = 2
                return cb.or(cb.and(cb.equal(root.get("name"), "张三三"), cb.equal(root.get("age"), 20)), cb.equal(root.get("id"), 2));
            }
        };

        Sort sort = new Sort(new Order(Direction.DESC, "id"));
        List<User> list = this.userRepositorySpecification.findAll(spec, sort);
        for (User users : list) {
            System.out.println(users);
        }
    }


}
