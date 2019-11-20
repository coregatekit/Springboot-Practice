package com.example.demo.Repositorys;

import com.example.demo.Entitys.Member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
    Member findByUsername(String username);
}