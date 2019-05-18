package com.yqw.boot.data.jpa.repository;

import com.yqw.boot.data.jpa.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * PagingAndSortingRepository接口
 */
public interface UserRepositoryPagingAndSorting extends PagingAndSortingRepository<User, Integer> {

}
