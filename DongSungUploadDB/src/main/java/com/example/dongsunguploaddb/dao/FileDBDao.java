package com.example.dongsunguploaddb.dao;

import com.example.dongsunguploaddb.model.FileDB;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * packageName : com.example.dongsunguploaddb.dao
 * fileName : FileDBDao
 * author : Seok
 * date : 2022-05-31
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-05-31         Seok          최초 생성
 */

@Mapper // @Mapper: Mybatis 이용
public interface FileDBDao {
    // id로 이미지를 찾는 메소드
    FileDB findById(String id);

    // 모든 이미지 목록을 찾는 메소드 (여러 건)
    List<FileDB> findAll();

    // 업로드 이미지 저장 메소드
    int save(FileDB fileDB);
}